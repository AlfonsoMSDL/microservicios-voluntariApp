package com.usuariosService.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

import com.usuariosService.dto.GetEstadoEliminacion;
import com.usuariosService.mapper.JsonMapper;

public class Cliente {

    private final JsonMapper<GetEstadoEliminacion> conversor = new JsonMapper<>();
    private final Logger logger = Logger.getLogger(Cliente.class.getName());

    public boolean deleteDataByIdVoluntario(String URL_BASE, Long id){

        String url = URL_BASE+"?action=deleteByIdVoluntario&id="+id;
        HttpClient cliente = HttpClient.newHttpClient();

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(url))
                .build();

        try {
            HttpResponse<String> respuestaJson = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

            GetEstadoEliminacion estado = conversor.fromJson(respuestaJson.body(), GetEstadoEliminacion.class);

            if (estado.status().equals("success")) {
                return true;
            }
            return false;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al realizar la solicitud HTTP", e);
        }
    }
}

