package com.jbyanx.microreportes.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServiceConfig {

    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = ServiceConfig.class
                .getClassLoader()
                .getResourceAsStream("services.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró services.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando configuración de servicios", e);
        }
    }

    public static String getUsuariosServiceUrl() {
        // Primero intenta obtener de variable de entorno (Docker)
        String envUrl = System.getenv("USUARIOS_SERVICE_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            return envUrl;
        }
        // Si no existe, usa el del properties (desarrollo local)
        return properties.getProperty("usuarios.service.url");
    }

    public static String getProyectosServiceUrl() {
        String envUrl = System.getenv("PROYECTOS_SERVICE_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            return envUrl;
        }
        return properties.getProperty("proyectos.service.url");
    }

    public static String getInscripcionesServiceUrl() {
        String envUrl = System.getenv("INSCRIPCIONES_SERVICE_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            return envUrl;
        }
        return properties.getProperty("inscripciones.service.url");
    }
}