package com.usuariosService.persistence.fabrica;

import com.usuariosService.persistence.UsuarioDao;
import com.usuariosService.persistence.impl.UsuarioPostgresqlDao;

public class UsuarioFabrica {

    public static UsuarioDao getImplementacion(String tipoBD){ 
        switch (tipoBD) {
            case "postgresql":
                return new UsuarioPostgresqlDao();
            default:
                return new UsuarioPostgresqlDao();
                
        }
    }

}
