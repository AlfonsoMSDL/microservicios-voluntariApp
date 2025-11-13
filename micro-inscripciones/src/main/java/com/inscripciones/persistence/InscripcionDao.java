package com.inscripciones.persistence;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.inscripciones.model.Inscripcion;

public class InscripcionDao {
    private final String INSERT = "INSERT INTO inscripciones (proyecto_id, voluntario_id, motivacion, fecha_inscripcion, id_estado) VALUE (?,?,?,?,?)";
    private final String UPDATE = "UPDATE inscripciones SET motivacion = ?, fecha_inscripcion = ?, id_estado = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM inscripciones WHERE id = ?";
    private final String SELECT_BY_PROYECTO = "SELECT * FROM inscripciones WHERE proyecto_id = ?";
    private final String SELECT_BY_VOLUNTARIO = "SELECT * FROM inscripciones WHERE voluntario_id = ?";
    private final String SELECT_BY_ID = "SELECT * FROM inscripciones WHERE id = ?";

    //CREAR
    public Inscripcion save(Inscripcion inscripcion){
        try (Connec conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.getGeneratedKeys)){
            
            stmt.setLong(1, inscripcion.getIdProyecto());
            stmt.setLong(2, inscripcion.getIdVoluntario());
            stmt.setString(3, inscripcion.getMotivacion());
            stmt.setDate(4, inscripcion.getFechaInscripcion());
            stmt.setLong(5, inscripcion.getEstadoInscripcion().getId());

            int registrosAfectados = stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                inscripcion.setId(rs.getLong(1));
            }

            Conexion.close(conn);
            Conexion.close(rs);
            Conexion.close(stmt);

            return registrosAfectados != 0 ? inscripcion : null;
        }catch (SQLException e){
            throw new RuntimeException("Error al crear inscripcion",e);
        }
    }

    //ACTUALIZAR
    public Inscripcion update(Inscripcion inscripcion){
        try (Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE)){
            
            stmt.setString(1, inscripcion.getMotivacion());
            stmt.setDate(2, inscripcion.getFechaInscripcion());
            stmt.setLong(3, inscripcion.getEstadoInscripcion().getId());

            stmt.setLong(4, inscripcion.getId());

            int registrosAfectados = stmt.executeUpdate();
             
            Conexion.close(conn);
            Conexion.close(stmt);

            return registrosAfectados > 0 ? inscripcion : null;

        }catch (SQLException e){
            throw new RuntimeException("Error al actualizar la inscripción",e);
        }
    }

    //BUSCAR POR ID PROYECTO
    public List<inscripcion> findAllInscripcionesByIdProyecto(Long id){
        List <inscripcion> inscripciones = new ArrayList<>();

        try(Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_PROYECTO)){
        
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Long id = rs.getLong("id");
                Long idProyecto = rs.getLong("proyecto_id");
                Long idVoluntario = rs.getLong("voluntario_id");
                String motivacion = rs. getString("motivacion");
                Date fechaInscripcion = rs.getDate("fecha_inscripcion");
                EstadoInscripcion estadoInscripcion = (new EstadoInscripcionDao()).findByID(rs.getLong("id_estado")).get();

                Inscripcion inscripcion = new Inscripcion(id, idProyecto, idVoluntario, motivacion, fechaInscripcion, estadoInscripcion);
                inscripciones.add(inscripcion);
            }

            Conexion.close(conn);
            Conexion.close(stmt);
            Conexion.close(rs);
        }catch (SQLException e){
            throw new RuntimeException("Error al listar inscripciones por idProyecto",e);
        }
        return inscripciones;
    }

    //BUSCAR POR ID VOLUNTARIO
    public List<inscripcion> findAllInscripcionesByIdVoluntario(Long id){
        List<inscripcion> inscripciones = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_VOLUNTARIO)){

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                Long idProyecto = rs.getLong("proyecto_id");
                Long idVoluntario = rs.getLong("voluntario_id");
                String motivacion = rs. getString("motivacion");
                Date fechaInscripcion = rs.getDate("fecha_inscripcion");
                EstadoInscripcion estadoInscripcion = (new EstadoInscripcionDao()).findByID(rs.getLong("id_estado")).get();

                Inscripcion inscripcion = new Inscripcion(id, idProyecto, idVoluntario, motivacion, fechaInscripcion, estadoInscripcion);
                inscripciones.add(inscripcion);
            }
            Conexion.close(conn);
            Conexion.close(stmt);
            Conexion.close(rs);
        }catch(SQLException e){
            throw new RuntimeException("Error al listar inscripciones por idVoluntario",e);
        }
        return inscripciones;
    }

    //BUSCAR POR ID
    public Optional<Inscripcion> findById(Long id){
        Inscripcion inscripcion = null;
        try (Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)){
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Long id = rs.getLong("id");
                Long idProyecto = rs.getLong("proyecto_id");
                Long idVoluntario = rs.getLong("voluntario_id");
                String motivacion = rs. getString("motivacion");
                Date fechaInscripcion = rs.getDate("fecha_inscripcion");
                EstadoInscripcion estadoInscripcion = (new EstadoInscripcionDao()).findByID(rs.getLong("id_estado")).get();

                inscripcion = new Inscripcion(id, idProyecto, idVoluntario, motivacion, fechaInscripcion, estadoInscripcion);
            }

            Conexion.close(conn);
            Conexion.close(stmt);
            Conexion.close(rs);
        }catch(SQLException e){
            throw new RuntimeException("Error al buscar inscripcion por id",e);
        }

        return Optional.ofNullable(inscripcion);
    }

    //ELIMINAR
    public boolean delete(Long id){
        try (Connection conn = Conexion.getConnection(;
            PreparedStatement stmt = conn.prepareStatement(DELETE))){
            
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        }catch (SQLException e){
            throw new RuntimeException("Error al eliminar inscripción",e);
        }
    }
}
