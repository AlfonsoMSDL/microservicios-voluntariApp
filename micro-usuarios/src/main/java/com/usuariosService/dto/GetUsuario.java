package com.usuariosService.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)

public record GetUsuario(
        //Estos seran los datos que se mostraran en el cliente
        @JsonAlias Long id,
        @JsonAlias String correo,
        @JsonAlias GetRol rol,
        @JsonAlias String nombreUsuario
){}