package com.jbyanx.microreportes.service;

import com.jbyanx.microreportes.client.MicroserviceClient;
import com.jbyanx.microreportes.dto.*;

import java.util.*;
import java.util.stream.Collectors;

public class ReporteOrganizacionService {

    private final MicroserviceClient client;
    private final String usuariosServiceUrl;
    private final String proyectosServiceUrl;
    private final String inscripcionesServiceUrl;

    public ReporteOrganizacionService() {
        this.client = new MicroserviceClient();
        this.usuariosServiceUrl = ServiceConfig.getUsuariosServiceUrl();
        this.proyectosServiceUrl = ServiceConfig.getProyectosServiceUrl();
        this.inscripcionesServiceUrl = ServiceConfig.getInscripcionesServiceUrl();
    }

    /**
     * Genera reporte completo de todas las organizaciones
     */
    public List<ReporteOrganizacionDTO> generarReporteOrganizaciones() {
        List<ReporteOrganizacionDTO> reporte = new ArrayList<>();

        try {
            // 1. Obtener todas las organizaciones
            String urlOrganizaciones = usuariosServiceUrl + "/api/organizaciones";
            List<OrganizacionDTO> organizaciones = client.getList(urlOrganizaciones, OrganizacionDTO[].class);

            // 2. Obtener todos los proyectos
            String urlProyectos = proyectosServiceUrl + "/api/proyectos";
            List<ProyectoDTO> proyectos = client.getList(urlProyectos, ProyectoDTO[].class);

            // 3. Obtener todas las inscripciones
            String urlInscripciones = inscripcionesServiceUrl + "/api/inscripciones";
            List<InscripcionDTO> inscripciones = client.getList(urlInscripciones, InscripcionDTO[].class);

            // 4. Agrupar proyectos por organización
            Map<Long, List<ProyectoDTO>> proyectosPorOrg = proyectos.stream()
                    .collect(Collectors.groupingBy(ProyectoDTO::getOrganizacionId));

            // 5. Crear mapa de inscripciones por proyecto
            Map<Long, List<InscripcionDTO>> inscripcionesPorProyecto = inscripciones.stream()
                    .collect(Collectors.groupingBy(InscripcionDTO::getIdProyecto));

            // 6. Generar reporte para cada organización
            for (OrganizacionDTO org : organizaciones) {
                ReporteOrganizacionDTO reporteDto = new ReporteOrganizacionDTO();
                reporteDto.setIdOrganizacion(org.getId());
                reporteDto.setNombreOrganizacion(org.getNombre());
                reporteDto.setCorreo(org.getCorreo());
                reporteDto.setTelefono(org.getTelefono());
                reporteDto.setDescripcion(org.getDescripcion());
                reporteDto.setTipoOrganizacion(org.getTipoOrganizacionNombre());

                // Proyectos de la organización
                List<ProyectoDTO> proyectosOrg = proyectosPorOrg.getOrDefault(org.getId(), new ArrayList<>());
                reporteDto.setTotalProyectos(proyectosOrg.size());

                // Contar proyectos activos (simulado - todos los que tienen fechas)
                int proyectosActivos = (int) proyectosOrg.stream()
                        .filter(p -> p.getFechaInicio() != null)
                        .count();
                reporteDto.setProyectosActivos(proyectosActivos);

                // Contar voluntarios inscritos en proyectos de esta organización
                Set<Long> voluntariosUnicos = new HashSet<>();
                Set<Long> voluntariosActivos = new HashSet<>();

                for (ProyectoDTO proyecto : proyectosOrg) {
                    List<InscripcionDTO> inscripcionesProyecto =
                            inscripcionesPorProyecto.getOrDefault(proyecto.getId(), new ArrayList<>());

                    for (InscripcionDTO inscripcion : inscripcionesProyecto) {
                        voluntariosUnicos.add(inscripcion.getIdVoluntario());
                        if ("Aprobada".equalsIgnoreCase(inscripcion.getNombreEstado())) {
                            voluntariosActivos.add(inscripcion.getIdVoluntario());
                        }
                    }
                }

                reporteDto.setTotalVoluntariosInscritos(voluntariosUnicos.size());
                reporteDto.setVoluntariosActivos(voluntariosActivos.size());

                reporte.add(reporteDto);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte de organizaciones", e);
        }

        return reporte;
    }

    /**
     * Genera reporte de top organizaciones más activas
     */
    public List<ReporteOrganizacionDTO> generarTopOrganizaciones(int limite) {
        List<ReporteOrganizacionDTO> reporte = generarReporteOrganizaciones();

        return reporte.stream()
                .sorted((a, b) -> {
                    // Ordenar por total de proyectos, luego por voluntarios activos
                    int compareProyectos = b.getTotalProyectos().compareTo(a.getTotalProyectos());
                    if (compareProyectos != 0) return compareProyectos;
                    return b.getVoluntariosActivos().compareTo(a.getVoluntariosActivos());
                })
                .limit(limite)
                .collect(Collectors.toList());
    }

    /**
     * Genera reporte de una organización específica
     */
    public ReporteOrganizacionDTO generarReporteOrganizacion(Long idOrganizacion) {
        List<ReporteOrganizacionDTO> reporte = generarReporteOrganizaciones();

        return reporte.stream()
                .filter(r -> r.getIdOrganizacion().equals(idOrganizacion))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Organización no encontrada: " + idOrganizacion));
    }

    /**
     * Genera reporte de organizaciones agrupadas por tipo
     */
    public Map<String, Object> generarReportePorTipo() {
        Map<String, Object> reporte = new HashMap<>();

        try {
            List<ReporteOrganizacionDTO> organizaciones = generarReporteOrganizaciones();

            // Agrupar por tipo
            Map<String, List<ReporteOrganizacionDTO>> porTipo = organizaciones.stream()
                    .collect(Collectors.groupingBy(
                            o -> o.getTipoOrganizacion() != null ? o.getTipoOrganizacion() : "Sin tipo"
                    ));

            // Crear resumen por tipo
            List<Map<String, Object>> resumenTipos = porTipo.entrySet().stream()
                    .map(entry -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("tipoOrganizacion", entry.getKey());
                        map.put("cantidadOrganizaciones", entry.getValue().size());

                        int totalProyectos = entry.getValue().stream()
                                .mapToInt(ReporteOrganizacionDTO::getTotalProyectos)
                                .sum();
                        map.put("totalProyectos", totalProyectos);

                        int totalVoluntarios = entry.getValue().stream()
                                .mapToInt(ReporteOrganizacionDTO::getTotalVoluntariosInscritos)
                                .sum();
                        map.put("totalVoluntariosInscritos", totalVoluntarios);

                        return map;
                    })
                    .sorted((a, b) -> Integer.compare(
                            (Integer) b.get("cantidadOrganizaciones"),
                            (Integer) a.get("cantidadOrganizaciones")
                    ))
                    .collect(Collectors.toList());

            reporte.put("tiposOrganizacion", resumenTipos);
            reporte.put("totalOrganizaciones", organizaciones.size());

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte por tipo de organización", e);
        }

        return reporte;
    }
}