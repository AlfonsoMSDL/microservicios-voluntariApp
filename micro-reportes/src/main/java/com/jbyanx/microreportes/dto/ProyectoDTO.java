package com.jbyanx.microreportes.dto;

import java.util.Date;

public class ProyectoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String requisitos;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer voluntariosRequeridos;
    private Long idCategoria;
    private String nombreCategoria;
    private Long organizacionId;
    private String nombreOrganizacion;

    // Constructor vacío
    public ProyectoDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getRequisitos() { return requisitos; }
    public void setRequisitos(String requisitos) { this.requisitos = requisitos; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public Integer getVoluntariosRequeridos() { return voluntariosRequeridos; }
    public void setVoluntariosRequeridos(Integer voluntariosRequeridos) { this.voluntariosRequeridos = voluntariosRequeridos; }

    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    public Long getOrganizacionId() { return organizacionId; }
    public void setOrganizacionId(Long organizacionId) { this.organizacionId = organizacionId; }

    public String getNombreOrganizacion() { return nombreOrganizacion; }
    public void setNombreOrganizacion(String nombreOrganizacion) { this.nombreOrganizacion = nombreOrganizacion; }
}