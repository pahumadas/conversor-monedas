package com.conversormonedas;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class ConversorMonedas {
    private final APIService apiService;
    private final Map<String, Moneda> monedas;
    private final Scanner scanner;
    private final DecimalFormat df;
    private final List<String> historial;

    public ConversorMonedas() {
        this.apiService = new APIService();
        this.scanner = new Scanner(System.in);
        this.df = new DecimalFormat("#,##0.00");
        this.historial = new ArrayList<>();

        // Inicializar monedas soportadas
        this.monedas = new HashMap<>();
        monedas.put("USD", new Moneda("USD", "Dólar Estadounidense", 1.0));
        monedas.put("EUR", new Moneda("EUR", "Euro", 0.0));
        monedas.put("GBP", new Moneda("GBP", "Libra Esterlina", 0.0));
        monedas.put("JPY", new Moneda("JPY", "Yen Japonés", 0.0));
        monedas.put("HKD", new Moneda("HKD", "Dólar de Hong Kong", 0.0));
        monedas.put("VND", new Moneda("VND", "Dong Vietnamita", 0.0));
        monedas.put("CLP", new Moneda("CLP", "Peso Chileno", 0.0));

        // Actualizar tasas al iniciar
        try {
            actualizarTasasDeCambio();
        } catch (Exception e) {
            System.err.println("Error al actualizar tasas de cambio: " + e.getMessage());
        }
    }

    public void iniciar() {
        System.out.println("=== CONVERSOR DE MONEDAS ===");
        mostrarMenu();
    }

    private void actualizarTasasDeCambio() throws IOException, InterruptedException {
        Map<String, Double> tasas = apiService.getExchangeRates("USD");
        for (Map.Entry<String, Moneda> entry : monedas.entrySet()) {
            String codigo = entry.getKey();
            if (tasas.containsKey(codigo)) {
                entry.getValue().setTasaCambio(tasas.get(codigo));
            }
        }
    }

    private void mostrarMenu() {
        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Realizar conversión");
            System.out.println("2. Ver historial de consultas");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.nextLine(); // Limpiar el buffer
                continue;
            }

            switch (opcion) {
                case 1:
                    realizarConversion();
                    break;
                case 2:
                    mostrarHistorial();
                    break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void realizarConversion() {
        System.out.println("\n=== REALIZAR CONVERSIÓN ===");

        // Mostrar monedas disponibles
        System.out.println("Monedas disponibles:");
        int i = 1;
        List<String> codigosMonedas = new ArrayList<>(monedas.keySet());
        for (String codigo : codigosMonedas) {
            System.out.println(i + ". " + monedas.get(codigo).getNombre() + " (" + codigo + ")");
            i++;
        }

        try {
            // Seleccionar moneda de origen
            System.out.print("\nSeleccione el número de la moneda de origen: ");
            int origenIndex = scanner.nextInt() - 1;
            String codigoOrigen = codigosMonedas.get(origenIndex);
            Moneda monedaOrigen = monedas.get(codigoOrigen);

            // Seleccionar moneda de destino
            System.out.print("Seleccione el número de la moneda de destino: ");
            int destinoIndex = scanner.nextInt() - 1;
            String codigoDestino = codigosMonedas.get(destinoIndex);
            Moneda monedaDestino = monedas.get(codigoDestino);

            // Ingresar monto
            System.out.print("Ingrese el monto a convertir: ");
            double monto = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el buffer

            // Realizar conversión
            double tasaCambio = monedaDestino.getTasaCambio() / monedaOrigen.getTasaCambio();
            double resultado = monto * tasaCambio;

            // Mostrar resultado
            System.out.println("\n=== RESULTADO ===");
            System.out.printf("%s %s = %s %s%n",
                    df.format(monto),
                    codigoOrigen,
                    df.format(resultado),
                    codigoDestino);

            // Mostrar tasa de cambio con más decimales
            DecimalFormat tasaFormat = new DecimalFormat("#,##0.000000");
            System.out.printf("Tasa de cambio: 1 %s = %s %s%n",
                    codigoOrigen,
                    tasaFormat.format(tasaCambio),
                    codigoDestino);

            // Agregar al historial
            String registro = String.format("%s - Conversión: %s -> %s | Monto: %s | Resultado: %s | Tasa: %s",
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()),
                    codigoOrigen,
                    codigoDestino,
                    df.format(monto),
                    df.format(resultado),
                    tasaFormat.format(tasaCambio));
            historial.add(0, registro); // Agregar al principio para ver los más recientes primero

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Opción de moneda no válida.");
            scanner.nextLine(); // Limpiar el buffer
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese un valor numérico válido.");
            scanner.nextLine(); // Limpiar el buffer
        }
    }

    private void mostrarHistorial() {
        System.out.println("\n=== HISTORIAL DE CONSULTAS ===");
        if (historial.isEmpty()) {
            System.out.println("No hay consultas en el historial.");
            return;
        }

        for (int i = 0; i < historial.size(); i++) {
            System.out.println((i + 1) + ". " + historial.get(i));
        }
    }
}