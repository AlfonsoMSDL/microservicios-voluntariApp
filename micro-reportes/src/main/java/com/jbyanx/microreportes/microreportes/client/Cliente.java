package com.jbyanx.microreportes.microreportes.client;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Cliente<T> {
    private final Gson gson = new Gson();

    public T getById(Long id, String URL_ROOT, Class<T> toConvert) {
        try {
            String urlGetById = URL_ROOT + "?action=getById&id=" + id;
            var cliente = HttpClient.newHttpClient();
            HttpRequest solicitud = HttpRequest.newBuilder().uri(URI.create(urlGetById)).build();

            HttpResponse<String> respuestaJson = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(respuestaJson.body(), toConvert);
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException("Error al realizar la solicitud HTTP", ex);
        }
    }
}
