package com.jbyanx.microreportes.service;

import com.jbyanx.microreportes.client.MicroserviceClient;
import com.jbyanx.microreportes.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteVoluntarioService {

    private final MicroserviceClient client;
    private final String usuariosServiceUrl;
    private final String inscripcionesServiceUrl;

    public ReporteVoluntarioService() {
        this.client = new MicroserviceClient();
        this.usuariosServiceUrl = ServiceConfig.getUsuariosServiceUrl();
        this.inscripcionesServiceUrl = ServiceConfig.getInscripcionesServiceUrl();
    }

    /**
     * Genera reporte completo de todos los voluntarios
     */
    public List<ReporteVoluntarioDTO> generarReporteVoluntarios() {
        List<ReporteVoluntarioDTO> reporte = new ArrayList<>();

        try {
            // 1. Obtener todos los voluntarios
            String urlVoluntarios = usuariosServiceUrl + "/api/voluntarios";
            List<VoluntarioDTO> voluntarios = client.getList(urlVoluntarios, VoluntarioDTO[].class);

            // 2. Obtener todas las inscripciones
            String urlInscripciones = inscripcionesServiceUrl + "/api/inscripciones";
            List<InscripcionDTO> inscripciones = client.getList(urlInscripciones, InscripcionDTO[].class);

            // 3. Agrupar inscripciones por voluntario
            Map<Long, List<InscripcionDTO>> inscripcionesPorVoluntario = inscripciones.stream()
                    .collect(Collectors.groupingBy(InscripcionDTO::getIdVoluntario));

            // 4. Generar reporte para cada voluntario
            for (VoluntarioDTO voluntario : voluntarios) {
                ReporteVoluntarioDTO reporteDto = new ReporteVoluntarioDTO();
                reporteDto.setIdVoluntario(voluntario.getId());
                reporteDto.setNombreCompleto(voluntario.getNombreCompleto());
                reporteDto.setCorreo(voluntario.getCorreo());
                reporteDto.setHabilidades(voluntario.getHabilidades());
                reporteDto.setAreasInteres(voluntario.getAreasInteres());

                // Obtener inscripciones del voluntario
                List<InscripcionDTO> inscripcionesVoluntario =
                        inscripcionesPorVoluntario.getOrDefault(voluntario.getId(), new ArrayList<>());

                reporteDto.setTotalInscripciones(inscripcionesVoluntario.size());

                // Contar por estado (basado en nombre del estado)
                long aprobadas = inscripcionesVoluntario.stream()
                        .filter(i -> "Aprobada".equalsIgnoreCase(i.getNombreEstado()))
                        .count();
                reporteDto.setInscripcionesAprobadas((int) aprobadas);

                long pendientes = inscripcionesVoluntario.stream()
                        .filter(i -> "En revisión".equalsIgnoreCase(i.getNombreEstado()))
                        .count();
                reporteDto.setInscripcionesPendientes((int) pendientes);

                long rechazadas = inscripcionesVoluntario.stream()
                        .filter(i -> "Rechazada".equalsIgnoreCase(i.getNombreEstado()))
                        .count();
                reporteDto.setInscripcionesRechazadas((int) rechazadas);

                reporte.add(reporteDto);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte de voluntarios", e);
        }

        return reporte;
    }

    /**
     * Genera reporte de top voluntarios más activos
     */
    public List<ReporteVoluntarioDTO> generarTopVoluntarios(int limite) {
        List<ReporteVoluntarioDTO> reporte = generarReporteVoluntarios();

        // Ordenar por inscripciones aprobadas (descendente)
        return reporte.stream()
                .sorted((a, b) -> b.getInscripcionesAprobadas().compareTo(a.getInscripcionesAprobadas()))
                .limit(limite)
                .collect(Collectors.toList());
    }

    /**
     * Genera reporte de un voluntario específico
     */
    public ReporteVoluntarioDTO generarReporteVoluntario(Long idVoluntario) {
        try {
            // Obtener el voluntario
            String urlVoluntario = usuariosServiceUrl + "/api/voluntarios?action=getById&id=" + idVoluntario;
            VoluntarioDTO voluntario = client.get(urlVoluntario, VoluntarioDTO.class);

            // Obtener inscripciones del voluntario
            String urlInscripciones = inscripcionesServiceUrl + "/api/inscripciones?idVoluntario=" + idVoluntario;
            List<InscripcionDTO> inscripciones = client.getList(urlInscripciones, InscripcionDTO[].class);

            // Crear reporte
            ReporteVoluntarioDTO reporteDto = new ReporteVoluntarioDTO();
            reporteDto.setIdVoluntario(voluntario.getId());
            reporteDto.setNombreCompleto(voluntario.getNombreCompleto());
            reporteDto.setCorreo(voluntario.getCorreo());
            reporteDto.setHabilidades(voluntario.getHabilidades());
            reporteDto.setAreasInteres(voluntario.getAreasInteres());
            reporteDto.setTotalInscripciones(inscripciones.size());

            long aprobadas = inscripciones.stream()
                    .filter(i -> "Aprobada".equalsIgnoreCase(i.getNombreEstado()))
                    .count();
            reporteDto.setInscripcionesAprobadas((int) aprobadas);

            long pendientes = inscripciones.stream()
                    .filter(i -> "En revisión".equalsIgnoreCase(i.getNombreEstado()))
                    .count();
            reporteDto.setInscripcionesPendientes((int) pendientes);

            long rechazadas = inscripciones.stream()
                    .filter(i -> "Rechazada".equalsIgnoreCase(i.getNombreEstado()))
                    .count();
            reporteDto.setInscripcionesRechazadas((int) rechazadas);

            return reporteDto;

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte del voluntario: " + idVoluntario, e);
        }
    }
}
