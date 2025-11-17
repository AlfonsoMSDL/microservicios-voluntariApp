package com.jbyanx.microreportes.service;

import com.jbyanx.microreportes.client.MicroserviceClient;
import com.jbyanx.microreportes.config.ServiceConfig;
import com.jbyanx.microreportes.dto.InscripcionDTO;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ReporteInscripcionService {

    private final MicroserviceClient client;
    private final String usuariosServiceUrl;
    private final String proyectosServiceUrl;
    private final String inscripcionesServiceUrl;

    public ReporteInscripcionService() {
        this.client = new MicroserviceClient();
        this.usuariosServiceUrl = ServiceConfig.getUsuariosServiceUrl();
        this.proyectosServiceUrl = ServiceConfig.getProyectosServiceUrl();
        this.inscripcionesServiceUrl = ServiceConfig.getInscripcionesServiceUrl();
    }

    /**
     * Genera reporte general de inscripciones
     */
    public Map<String, Object> generarReporteGeneral() {
        Map<String, Object> reporte = new HashMap<>();

        try {
            // Obtener todas las inscripciones
            String urlInscripciones = inscripcionesServiceUrl + "/api/inscripciones";
            List<InscripcionDTO> inscripciones = client.getList(urlInscripciones, InscripcionDTO[].class);

            reporte.put("totalInscripciones", inscripciones.size());

            // Contar por estado
            Map<String, Long> porEstado = inscripciones.stream()
                    .collect(Collectors.groupingBy(
                            i -> i.getNombreEstado() != null ? i.getNombreEstado() : "Sin estado",
                            Collectors.counting()
                    ));
            reporte.put("inscripcionesPorEstado", porEstado);

            // Calcular tasas
            long total = inscripciones.size();
            if (total > 0) {
                long aprobadas = porEstado.getOrDefault("Aprobada", 0L);
                long pendientes = porEstado.getOrDefault("En revisión", 0L);
                long rechazadas = porEstado.getOrDefault("Rechazada", 0L);

                reporte.put("tasaAprobacion", Math.round((aprobadas * 100.0 / total) * 100.0) / 100.0);
                reporte.put("tasaRechazo", Math.round((rechazadas * 100.0 / total) * 100.0) / 100.0);
                reporte.put("tasaPendiente", Math.round((pendientes * 100.0 / total) * 100.0) / 100.0);
            }

            // Proyectos más solicitados
            Map<Long, Long> inscripcionesPorProyecto = inscripciones.stream()
                    .collect(Collectors.groupingBy(
                            InscripcionDTO::getIdProyecto,
                            Collectors.counting()
                    ));

            List<Map<String, Object>> topProyectos = inscripcionesPorProyecto.entrySet().stream()
                    .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                    .limit(5)
                    .map(entry -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("idProyecto", entry.getKey());
                        map.put("totalInscripciones", entry.getValue());
                        return map;
                    })
                    .collect(Collectors.toList());

            reporte.put("proyectosMasSolicitados", topProyectos);

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte general de inscripciones", e);
        }

        return reporte;
    }

    /**
     * Genera reporte de inscripciones por estado
     */
    public Map<String, Object> generarReportePorEstado() {
        Map<String, Object> reporte = new HashMap<>();

        try {
            String urlInscripciones = inscripcionesServiceUrl + "/api/inscripciones";
            List<InscripcionDTO> inscripciones = client.getList(urlInscripciones, InscripcionDTO[].class);

            // Agrupar por estado
            Map<String, List<InscripcionDTO>> porEstado = inscripciones.stream()
                    .collect(Collectors.groupingBy(
                            i -> i.getNombreEstado() != null ? i.getNombreEstado() : "Sin estado"
                    ));

            // Crear detalle por cada estado
            List<Map<String, Object>> detalleEstados = porEstado.entrySet().stream()
                    .map(entry -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("estado", entry.getKey());
                        map.put("cantidad", entry.getValue().size());
                        map.put("porcentaje", Math.round((entry.getValue().size() * 100.0 / inscripciones.size()) * 100.0) / 100.0);
                        return map;
                    })
                    .sorted((a, b) -> Integer.compare((Integer) b.get("cantidad"), (Integer) a.get("cantidad")))
                    .collect(Collectors.toList());

            reporte.put("estados", detalleEstados);
            reporte.put("totalInscripciones", inscripciones.size());

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte por estado", e);
        }

        return reporte;
    }

    /**
     * Genera reporte de inscripciones de un estado específico
     */
    public Map<String, Object> generarReportePorEstadoEspecifico(String nombreEstado) {
        Map<String, Object> reporte = new HashMap<>();

        try {
            String urlInscripciones = inscripcionesServiceUrl + "/api/inscripciones";
            List<InscripcionDTO> todasInscripciones = client.getList(urlInscripciones, InscripcionDTO[].class);

            // Filtrar por estado
            List<InscripcionDTO> inscripcionesFiltradas = todasInscripciones.stream()
                    .filter(i -> nombreEstado.equalsIgnoreCase(i.getNombreEstado()))
                    .collect(Collectors.toList());

            reporte.put("estado", nombreEstado);
            reporte.put("totalInscripciones", inscripcionesFiltradas.size());
            reporte.put("inscripciones", inscripcionesFiltradas);

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte por estado: " + nombreEstado, e);
        }

        return reporte;
    }

    /**
     * Genera tendencia de inscripciones (simulado - últimos registros)
     */
    public Map<String, Object> generarTendenciaInscripciones() {
        Map<String, Object> reporte = new HashMap<>();

        try {
            String urlInscripciones = inscripcionesServiceUrl + "/api/inscripciones";
            List<InscripcionDTO> inscripciones = client.getList(urlInscripciones, InscripcionDTO[].class);

            // Agrupar por fecha (simulado con conteo simple)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, Long> porFecha = inscripciones.stream()
                    .filter(i -> i.getFechaInscripcion() != null)
                    .collect(Collectors.groupingBy(
                            i -> sdf.format(i.getFechaInscripcion()),
                            Collectors.counting()
                    ));

            // Ordenar por fecha
            List<Map<String, Object>> tendencia = porFecha.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(entry -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("fecha", entry.getKey());
                        map.put("cantidad", entry.getValue());
                        return map;
                    })
                    .collect(Collectors.toList());

            reporte.put("tendencia", tendencia);
            reporte.put("totalInscripciones", inscripciones.size());
            reporte.put("totalDias", porFecha.size());

        } catch (Exception e) {
            throw new RuntimeException("Error generando tendencia de inscripciones", e);
        }

        return reporte;
    }
}