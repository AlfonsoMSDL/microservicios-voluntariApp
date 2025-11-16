package com.jbyanx.microreportes.dto;

import java.time.LocalDate;

public class ReporteInscripcionesDTO {
    private LocalDate fecha;
    private Integer totalInscripciones;
    private Integer inscripcionesAprobadas;
    private Integer inscripcionesPendientes;
    private Integer inscripcionesRechazadas;

    public ReporteInscripcionesDTO() {}

    public ReporteInscripcionesDTO(LocalDate fecha, Integer totalInscripciones,
                                   Integer inscripcionesAprobadas, Integer inscripcionesPendientes,
                                   Integer inscripcionesRechazadas) {
        this.fecha = fecha;
        this.totalInscripciones = totalInscripciones;
        this.inscripcionesAprobadas = inscripcionesAprobadas;
        this.inscripcionesPendientes = inscripcionesPendientes;
        this.inscripcionesRechazadas = inscripcionesRechazadas;
    }

    // Getters y Setters
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Integer getTotalInscripciones() { return totalInscripciones; }
    public void setTotalInscripciones(Integer totalInscripciones) { this.totalInscripciones = totalInscripciones; }

    public Integer getInscripcionesAprobadas() { return inscripcionesAprobadas; }
    public void setInscripcionesAprobadas(Integer inscripcionesAprobadas) { this.inscripcionesAprobadas = inscripcionesAprobadas; }

    public Integer getInscripcionesPendientes() { return inscripcionesPendientes; }
    public void setInscripcionesPendientes(Integer inscripcionesPendientes) { this.inscripcionesPendientes = inscripcionesPendientes; }

    public Integer getInscripcionesRechazadas() { return inscripcionesRechazadas; }
    public void setInscripcionesRechazadas(Integer inscripcionesRechazadas) { this.inscripcionesRechazadas = inscripcionesRechazadas; }
}