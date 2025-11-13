package com.inscripciones.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetInscripcion(
    @JsonAlias String id,
    @JsonAlias String motivacion,
    @JsonAlias String fecha_inscripcion,
    @JsonAlias GetEstadoInscripcion estadoInscripcion,
    @JsonAlias GetProyecto proyecto,
    @JsonAlias GetVoluntario voluntario
) {

}
