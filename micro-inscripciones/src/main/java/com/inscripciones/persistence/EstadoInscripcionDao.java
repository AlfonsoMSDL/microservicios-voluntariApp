package com.inscripciones.persistence;

import java.beans.Statement;
import java.lang.StackWalker.Option;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import com.inscripciones.model.EstadoInscripcion;

public class EstadoInscripcionDao {
    private final String INSERT = "INSERT INTO estados_inscripcion (nombre, descripcion) VALEUS (?,?)";
    private final String SELECT_ALL = "SELECT * FROM estatados_inscripcion";
    private final String SELECT_BY_ID = "SELECT * FROM estados_inscripcion WHERE id = ?";
    private final String SELECT_BY_NOMBRE = "SELECT * FROM estados_inscripcion WHERE nombre = ?";
    private final String UPDATE = "UPDATE estados_inscripcion SET nombre = ?, descripcion = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM estadosInscripcion WHERE id = ?";

    //CREATE
    public EstadoInscripcion save(EstadoInscripcion estadoInscripcion){
        try(Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT,java.sql.Statement.RETURN_GENERATED_KEYS)){
            
            stmt.setString(1, estadoInscripcion.getNombre());
            stmt.setString(2, estadoInscripcion.getDescripcion());

            int registrosAfectados = stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                estadoInscripcion.setId(rs.getLong(1));
            }

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

            return registrosAfectados !=0 ? estadoInscripcion : null;

        }catch (SQLException e){
            throw new RuntimeException("Error al guardar el estado de inscripcion",e);
        }
    }

    //READ ALL
    public List<EstadoInscripcion> findAll(){
        List<EstadoInscripcion> estadosInscripcion = new ArrayList<>();
        try(Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = stmt.executeQuery()){
            
            while(rs.next()){
                EstadoInscripcion estadoInscripcion = new EstadoInscripcion(
                    rs.getLong("id"), 
                    rs.getString("nombre"), 
                    rs.getString("descripcion"));

                estadosInscripcion.add(estadoInscripcion);
            }

            Conexion.close(conn);
            Conexion.close(stmt);
            Conexion.close(rs);

        }catch(SQLException e){
            throw new RuntimeException("Error al listar todas los estados de inscripcion",e);
        }

        return estadosInscripcion;
    }

    //READ BY ID
    public Optional<EstadoInscripcion> findByID(Long id){

        EstadoInscripcion estadoInscripcion = null;

        try(Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)){
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                estadoInscripcion = new EstadoInscripcion(
                    rs.getLong("id"), 
                    rs.getString("nombre"),
                    rs.getString("descripcion"));
            }

            Conexion.close(conn);
            Conexion.close(stmt);
            Conexion.close(rs);

            
        }catch(SQLException e){
            throw new RuntimeException("Error al buscar estado de inscripcion por id",e);
        }

        return Optional.ofNullable(estadoInscripcion);
    }

    // READ BY NOMBRE
    public Optional<EstadoInscripcion> findByNombre(String nombre){

        EstadoInscripcion estadoInscripcion = null;

        try (Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_NOMBRE)) {
            
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                estadoInscripcion = new EstadoInscripcion(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"));
            }

            Conexion.close(conn);
            Conexion.close(rs);
            Conexion.close(stmt);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar estado de inscripcion por nombre",e);
        }

        return Optional.ofNullable(estadoInscripcion);
    }

    //UPDATE
    public EstadoInscripcion update(EstadoInscripcion estadoInscripcion){
        try (Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setString(1, estadoInscripcion.getNombre());
            stmt.setString(2, estadoInscripcion.getDescripcion());
            stmt.setLong(3, estadoInscripcion.getId());

            return stmt.executeUpdate() > 0 ? estadoInscripcion : null;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el estado de inscripcion",e);
        }
    }

    //DELETE
    public boolean delete(Long id){
        try (Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(DELETE)){
            
            stmt.setLong(1, id);

            return stmt.executeUpdate() > 0;
        }catch (SQLException e){
            throw new RuntimeException("Error al eliminar el estado de inscripcion",e);
        }
    }

}
