package com.proyectos.persistence;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConexion {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    static {
        String host = System.getenv("MONGO_HOST");
        String port = System.getenv("MONGO_PORT");
        String dbName = System.getenv("MONGO_DB");

        if (host == null || port == null || dbName == null) {
            throw new RuntimeException("Variables de entorno de Mongo no configuradas correctamente");
        }

        String uri = "mongodb://" + host + ":" + port;
        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase(dbName);
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
