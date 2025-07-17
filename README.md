# Conversor de Monedas en Java
Un conversor de monedas en Java que utiliza tasas de cambio en tiempo real a través de una API externa.
## Características
- Conversión entre múltiples monedas (USD, EUR, GBP, JPY, HKD, VND, CLP)
- Interfaz de línea de comandos (CLI) intuitiva
- Historial de conversiones recientes
- Tasas de cambio actualizadas automáticamente
- Precisión de hasta 6 decimales en las tasas de cambio
## Requisitos
- Java 11 o superior
- Conexión a Internet (para obtener tasas de cambio actualizadas)
- API Key de ExchangeRate-API (gratuita)
## Estructura del Proyecto
-src/ para el código fuente
-com.conversormonedas como paquete raíz (siguiendo la convención de nombres de dominio inverso)
-lib/ para las bibliotecas externas

### Explicación de los archivos:

1. **APIService.java**
   - Se encarga de hacer las peticiones a la API de tasas de cambio
   - Procesa la respuesta JSON que devuelve la API

2. **ConversorMonedas.java**
   - Contiene el menú principal del programa
   - Maneja la lógica de conversión entre monedas
   - Administra el historial de conversiones

3. **Main.java**
   - Punto de inicio de la aplicación
   - Crea una instancia de ConversorMonedas y lo inicia

4. **Moneda.java**
   - Define cómo es una moneda (código, nombre, tasa de cambio)
   - Incluye métodos para obtener y establecer estos valores

5. **gson-2.13.1.jar**
   - Biblioteca necesaria para trabajar con archivos JSON
   - Se usa para interpretar la respuesta de la API

## Cómo usar
1. Clona el repositorio
2. Importa el proyecto en tu IDE favorito
3. Asegúrate de tener la biblioteca Gson en el classpath
4. Ejecuta la clase [Main](cci:2://file:///C:/Users/HP_Laptop/Documents/Conversor%20de%20Monedas/src/com/conversormonedas/Main.java:3:0-8:1)
## Funcionamiento
El programa:
1. Se inicia mostrando un menú principal
2. Permite realizar conversiones entre monedas
3. Muestra el historial de conversiones
4. Actualiza automáticamente las tasas de cambio al iniciar
## Tecnologías utilizadas
- Java 11
- Gson para el manejo de JSON
- ExchangeRate-API para obtener tasas de cambio
