package com.proyectos.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetCategoria(
        @JsonAlias String id,
        @JsonAlias String nombre
) {
}
