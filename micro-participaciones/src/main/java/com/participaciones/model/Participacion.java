package com.participaciones.model;

import java.sql.Date;

import com.participaciones.dto.GetProyecto;
import com.participaciones.dto.GetVoluntario;

public class Participacion {

    private Long id;
    private GetVoluntario voluntario;
    private Long idVoluntario;
    private GetProyecto proyecto;
    private Long idProyecto;
    private Date fechaInicio;
    private Date fechaFin;
    private int horasRealizadas;


    //Para obtener
    public Participacion(Long id, GetVoluntario voluntario, GetProyecto proyecto, Date fechaInicio, Date fechaFin, int horasRealizadas) {
        this.id = id;
        this.voluntario = voluntario;
        this.proyecto = proyecto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horasRealizadas = horasRealizadas;
    }


    //Para insertar
    public Participacion(Long idVoluntario, Long idProyecto, Date fechaInicio,
            int horasRealizadas) {
        this.idVoluntario = idVoluntario;
        this.idProyecto = idProyecto;
        this.fechaInicio = fechaInicio;
        this.horasRealizadas = horasRealizadas;
    }

    

    //Para actualizar
    public Participacion(Long id, Long idVoluntario, Long idProyecto, Date fechaInicio, Date fechaFin,
            int horasRealizadas) {
        this.id = id;
        this.idVoluntario = idVoluntario;
        this.idProyecto = idProyecto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horasRealizadas = horasRealizadas;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public GetVoluntario getVoluntario() {
        return voluntario;
    }


    public void setVoluntario(GetVoluntario voluntario) {
        this.voluntario = voluntario;
    }


    public GetProyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(GetProyecto proyecto) {
        this.proyecto = proyecto;
    }


    public Date getFechaInicio() {
        return fechaInicio;
    }


    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }


    public Date getFechaFin() {
        return fechaFin;
    }


    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }


    public int getHorasRealizadas() {
        return horasRealizadas;
    }


    public void setHorasRealizadas(int horasRealizadas) {
        this.horasRealizadas = horasRealizadas;
    }


    public Long getIdVoluntario() {
        return idVoluntario;
    }


    public void setIdVoluntario(Long idVoluntario) {
        this.idVoluntario = idVoluntario;
    }


    public Long getIdProyecto() {
        return idProyecto;
    }


    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }


    @Override
    public String toString() {
        return "Participacion [id=" + id + ", idVoluntario=" + idVoluntario + ", idProyecto=" + idProyecto
                + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", horasRealizadas=" + horasRealizadas
                + "]";
    }

    

    

    

    

    

}
