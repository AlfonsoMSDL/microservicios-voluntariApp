package com.participaciones.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetParticipacion(

    @JsonAlias Long id,
    @JsonAlias GetVoluntario voluntario,
    @JsonAlias GetProyecto proyecto,
    @JsonAlias Date fechaInicio,
    @JsonAlias Date fechaFin,
    @JsonAlias int horasRealizadas

) {

}
