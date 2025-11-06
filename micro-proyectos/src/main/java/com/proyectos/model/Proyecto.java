package com.proyectos.model;

import com.proyectos.model.Categoria;

import java.sql.Date;

public class Proyecto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String requisitos;
    private Date fecha_inicio;
    private Date fecha_fin;
    private Integer voluntarios_requeridos;
    private Categoria categoria;
    private GetOrganizacion organizacion;
    private Long idOrganizacion;

    //Para mostrar
    public Proyecto(Long id, String nombre, String descripcion, String ubicacion, String requisitos, Date fecha_inicio, Date fecha_fin, Integer voluntarios_requeridos, Categoria categoria, Long idOrganizacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.requisitos = requisitos;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.voluntarios_requeridos = voluntarios_requeridos;
        this.categoria = categoria;
        this.idOrganizacion = idOrganizacion;
    }


    //Para insertar
    public Proyecto(String nombre, String descripcion, String ubicacion, String requisitos, Date fecha_inicio, Date fecha_fin, Integer voluntarios_requeridos, Categoria categoria, Long idOrganizacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.requisitos = requisitos;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.voluntarios_requeridos = voluntarios_requeridos;
        this.categoria = categoria;
        this.idOrganizacion = idOrganizacion;
    }

    //Para actualizar
    public Proyecto(Long id, String nombre, String descripcion, String ubicacion, String requisitos, Date fecha_inicio, Date fecha_fin, Integer voluntarios_requeridos, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.requisitos = requisitos;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.voluntarios_requeridos = voluntarios_requeridos;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Integer getVoluntarios_requeridos() {
        return voluntarios_requeridos;
    }

    public void setVoluntarios_requeridos(Integer voluntarios_requeridos) {
        this.voluntarios_requeridos = voluntarios_requeridos;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public GetOrganizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(GetOrganizacion organizacion) {
        this.organizacion = organizacion;
    }

    public Long getIdOrganizacion() {
        return idOrganizacion;
    }
    public void setIdOrganizacion(Long idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", requisitos='" + requisitos + '\'' +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_fin=" + fecha_fin +
                ", voluntarios_requeridos=" + voluntarios_requeridos +
                ", categoria=" + categoria +
                ", organizacion=" + organizacion +
                '}';
    }
}
