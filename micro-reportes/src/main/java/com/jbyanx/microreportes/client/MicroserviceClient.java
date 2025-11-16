package com.jbyanx.microreportes.client;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class MicroserviceClient {

    private final HttpClient httpClient;
    private final Gson gson;

    public MicroserviceClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Obtiene un recurso por ID
     */
    public <T> T getById(String baseUrl, Long id, Class<T> clazz) {
        try {
            String url = baseUrl + "?action=getById&id=" + id;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error HTTP " + response.statusCode() + ": " + response.body());
            }

            return gson.fromJson(response.body(), clazz);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al obtener recurso por ID: " + id, e);
        }
    }

    /**
     * Obtiene todos los recursos
     */
    public <T> List<T> getAll(String url, Class<T[]> arrayClass) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error HTTP " + response.statusCode() + ": " + response.body());
            }

            T[] array = gson.fromJson(response.body(), arrayClass);
            return Arrays.asList(array);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al obtener lista de recursos", e);
        }
    }

    /**
     * Realiza una petición GET personalizada
     */
    public <T> T get(String url, Class<T> clazz) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error HTTP " + response.statusCode() + ": " + response.body());
            }

            return gson.fromJson(response.body(), clazz);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error en petición GET: " + url, e);
        }
    }

    /**
     * Realiza una petición GET que retorna una lista
     */
    public <T> List<T> getList(String url, Class<T[]> arrayClass) {
        return getAll(url, arrayClass);
    }
}