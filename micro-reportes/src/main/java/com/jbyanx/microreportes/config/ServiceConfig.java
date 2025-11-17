package com.jbyanx.microreportes.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServiceConfig {

    private static final Properties properties = new Properties();

    static {
        try {
            loadProperties();
        } catch (Exception e) {
            System.err.println("⚠️ WARNING: No se pudo cargar services.properties");
            e.printStackTrace();
        }
    }

    private static void loadProperties() {
        try (InputStream input = ServiceConfig.class
                .getClassLoader()
                .getResourceAsStream("services.properties")) {
            if (input == null) {
                System.out.println("⚠️ services.properties no encontrado, usando solo variables de entorno");
                return;
            }
            properties.load(input);
            System.out.println("✅ services.properties cargado correctamente");
        } catch (IOException e) {
            System.err.println("❌ Error cargando services.properties: " + e.getMessage());
        }
    }

    // URLs base (sin /api)
    public static String getUsuariosServiceUrl() {
        String envUrl = System.getenv("USUARIOS_SERVICE_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            return envUrl;
        }
        return properties.getProperty("usuarios.service.url", "http://localhost:8081");
    }

    public static String getProyectosServiceUrl() {
        String envUrl = System.getenv("PROYECTOS_SERVICE_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            return envUrl;
        }
        return properties.getProperty("proyectos.service.url", "http://localhost:8082");
    }

    public static String getInscripcionesServiceUrl() {
        String envUrl = System.getenv("INSCRIPCIONES_SERVICE_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            return envUrl;
        }
        return properties.getProperty("inscripciones.service.url", "http://localhost:8083");
    }
}