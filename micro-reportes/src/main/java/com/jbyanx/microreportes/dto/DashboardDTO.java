package com.jbyanx.microreportes.dto;

import java.util.List;
import java.util.Map;

public class DashboardDTO {
    private Integer totalVoluntarios;
    private Integer totalOrganizaciones;
    private Integer totalProyectos;
    private Integer totalInscripciones;
    private Map<String, Long> inscripcionesPorEstado;
    private Map<String, Long> proyectosPorCategoria;
    private List<Map<String, Object>> topProyectos;
    private List<Map<String, Object>> topVoluntarios;
    private Double tasaOcupacionPromedio;

    // Constructor vacío
    public DashboardDTO() {}

    // Getters y Setters
    public Integer getTotalVoluntarios() { return totalVoluntarios; }
    public void setTotalVoluntarios(Integer totalVoluntarios) { this.totalVoluntarios = totalVoluntarios; }

    public Integer getTotalOrganizaciones() { return totalOrganizaciones; }
    public void setTotalOrganizaciones(Integer totalOrganizaciones) { this.totalOrganizaciones = totalOrganizaciones; }

    public Integer getTotalProyectos() { return totalProyectos; }
    public void setTotalProyectos(Integer totalProyectos) { this.totalProyectos = totalProyectos; }

    public Integer getTotalInscripciones() { return totalInscripciones; }
    public void setTotalInscripciones(Integer totalInscripciones) { this.totalInscripciones = totalInscripciones; }

    public Map<String, Long> getInscripcionesPorEstado() { return inscripcionesPorEstado; }
    public void setInscripcionesPorEstado(Map<String, Long> inscripcionesPorEstado) { this.inscripcionesPorEstado = inscripcionesPorEstado; }

    public Map<String, Long> getProyectosPorCategoria() { return proyectosPorCategoria; }
    public void setProyectosPorCategoria(Map<String, Long> proyectosPorCategoria) { this.proyectosPorCategoria = proyectosPorCategoria; }

    public List<Map<String, Object>> getTopProyectos() { return topProyectos; }
    public void setTopProyectos(List<Map<String, Object>> topProyectos) { this.topProyectos = topProyectos; }

    public List<Map<String, Object>> getTopVoluntarios() { return topVoluntarios; }
    public void setTopVoluntarios(List<Map<String, Object>> topVoluntarios) { this.topVoluntarios = topVoluntarios; }

    public Double getTasaOcupacionPromedio() { return tasaOcupacionPromedio; }
    public void setTasaOcupacionPromedio(Double tasaOcupacionPromedio) { this.tasaOcupacionPromedio = tasaOcupacionPromedio; }
}