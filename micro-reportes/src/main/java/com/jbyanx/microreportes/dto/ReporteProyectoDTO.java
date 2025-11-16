package com.jbyanx.microreportes.dto;

import java.util.Date;

public class ReporteProyectoDTO {
    private Long idProyecto;
    private String nombreProyecto;
    private String nombreOrganizacion;
    private String nombreCategoria;
    private Integer voluntariosRequeridos;
    private Integer voluntariosInscritos;
    private Integer inscripcionesAprobadas;
    private Integer inscripcionesPendientes;
    private Double porcentajeOcupacion;
    private String ubicacion;
    private Date fechaInicio;
    private Date fechaFin;

    // Constructor vacío
    public ReporteProyectoDTO() {}

    // Getters y Setters
    public Long getIdProyecto() { return idProyecto; }
    public void setIdProyecto(Long idProyecto) { this.idProyecto = idProyecto; }

    public String getNombreProyecto() { return nombreProyecto; }
    public void setNombreProyecto(String nombreProyecto) { this.nombreProyecto = nombreProyecto; }

    public String getNombreOrganizacion() { return nombreOrganizacion; }
    public void setNombreOrganizacion(String nombreOrganizacion) { this.nombreOrganizacion = nombreOrganizacion; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    public Integer getVoluntariosRequeridos() { return voluntariosRequeridos; }
    public void setVoluntariosRequeridos(Integer voluntariosRequeridos) { this.voluntariosRequeridos = voluntariosRequeridos; }

    public Integer getVoluntariosInscritos() { return voluntariosInscritos; }
    public void setVoluntariosInscritos(Integer voluntariosInscritos) { this.voluntariosInscritos = voluntariosInscritos; }

    public Integer getInscripcionesAprobadas() { return inscripcionesAprobadas; }
    public void setInscripcionesAprobadas(Integer inscripcionesAprobadas) { this.inscripcionesAprobadas = inscripcionesAprobadas; }

    public Integer getInscripcionesPendientes() { return inscripcionesPendientes; }
    public void setInscripcionesPendientes(Integer inscripcionesPendientes) { this.inscripcionesPendientes = inscripcionesPendientes; }

    public Double getPorcentajeOcupacion() { return porcentajeOcupacion; }
    public void setPorcentajeOcupacion(Double porcentajeOcupacion) { this.porcentajeOcupacion = porcentajeOcupacion; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
}