// APIService.java
package com.conversormonedas;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class APIService {
    private static final String API_KEY = "c7fe00433e087295a8b2da00";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private final Gson gson = new Gson();

    public Map<String, Double> getExchangeRates(String baseCurrency) throws IOException, InterruptedException {
        String url = BASE_URL + API_KEY + "/latest/" + baseCurrency;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            var jsonResponse = gson.fromJson(response.body(), Map.class);
            return (Map<String, Double>) jsonResponse.get("conversion_rates");
        } else {
            throw new RuntimeException("Error al obtener las tasas de cambio: " + response.statusCode());
        }
    }
}