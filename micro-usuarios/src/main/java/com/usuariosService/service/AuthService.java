package com.usuariosService.service;

import com.usuariosService.dto.GetUsuario;
import com.usuariosService.mapper.GenericMapper;
import com.usuariosService.persistence.*;
import com.usuariosService.persistence.fabrica.UsuarioFabrica;
import com.usuariosService.persistence.impl.UsuarioPostgresqlDao;
import com.usuariosService.model.*;
import org.apache.log4j.Logger;

import java.util.Optional;

public class AuthService {
    private final UsuarioDao usuarioDao;
    private GenericMapper<GetUsuario,Usuario> mapperUsuario;

    Logger log = Logger.getLogger(AuthService.class);

    public AuthService() {
        this.usuarioDao = UsuarioFabrica.getImplementacion("postgresql");
        this.mapperUsuario = new GenericMapper<>();
    }
    public GetUsuario Login (String correo, String clave){

        Usuario usuario = usuarioDao.findByEmail(correo).get();
        if(usuario != null){
            if(clave.equals(usuario.getClave())){

                return mapperUsuario.toDto(usuario, GetUsuario.class);
            }
        }
        return null;
    }


}
