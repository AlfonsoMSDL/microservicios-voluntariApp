package com.jbyanx.microreportes.dto;

public class ReporteVoluntarioDTO {
    private Long idVoluntario;
    private String nombreCompleto;
    private String correo;
    private Integer totalInscripciones;
    private Integer inscripcionesAprobadas;
    private Integer inscripcionesPendientes;
    private Integer inscripcionesRechazadas;
    private String habilidades;
    private String areasInteres;

    // Constructor vacío
    public ReporteVoluntarioDTO() {}

    // Getters y Setters
    public Long getIdVoluntario() { return idVoluntario; }
    public void setIdVoluntario(Long idVoluntario) { this.idVoluntario = idVoluntario; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Integer getTotalInscripciones() { return totalInscripciones; }
    public void setTotalInscripciones(Integer totalInscripciones) { this.totalInscripciones = totalInscripciones; }

    public Integer getInscripcionesAprobadas() { return inscripcionesAprobadas; }
    public void setInscripcionesAprobadas(Integer inscripcionesAprobadas) { this.inscripcionesAprobadas = inscripcionesAprobadas; }

    public Integer getInscripcionesPendientes() { return inscripcionesPendientes; }
    public void setInscripcionesPendientes(Integer inscripcionesPendientes) { this.inscripcionesPendientes = inscripcionesPendientes; }

    public Integer getInscripcionesRechazadas() { return inscripcionesRechazadas; }
    public void setInscripcionesRechazadas(Integer inscripcionesRechazadas) { this.inscripcionesRechazadas = inscripcionesRechazadas; }

    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }

    public String getAreasInteres() { return areasInteres; }
    public void setAreasInteres(String areasInteres) { this.areasInteres = areasInteres; }
}