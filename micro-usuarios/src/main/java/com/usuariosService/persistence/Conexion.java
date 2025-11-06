package com.usuariosService.persistence;

import java.sql.*;

public class Conexion {
    private static final String url= "jdbc:postgresql://"+System.getenv("DB_HOST")+":5432/"+System.getenv("DB_NAME");
    private static final String user= System.getenv("DB_USER");
    private static final String password = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver de PostgreSQL", e);
        }
        return  DriverManager.getConnection(url,user,password);
    }

    public static void close(Connection cn) throws SQLException {
        cn.close();
    }

    public static void close(PreparedStatement stmt) throws SQLException {
        stmt.close();
    }

    public static void close(ResultSet rs) throws SQLException {
        rs.close();
    }



}
