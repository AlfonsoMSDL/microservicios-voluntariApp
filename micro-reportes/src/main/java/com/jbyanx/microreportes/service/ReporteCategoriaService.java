package com.jbyanx.microreportes.service;

import com.jbyanx.microreportes.client.MicroserviceClient;
import com.jbyanx.microreportes.config.ServiceConfig;
import com.jbyanx.microreportes.dto.*;

import java.util.*;
import java.util.stream.Collectors;

public class ReporteCategoriaService {

    private final MicroserviceClient client;
    private final String proyectosServiceUrl;

    public ReporteCategoriaService() {
        this.client = new MicroserviceClient();
        this.proyectosServiceUrl = ServiceConfig.getProyectosServiceUrl();
    }

    /**
     * Genera reporte de categorías con sus proyectos
     */
    public Map<String, Object> generarReporteCategorias() {
        Map<String, Object> reporte = new HashMap<>();

        try {
            // 1. Obtener todas las categorías
            String urlCategorias = proyectosServiceUrl + "/api/categorias";
            List<CategoriaDTO> categorias = client.getList(urlCategorias, CategoriaDTO[].class);

            // 2. Obtener todos los proyectos
            String urlProyectos = proyectosServiceUrl + "/api/proyectos";
            List<ProyectoDTO> proyectos = client.getList(urlProyectos, ProyectoDTO[].class);

            // 3. Contar proyectos por categoría
            Map<String, Long> proyectosPorCategoria = proyectos.stream()
                    .collect(Collectors.groupingBy(
                            p -> p.getNombreCategoria() != null ? p.getNombreCategoria() : "Sin categoría",
                            Collectors.counting()
                    ));

            // 4. Crear lista de categorías con conteos
            List<Map<String, Object>> categoriasConConteo = categorias.stream()
                    .map(c -> {
                        Map<String, Object> catMap = new HashMap<>();
                        catMap.put("id", c.getId());
                        catMap.put("nombre", c.getNombre());
                        catMap.put("descripcion", c.getDescripcion());
                        catMap.put("totalProyectos", proyectosPorCategoria.getOrDefault(c.getNombre(), 0L));
                        return catMap;
                    })
                    .sorted((a, b) -> Long.compare(
                            (Long) b.get("totalProyectos"),
                            (Long) a.get("totalProyectos")
                    ))
                    .collect(Collectors.toList());

            reporte.put("categorias", categoriasConConteo);
            reporte.put("totalCategorias", categorias.size());
            reporte.put("totalProyectos", proyectos.size());

            // 5. Categoría más popular
            if (!categoriasConConteo.isEmpty()) {
                reporte.put("categoriaMasPopular", categoriasConConteo.get(0));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte de categorías", e);
        }

        return reporte;
    }

    /**
     * Genera reporte detallado de una categoría específica
     */
    public Map<String, Object> generarReporteCategoria(Long idCategoria) {
        Map<String, Object> reporte = new HashMap<>();

        try {
            // Obtener la categoría
            String urlCategoria = proyectosServiceUrl + "/api/categorias?action=getById&id=" + idCategoria;
            CategoriaDTO categoria = client.get(urlCategoria, CategoriaDTO.class);

            if (categoria == null) {
                throw new RuntimeException("Categoría no encontrada");
            }

            // Obtener proyectos de la categoría
            String urlProyectos = proyectosServiceUrl + "/api/proyectos";
            List<ProyectoDTO> todosProyectos = client.getList(urlProyectos, ProyectoDTO[].class);

            List<ProyectoDTO> proyectosCategoria = todosProyectos.stream()
                    .filter(p -> p.getNombreCategoria() != null &&
                            p.getNombreCategoria().equals(categoria.getNombre()))
                    .collect(Collectors.toList());

            reporte.put("categoria", categoria);
            reporte.put("proyectos", proyectosCategoria);
            reporte.put("totalProyectos", proyectosCategoria.size());

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte de categoría: " + idCategoria, e);
        }

        return reporte;
    }
}