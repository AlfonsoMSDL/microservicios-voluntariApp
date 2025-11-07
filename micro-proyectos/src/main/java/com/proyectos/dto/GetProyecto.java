package com.proyectos.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record GetProyecto(
        @JsonAlias String id,
        @JsonAlias String nombre,
        @JsonAlias String descripcion,
        @JsonAlias String ubicacion,
        @JsonAlias String requisitos,
        @JsonAlias String fecha_inicio,
        @JsonAlias String fecha_fin,
        @JsonAlias String voluntarios_requeridos,
        @JsonAlias GetCategoria categoria,
        @JsonAlias GetOrganizacion organizacion,
        @JsonAlias Long idOrganizacion
        
) {
}
