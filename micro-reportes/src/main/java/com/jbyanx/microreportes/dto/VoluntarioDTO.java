package com.jbyanx.microreportes.dto;

public class VoluntarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String habilidades;
    private String experiencia;
    private String disponibilidad;
    private String areasInteres;

    // Constructor vacío
    public VoluntarioDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public String getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(String disponibilidad) { this.disponibilidad = disponibilidad; }

    public String getAreasInteres() { return areasInteres; }
    public void setAreasInteres(String areasInteres) { this.areasInteres = areasInteres; }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}