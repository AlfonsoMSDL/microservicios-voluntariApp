package com.inscripciones.model;

public class Inscripcion {
    private Long id;
    private Long idProyecto;
    private Long idVoluntario;
    private String motivacion;
    private Date fechaInscripcion;
    private EstadoInscripcion estadoInscripcion;
    private GetProyecto proyecto;
    private GetVoluntario voluntario;

    public Inscripcion(Long id, Long idProyecto, Long idVoluntario, String motivacion, Date fechaInscripcion, EstadoInscripcion estadoInscripcion){
        this.id = id;
        this.idProyecto = idProyecto;
        this.idVoluntario = idVoluntario;
        this.motivacion = motivacion;
        this.fechaInscripcion = fechaInscripcion;
        this.estadoInscripcion = estadoInscripcion;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdProyecto() {
        return idProyecto;
    }
    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }
    public Long getIdVoluntario() {
        return idVoluntario;
    }
    public void setIdVoluntario(Long idVoluntario) {
        this.idVoluntario = idVoluntario;
    }
    public String getMotivacion() {
        return motivacion;
    }
    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
    }
    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }
    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
    public EstadoInscripcion getEstadoInscripcion() {
        return estadoInscripcion;
    }
    public void setEstadoInscripcion(EstadoInscripcion estadoInscripcion) {
        this.estadoInscripcion = estadoInscripcion;
    }
    
    
}
