package com.jbyanx.microreportes.dto;

public class ReporteOrganizacionDTO {
    private Long idOrganizacion;
    private String nombreOrganizacion;
    private String correo;
    private String telefono;
    private String descripcion;
    private String tipoOrganizacion;
    private Integer totalProyectos;
    private Integer proyectosActivos;
    private Integer totalVoluntariosInscritos;
    private Integer voluntariosActivos;

    // Constructor vacío
    public ReporteOrganizacionDTO() {}

    // Getters y Setters
    public Long getIdOrganizacion() { return idOrganizacion; }
    public void setIdOrganizacion(Long idOrganizacion) { this.idOrganizacion = idOrganizacion; }

    public String getNombreOrganizacion() { return nombreOrganizacion; }
    public void setNombreOrganizacion(String nombreOrganizacion) { this.nombreOrganizacion = nombreOrganizacion; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipoOrganizacion() { return tipoOrganizacion; }
    public void setTipoOrganizacion(String tipoOrganizacion) { this.tipoOrganizacion = tipoOrganizacion; }

    public Integer getTotalProyectos() { return totalProyectos; }
    public void setTotalProyectos(Integer totalProyectos) { this.totalProyectos = totalProyectos; }

    public Integer getProyectosActivos() { return proyectosActivos; }
    public void setProyectosActivos(Integer proyectosActivos) { this.proyectosActivos = proyectosActivos; }

    public Integer getTotalVoluntariosInscritos() { return totalVoluntariosInscritos; }
    public void setTotalVoluntariosInscritos(Integer totalVoluntariosInscritos) { this.totalVoluntariosInscritos = totalVoluntariosInscritos; }

    public Integer getVoluntariosActivos() { return voluntariosActivos; }
    public void setVoluntariosActivos(Integer voluntariosActivos) { this.voluntariosActivos = voluntariosActivos; }
}