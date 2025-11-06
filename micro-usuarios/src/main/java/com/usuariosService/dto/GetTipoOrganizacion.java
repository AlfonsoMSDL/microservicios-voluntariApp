package com.usuariosService.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetTipoOrganizacion(
        @JsonAlias Long id,
        @JsonAlias String nombre,
        @JsonAlias String descripcion
) {
}
