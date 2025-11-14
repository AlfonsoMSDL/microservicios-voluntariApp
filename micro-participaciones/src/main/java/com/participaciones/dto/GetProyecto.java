package com.participaciones.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetProyecto(
    @JsonAlias Long id,
    @JsonAlias String nombre,
    @JsonAlias String descripcion,
    @JsonAlias String ubicacion,
    @JsonAlias String fecha_inicio,
    @JsonAlias String fecha_fin,
    @JsonAlias String voluntarios_requeridos,
    @JsonAlias String idOrganizacion
) {

}
