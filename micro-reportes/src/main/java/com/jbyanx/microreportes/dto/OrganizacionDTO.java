package com.jbyanx.microreportes.dto;

public class OrganizacionDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private String descripcion;
    private Long tipoOrganizacionId;
    private String tipoOrganizacionNombre;

    // Constructor vacío
    public OrganizacionDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Long getTipoOrganizacionId() { return tipoOrganizacionId; }
    public void setTipoOrganizacionId(Long tipoOrganizacionId) { this.tipoOrganizacionId = tipoOrganizacionId; }

    public String getTipoOrganizacionNombre() { return tipoOrganizacionNombre; }
    public void setTipoOrganizacionNombre(String tipoOrganizacionNombre) { this.tipoOrganizacionNombre = tipoOrganizacionNombre; }
}