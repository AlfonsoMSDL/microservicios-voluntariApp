package com.jbyanx.microreportes.service;

import com.jbyanx.microreportes.client.MicroserviceClient;
import com.jbyanx.microreportes.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteProyectoService {

    private final MicroserviceClient client;
    private final String proyectosServiceUrl;
    private final String inscripcionesServiceUrl;

    public ReporteProyectoService() {
        this.client = new MicroserviceClient();
        this.proyectosServiceUrl = ServiceConfig.getProyectosServiceUrl();
        this.inscripcionesServiceUrl = ServiceConfig.getInscripcionesServiceUrl();
    }

    /**
     * Genera reporte completo de todos los proyectos
     */
    public List<ReporteProyectoDTO> generarReporteProyectos() {
        List<ReporteProyectoDTO> reporte = new ArrayList<>();

        try {
            // 1. Obtener todos los proyectos
            String urlProyectos = proyectosServiceUrl + "/api/proyectos";
            List<ProyectoDTO> proyectos = client.getList(urlProyectos, ProyectoDTO[].class);

            // 2. Obtener todas las inscripciones
            String urlInscripciones = inscripcionesServiceUrl + "/api/inscripciones";
            List<InscripcionDTO> inscripciones = client.getList(urlInscripciones, InscripcionDTO[].class);

            // 3. Agrupar inscripciones por proyecto
            Map<Long, List<InscripcionDTO>> inscripcionesPorProyecto = inscripciones.stream()
                    .collect(Collectors.groupingBy(InscripcionDTO::getIdProyecto));

            // 4. Generar reporte para cada proyecto
            for (ProyectoDTO proyecto : proyectos) {
                ReporteProyectoDTO reporteDto = new ReporteProyectoDTO();
                reporteDto.setIdProyecto(proyecto.getId());
                reporteDto.setNombreProyecto(proyecto.getNombre());
                reporteDto.setNombreOrganizacion(proyecto.getNombreOrganizacion());
                reporteDto.setNombreCategoria(proyecto.getNombreCategoria());
                reporteDto.setVoluntariosRequeridos(proyecto.getVoluntariosRequeridos());
                reporteDto.setUbicacion(proyecto.getUbicacion());
                reporteDto.setFechaInicio(proyecto.getFechaInicio());
                reporteDto.setFechaFin(proyecto.getFechaFin());

                // Obtener inscripciones del proyecto
                List<InscripcionDTO> inscripcionesProyecto =
                        inscripcionesPorProyecto.getOrDefault(proyecto.getId(), new ArrayList<>());

                reporteDto.setVoluntariosInscritos(inscripcionesProyecto.size());

                // Contar por estado
                long aprobadas = inscripcionesProyecto.stream()
                        .filter(i -> "Aprobada".equalsIgnoreCase(i.getNombreEstado()))
                        .count();
                reporteDto.setInscripcionesAprobadas((int) aprobadas);

                long pendientes = inscripcionesProyecto.stream()
                        .filter(i -> "En revisión".equalsIgnoreCase(i.getNombreEstado()))
                        .count();
                reporteDto.setInscripcionesPendientes((int) pendientes);

                // Calcular porcentaje de ocupación (basado en aprobadas)
                if (proyecto.getVoluntariosRequeridos() != null && proyecto.getVoluntariosRequeridos() > 0) {
                    double porcentaje = (aprobadas * 100.0) / proyecto.getVoluntariosRequeridos();
                    reporteDto.setPorcentajeOcupacion(Math.round(porcentaje * 100.0) / 100.0); // 2 decimales
                } else {
                    reporteDto.setPorcentajeOcupacion(0.0);
                }

                reporte.add(reporteDto);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte de proyectos", e);
        }

        return reporte;
    }

    /**
     * Genera reporte de proyectos con mayor ocupación
     */
    public List<ReporteProyectoDTO> generarProyectosMasPopulares(int limite) {
        List<ReporteProyectoDTO> reporte = generarReporteProyectos();

        return reporte.stream()
                .sorted((a, b) -> b.getPorcentajeOcupacion().compareTo(a.getPorcentajeOcupacion()))
                .limit(limite)
                .collect(Collectors.toList());
    }

    /**
     * Genera reporte de proyectos por categoría
     */
    public Map<String, List<ReporteProyectoDTO>> generarReportePorCategoria() {
        List<ReporteProyectoDTO> reporte = generarReporteProyectos();

        return reporte.stream()
                .collect(Collectors.groupingBy(ReporteProyectoDTO::getNombreCategoria));
    }

    /**
     * Genera reporte de un proyecto específico
     */
    public ReporteProyectoDTO generarReporteProyecto(Long idProyecto) {
        try {
            // Obtener el proyecto
            String urlProyecto = proyectosServiceUrl + "/api/proyectos?action=getById&id=" + idProyecto;
            ProyectoDTO proyecto = client.get(urlProyecto, ProyectoDTO.class);

            // Obtener inscripciones del proyecto
            String urlInscripciones = inscripcionesServiceUrl + "/api/inscripciones?idProyecto=" + idProyecto;
            List<InscripcionDTO> inscripciones = client.getList(urlInscripciones, InscripcionDTO[].class);

            // Crear reporte
            ReporteProyectoDTO reporteDto = new ReporteProyectoDTO();
            reporteDto.setIdProyecto(proyecto.getId());
            reporteDto.setNombreProyecto(proyecto.getNombre());
            reporteDto.setNombreOrganizacion(proyecto.getNombreOrganizacion());
            reporteDto.setNombreCategoria(proyecto.getNombreCategoria());
            reporteDto.setVoluntariosRequeridos(proyecto.getVoluntariosRequeridos());
            reporteDto.setUbicacion(proyecto.getUbicacion());
            reporteDto.setFechaInicio(proyecto.getFechaInicio());
            reporteDto.setFechaFin(proyecto.getFechaFin());
            reporteDto.setVoluntariosInscritos(inscripciones.size());

            long aprobadas = inscripciones.stream()
                    .filter(i -> "Aprobada".equalsIgnoreCase(i.getNombreEstado()))
                    .count();
            reporteDto.setInscripcionesAprobadas((int) aprobadas);

            long pendientes = inscripciones.stream()
                    .filter(i -> "En revisión".equalsIgnoreCase(i.getNombreEstado()))
                    .count();
            reporteDto.setInscripcionesPendientes((int) pendientes);

            if (proyecto.getVoluntariosRequeridos() != null && proyecto.getVoluntariosRequeridos() > 0) {
                double porcentaje = (aprobadas * 100.0) / proyecto.getVoluntariosRequeridos();
                reporteDto.setPorcentajeOcupacion(Math.round(porcentaje * 100.0) / 100.0);
            } else {
                reporteDto.setPorcentajeOcupacion(0.0);
            }

            return reporteDto;

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte del proyecto: " + idProyecto, e);
        }
    }
}