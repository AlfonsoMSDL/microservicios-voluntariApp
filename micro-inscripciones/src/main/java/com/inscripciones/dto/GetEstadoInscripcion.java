package com.inscripciones.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetEstadoInscripcion(
        @JsonAlias String id,
        @JsonAlias String nombre,
        @JsonAlias Sring descripcion
) {
}
