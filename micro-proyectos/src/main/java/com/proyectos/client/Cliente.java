package com.proyectos.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

import com.proyectos.dto.GetEstadoEliminacion;
import com.proyectos.mapper.JsonMapper;

public class Cliente<T> {

    private final JsonMapper<T> conversor = new JsonMapper<>();
    private final JsonMapper<GetEstadoEliminacion> conversorEstadoEliminacion = new JsonMapper<>();
    private final Logger logger = Logger.getLogger(Cliente.class.getName());

    public  T getById(Long id, String URL_ROOT, Class<T> toConvert) {
        try {
            String urlGetById = URL_ROOT + "?action=getById&id=" + id;
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest solicitud = HttpRequest.newBuilder().uri(URI.create(urlGetById)).build();

            HttpResponse<String> respuestaJson = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            //logger.info(respuestaJson.body());
            return conversor.fromJson(respuestaJson.body(), toConvert);
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException("Error al realizar la solicitud HTTP", ex);
        }
    }

    public boolean deleteDataByIdProyecto(String URL_BASE, Long id){

        String url = URL_BASE+"?action=deleteByIdProyecto&id="+id;
        HttpClient cliente = HttpClient.newHttpClient();

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(url))
                .build();

        try {
            HttpResponse<String> respuestaJson = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

            GetEstadoEliminacion estado = conversorEstadoEliminacion.fromJson(respuestaJson.body(), GetEstadoEliminacion.class);

            if (estado.status().equals("success")) {
                return true;
            }
            return false;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al realizar la solicitud HTTP", e);
        }
    }
}

