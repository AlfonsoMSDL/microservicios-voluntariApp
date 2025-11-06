package com.usuariosService.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetVoluntario(
        //Estos seran los datos que se mostraran en el cliente

        @JsonAlias Long id,
        @JsonAlias String nombre,
        @JsonAlias String nombreUsuario,

        @JsonAlias String apellido,
        @JsonAlias String correo,
        @JsonAlias String clave,
        @JsonAlias String telefono,
        @JsonAlias String habilidades,
        @JsonAlias String areas_interes,
        @JsonAlias String experiencia,
        @JsonAlias String disponibilidad



) {
}
