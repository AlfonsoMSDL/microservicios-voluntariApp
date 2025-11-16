package com.inscripciones.persistence.fabrica;

import com.inscripciones.persistence.InscripcionDao;
import com.inscripciones.persistence.impl.InscripcionPostgresqlDao;

public class InscripcionFabrica {
    
    public static InscripcionDao getImplementacion(String tipoBD){ 
        switch (tipoBD) {
            case "postgresql":
                return new InscripcionPostgresqlDao();
            default:
                return new InscripcionPostgresqlDao();
                
        }
    }
}
