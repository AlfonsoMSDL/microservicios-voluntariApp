package com.jbyanx.microreportes.service;

import com.jbyanx.microreportes.client.MicroserviceClient;
import com.jbyanx.microreportes.config.ServiceConfig;
import com.jbyanx.microreportes.dto.*;

import java.util.*;
import java.util.stream.Collectors;

public class DashboardService {

    private final MicroserviceClient client;
    private final String usuariosServiceUrl;
    private final String proyectosServiceUrl;
    private final String inscripcionesServiceUrl;

    public DashboardService() {
        this.client = new MicroserviceClient();
        this.usuariosServiceUrl = ServiceConfig.getUsuariosServiceUrl();
        this.proyectosServiceUrl = ServiceConfig.getProyectosServiceUrl();
        this.inscripcionesServiceUrl = ServiceConfig.getInscripcionesServiceUrl();
    }

    /**
     * Genera dashboard con estadísticas generales
     */
    public DashboardDTO generarDashboard() {
        DashboardDTO dashboard = new DashboardDTO();

        try {
            // 1. Obtener totales de voluntarios ✅
            String urlVoluntarios = usuariosServiceUrl + "/voluntarios?action=getAllVoluntarios";
            List<VoluntarioDTO> voluntarios = client.getList(urlVoluntarios, VoluntarioDTO[].class);
            dashboard.setTotalVoluntarios(voluntarios.size());

            // 2. Obtener totales de organizaciones ✅
            String urlOrganizaciones = usuariosServiceUrl + "/organizaciones?action=getAllOrganizaciones";
            int totalOrganizaciones = client.getCount(urlOrganizaciones);
            dashboard.setTotalOrganizaciones(totalOrganizaciones);

            // 3. Obtener totales de proyectos ✅
            String urlProyectos = proyectosServiceUrl + "/proyectos?action=getProyectos";
            List<ProyectoDTO> proyectos = client.getList(urlProyectos, ProyectoDTO[].class);
            dashboard.setTotalProyectos(proyectos.size());

            // 4. Obtener inscripciones (agregando de cada proyecto) ✅
            List<InscripcionDTO> todasLasInscripciones = new ArrayList<>();
            for (ProyectoDTO proyecto : proyectos) {
                String urlInscripciones = inscripcionesServiceUrl +
                        "/inscripciones?action=getInscripcionesByProyecto&idProyecto=" + proyecto.getId();
                try {
                    List<InscripcionDTO> inscripcionesProyecto = client.getList(urlInscripciones, InscripcionDTO[].class);
                    todasLasInscripciones.addAll(inscripcionesProyecto);
                } catch (Exception e) {
                    // Si un proyecto no tiene inscripciones, continuar
                }
            }
            dashboard.setTotalInscripciones(todasLasInscripciones.size());

            // 5. Contar inscripciones por estado
            Map<String, Long> inscripcionesPorEstado = todasLasInscripciones.stream()
                    .collect(Collectors.groupingBy(
                            i -> i.getNombreEstado() != null ? i.getNombreEstado() : "Sin estado",
                            Collectors.counting()
                    ));
            dashboard.setInscripcionesPorEstado(inscripcionesPorEstado);

            // 6. Proyectos por categoría
            Map<String, Long> proyectosPorCategoria = proyectos.stream()
                    .collect(Collectors.groupingBy(
                            p -> p.getNombreCategoria() != null ? p.getNombreCategoria() : "Sin categoría",
                            Collectors.counting()
                    ));
            dashboard.setProyectosPorCategoria(proyectosPorCategoria);

            // 7. Top 5 proyectos más populares (por inscripciones aprobadas)
            Map<Long, Long> inscripcionesPorProyecto = todasLasInscripciones.stream()
                    .filter(i -> "Aprobada".equalsIgnoreCase(i.getNombreEstado()))
                    .collect(Collectors.groupingBy(
                            InscripcionDTO::getIdProyecto,
                            Collectors.counting()
                    ));

            List<Map<String, Object>> topProyectos = proyectos.stream()
                    .map(p -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", p.getId());
                        map.put("nombre", p.getNombre());
                        map.put("inscripciones", inscripcionesPorProyecto.getOrDefault(p.getId(), 0L));
                        return map;
                    })
                    .sorted((a, b) -> Long.compare(
                            (Long) b.get("inscripciones"),
                            (Long) a.get("inscripciones")
                    ))
                    .limit(5)
                    .collect(Collectors.toList());
            dashboard.setTopProyectos(topProyectos);

            // 8. Top 5 voluntarios más activos
            Map<Long, Long> inscripcionesPorVoluntario = todasLasInscripciones.stream()
                    .filter(i -> "Aprobada".equalsIgnoreCase(i.getNombreEstado()))
                    .collect(Collectors.groupingBy(
                            InscripcionDTO::getIdVoluntario,
                            Collectors.counting()
                    ));

            List<Map<String, Object>> topVoluntarios = voluntarios.stream()
                    .map(v -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", v.getId());
                        map.put("nombre", v.getNombreCompleto());
                        map.put("inscripciones", inscripcionesPorVoluntario.getOrDefault(v.getId(), 0L));
                        return map;
                    })
                    .sorted((a, b) -> Long.compare(
                            (Long) b.get("inscripciones"),
                            (Long) a.get("inscripciones")
                    ))
                    .limit(5)
                    .collect(Collectors.toList());
            dashboard.setTopVoluntarios(topVoluntarios);

            // 9. Calcular tasa de ocupación promedio
            double tasaOcupacionPromedio = proyectos.stream()
                    .filter(p -> p.getVoluntariosRequeridos() != null && p.getVoluntariosRequeridos() > 0)
                    .mapToDouble(p -> {
                        long inscritos = inscripcionesPorProyecto.getOrDefault(p.getId(), 0L);
                        return (inscritos * 100.0) / p.getVoluntariosRequeridos();
                    })
                    .average()
                    .orElse(0.0);
            dashboard.setTasaOcupacionPromedio(Math.round(tasaOcupacionPromedio * 100.0) / 100.0);

        } catch (Exception e) {
            throw new RuntimeException("Error generando dashboard", e);
        }

        return dashboard;
    }
}