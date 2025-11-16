package com.inscripciones.persistence.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.inscripciones.model.EstadoInscripcion;
import com.inscripciones.model.Inscripcion;
import com.inscripciones.persistence.Conexion;
import com.inscripciones.persistence.EstadoInscripcionDao;
import com.inscripciones.persistence.InscripcionDao;

public class InscripcionPostgresqlDao implements InscripcionDao {
    private final String INSERT = "INSERT INTO inscripciones (voluntario_id, proyecto_id, motivacion, fecha_inscripcion, id_estado) VALUES (?,?,?,?,?)";
    private final String UPDATE = "UPDATE inscripciones SET motivacion = ? WHERE id = ?";
    private final String UPDATE_ESTADO = "UPDATE inscripciones SET id_estado = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM inscripciones WHERE id = ?";
    private final String SELECT_BY_PROYECTO = "SELECT * FROM inscripciones WHERE proyecto_id = ? AND id_estado = 1";
    private final String SELECT_BY_VOLUNTARIO = "SELECT * FROM inscripciones WHERE voluntario_id = ?";
    private final String SELECT_BY_ID = "SELECT * FROM inscripciones WHERE id = ?";

    //CREAR
    public Inscripcion save(Inscripcion inscripcion){
        Connection conn=null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setLong(1, inscripcion.getIdVoluntario());
            stmt.setLong(2, inscripcion.getIdProyecto());
            stmt.setString(3, inscripcion.getMotivacion());
            stmt.setDate(4, inscripcion.getFechaInscripcion());
            stmt.setLong(5, inscripcion.getEstadoInscripcion().getId());

            int registrosAfectados = stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                inscripcion.setId(rs.getLong(1));
            }

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

            return registrosAfectados != 0 ? inscripcion : null;
        }catch (SQLException e){
            throw new RuntimeException("Error al crear inscripcion",e);
        }
    }

    //ACTUALIZAR INSCRIPCION
    public Inscripcion update(Inscripcion inscripcion){
        Connection conn=null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(UPDATE);
            
            stmt.setString(1, inscripcion.getMotivacion());
            stmt.setLong(2, inscripcion.getId());

            int registrosAfectados = stmt.executeUpdate();
             
            Conexion.close(stmt);
            Conexion.close(conn);

            return registrosAfectados > 0 ? inscripcion : null;

        }catch (SQLException e){
            throw new RuntimeException("Error al actualizar la inscripción",e);
        }
    }

    //ACTUALIZAR ESTADO
    public Inscripcion updateEstado(Inscripcion inscripcion){
        Connection conn=null;
        PreparedStatement stmt = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(UPDATE_ESTADO);

            stmt.setLong(1, inscripcion.getEstadoInscripcion().getId());
            stmt.setLong(2, inscripcion.getId());

            int registrosAfectados = stmt.executeUpdate();

            Conexion.close(stmt);
            Conexion.close(conn);

            return registrosAfectados > 0 ? inscripcion : null;
        }catch(SQLException e){
            throw new RuntimeException("Error al actualizar estado inscripcion" + e.getMessage(), e);
        }
    }


    //BUSCAR POR ID PROYECTO
    public List<Inscripcion> findAllInscripcionesByIdProyecto(Long id){
        Connection conn=null;
        PreparedStatement stmt=null;
        ResultSet rs = null;
        List <Inscripcion> inscripciones = new ArrayList<>();
        Inscripcion inscripcion = null;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SELECT_BY_PROYECTO);
        
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while (rs.next()){
                Long idInscripcion = rs.getLong("id");
                Long idProyecto = rs.getLong("proyecto_id");
                Long idVoluntario = rs.getLong("voluntario_id");
                String motivacion = rs. getString("motivacion");
                Date fechaInscripcion = rs.getDate("fecha_inscripcion");
                EstadoInscripcion estadoInscripcion = (new EstadoInscripcionDao()).findByID(rs.getLong("id_estado")).get();

                inscripcion = new Inscripcion(idInscripcion, idProyecto, idVoluntario, motivacion, fechaInscripcion, estadoInscripcion);
                inscripciones.add(inscripcion);
            }

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }catch (SQLException e){
            throw new RuntimeException("Error al listar inscripciones por idProyecto",e);
        }
        return inscripciones;
    }

    //BUSCAR POR ID VOLUNTARIO
    public List<Inscripcion> findAllInscripcionesByIdVoluntario(Long id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Inscripcion> inscripciones = new ArrayList<>();
        Inscripcion inscripcion = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SELECT_BY_VOLUNTARIO);

            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while(rs.next()){
                Long idInscripcion = rs.getLong("id");
                Long idProyecto = rs.getLong("proyecto_id");
                Long idVoluntario = rs.getLong("voluntario_id");
                String motivacion = rs. getString("motivacion");
                Date fechaInscripcion = rs.getDate("fecha_inscripcion");
                EstadoInscripcion estadoInscripcion = (new EstadoInscripcionDao()).findByID(rs.getLong("id_estado")).get();

                inscripcion = new Inscripcion(idInscripcion, idProyecto, idVoluntario, motivacion, fechaInscripcion, estadoInscripcion);
                inscripciones.add(inscripcion);
            }
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }catch(SQLException e){
            throw new RuntimeException("Error al listar inscripciones por idVoluntario",e);
        }
        return inscripciones;
    }

    //BUSCAR POR ID
    public Optional<Inscripcion> findById(Long id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Inscripcion inscripcion = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SELECT_BY_ID);
            
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            if(rs.next()){
                Long idInscripcion = rs.getLong("id");
                Long idProyecto = rs.getLong("proyecto_id");
                Long idVoluntario = rs.getLong("voluntario_id");
                String motivacion = rs. getString("motivacion");
                Date fechaInscripcion = rs.getDate("fecha_inscripcion");
                EstadoInscripcion estadoInscripcion = (new EstadoInscripcionDao()).findByID(rs.getLong("id_estado")).get();

                inscripcion = new Inscripcion(idInscripcion, idProyecto, idVoluntario, motivacion, fechaInscripcion, estadoInscripcion);
            }

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }catch(SQLException e){
            throw new RuntimeException("Error al buscar inscripcion por id",e);
        }

        return Optional.ofNullable(inscripcion);
    }

    //ELIMINAR
    public boolean delete(Long id){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();

            stmt = conn.prepareStatement(DELETE);
            
            stmt.setLong(1, id);
            int registrosAfectados = stmt.executeUpdate();

            Conexion.close(stmt);
            Conexion.close(conn);
            return registrosAfectados > 0;

        }catch (SQLException e){
            throw new RuntimeException("Error al eliminar inscripción",e);
        }
    }
}
