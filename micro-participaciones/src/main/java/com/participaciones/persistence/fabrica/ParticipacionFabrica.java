package com.participaciones.persistence.fabrica;

import com.participaciones.persistence.ParticipacionDao;
import com.participaciones.persistence.impl.ParticipacionPostgresqlDao;

public class ParticipacionFabrica {

    public static ParticipacionDao getImplementacion(String tipoBD){ 
        switch (tipoBD) {
            case "postgresql":
                return new ParticipacionPostgresqlDao();
            default:
                return new ParticipacionPostgresqlDao();
                
        }
    }
}
