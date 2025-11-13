package com.inscripciones.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetVoluntario(
    @JsonAlias Long id,
    @JsonAlias String nombre,
    @JsonAlias String apellido,
    @JsonAlias String nombreUsuario,
    @JsonAlias String correo,
    @JsonAlias String clave,
    @JsonAlias String telefono
) {

}
