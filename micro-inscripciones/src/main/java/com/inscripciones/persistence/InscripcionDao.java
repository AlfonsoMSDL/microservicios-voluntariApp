package com.inscripciones.persistence;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.inscripciones.model.Inscripcion;

public class InscripcionDao {
    private final String INSERT = "INSERT INTO inscripciones (idProyecto, idVoluntario, motivacion, fechaInscripcion, idEstadoInscripcion) VALUE (?,?,?,?,?)";


    //CREAR
    public Inscripcion save(Inscripcion inscripcion){
        try (Connec conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.getGeneratedKeys)){
            
            stmt.setLong(1, inscripcion.getIdProyecto());
            stmt.setLong(2, inscripcion.getIdVoluntario());
            stmt.setString(3, inscripcion.getMotivacion());
            stmt.setDate(4, inscripcion.getFechaInscripcion());
            stmt.setLong(5, inscripcion.getEstadoInscripcion().getId());

            int Re
        }
    }
}
