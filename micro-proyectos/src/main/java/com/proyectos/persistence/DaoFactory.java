package com.proyectos.persistence;

import com.proyectos.persistence.dao.*;

public class DaoFactory {

    public static CategoriaDao getCategoriaDao() {
        String tipo = System.getenv("DB_TYPE"); // puede ser "POSTGRES" o "MONGO"

        if ("MONGO".equalsIgnoreCase(tipo)) {
            return new CategoriaDaoMongo();
        } else {
            return new CategoriaDaoPostgres();
        }
    }
}
