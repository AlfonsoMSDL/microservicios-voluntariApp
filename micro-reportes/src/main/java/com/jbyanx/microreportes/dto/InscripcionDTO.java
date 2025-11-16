package com.jbyanx.microreportes.dto;

import java.util.Date;

public class InscripcionDTO {
    private Long id;
    private Long idProyecto;
    private Long idVoluntario;
    private String motivacion;
    private Date fechaInscripcion;
    private Long idEstado;
    private String nombreEstado;

    // Constructor vacío
    public InscripcionDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdProyecto() { return idProyecto; }
    public void setIdProyecto(Long idProyecto) { this.idProyecto = idProyecto; }

    public Long getIdVoluntario() { return idVoluntario; }
    public void setIdVoluntario(Long idVoluntario) { this.idVoluntario = idVoluntario; }

    public String getMotivacion() { return motivacion; }
    public void setMotivacion(String motivacion) { this.motivacion = motivacion; }

    public Date getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(Date fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    public Long getIdEstado() { return idEstado; }
    public void setIdEstado(Long idEstado) { this.idEstado = idEstado; }

    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
}