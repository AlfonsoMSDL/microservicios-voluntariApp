package com.proyectos.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetOrganizacion(
        //Estos seran los datos que se mostraran en el cliente
        @JsonAlias String id,
        @JsonAlias String nombre,
        @JsonAlias String nombreUsuario,
        @JsonAlias String correo,
        @JsonAlias String clave,
        @JsonAlias String telefono,
        @JsonAlias String descripcion,
        @JsonAlias GetTipoOrganizacion tipoOrganizacion
) {
}
