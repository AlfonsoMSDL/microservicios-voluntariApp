package com.inscripciones.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAlias;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetEstadoInscripcion(
        @JsonAlias String id,
        @JsonAlias String nombre,
        @JsonAlias String descripcion
) {
}
