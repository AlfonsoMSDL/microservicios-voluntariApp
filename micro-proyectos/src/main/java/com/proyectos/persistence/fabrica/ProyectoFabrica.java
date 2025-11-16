package com.proyectos.persistence.fabrica;

import com.proyectos.persistence.ProyectoDao;
import com.proyectos.persistence.impl.ProyectoPostgresqlDao;

public class ProyectoFabrica {

    public static ProyectoDao getImplementacion(String tipoBD){ 
        switch (tipoBD) {
            case "postgresql":
                return new ProyectoPostgresqlDao();
            default:
                return new ProyectoPostgresqlDao();
                
        }
    }

}
