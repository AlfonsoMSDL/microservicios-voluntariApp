package com.participaciones.persistence.impl;

import com.participaciones.model.Participacion;
import com.participaciones.persistence.Conexion;
import com.participaciones.persistence.ParticipacionDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParticipacionPostgresqlDao implements ParticipacionDao {

    private static final String INSERT = 
        "INSERT INTO participaciones (voluntario_id, proyecto_id, fecha_inicio, horas_realizadas) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SELECT_BY_PROYECTO = 
        "SELECT * FROM participaciones WHERE proyecto_id = ?";

    private static final String SELECT_BY_VOLUNTARIO =
        "SELECT * FROM participaciones WHERE voluntario_id = ?";

    private static final String SELECT_BY_ID =
        "SELECT * FROM participaciones WHERE id = ?";

    private static final String UPDATE =
        "UPDATE participaciones SET fecha_fin = ?, horas_realizadas = ? WHERE id = ?";

    private static final String DELETE =
        "DELETE FROM participaciones WHERE id = ?";
    private static final String DELETE_BY_VOLUNTARIO =
        "DELETE FROM participaciones WHERE voluntario_id = ?";
    private static final String DELETE_BY_PROYECTO =
        "DELETE FROM participaciones WHERE proyecto_id = ?";


    // ============================================================
    //                      INSERTAR
    // ============================================================
    public Participacion save(Participacion p) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, p.getIdVoluntario());
            ps.setLong(2, p.getIdProyecto());
            ps.setDate(3, p.getFechaInicio());
            ps.setInt(4, p.getHorasRealizadas());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    p.setId(rs.getLong(1));
                }
                Conexion.close(rs);
            }

            Conexion.close(ps);
            Conexion.close(conn);

            return rows > 0 ? p : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error guardando participación: " + e.getMessage(), e);
        }
    }


    // ============================================================
    //                      LISTAR TODAS LAS PARTICIPACIONES DE UN PROYECTO
    // ============================================================
    public List<Participacion> findAllParticipacionesByIdProyecto(Long idProyectoBuscar) {
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;

        List<Participacion> lista = new ArrayList<>();
        Participacion p;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SELECT_BY_PROYECTO);
            ps.setLong(1, idProyectoBuscar);
            rs = ps.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                Long idVoluntario = rs.getLong("voluntario_id");
                Long idProyecto = rs.getLong("proyecto_id");
                Date fechaInicio = rs.getDate("fecha_inicio");
                Date fechaFin = rs.getDate("fecha_fin");
                int horas = rs.getInt("horas_realizadas");

                p = new Participacion(id, idVoluntario, idProyecto, fechaInicio, fechaFin, horas);

                lista.add(p);
            }

            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);

        } catch (SQLException e) {
            throw new RuntimeException("Error obteniendo participaciones: " + e.getMessage(), e);
        }

        return lista;
    }

    //Listar todas las participaciones de un voluntario
    public List<Participacion> findAllParticipacionesByIdVoluntario(Long idVoluntarioBuscar){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Participacion> participaciones = new ArrayList<>();
        Participacion p=null;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SELECT_BY_VOLUNTARIO);
            stmt.setLong(1, idVoluntarioBuscar);
            rs = stmt.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                Long idVoluntario = rs.getLong("voluntario_id");
                Long idProyecto = rs.getLong("proyecto_id");
                Date fechaInicio = rs.getDate("fecha_inicio");
                Date fechaFin = rs.getDate("fecha_fin");
                int horas = rs.getInt("horas_realizadas");

                p = new Participacion(id, idVoluntario, idProyecto, fechaInicio, fechaFin, horas);
                participaciones.add(p);
            }

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        }catch(SQLException e){
            throw new RuntimeException("Error obteniendo participaciones"+e.getMessage(), e);
        }
        return participaciones;
    }


    // ============================================================
    //                      BUSCAR POR ID
    // ============================================================
    public Optional<Participacion> findById(Long idBuscado) {
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;

        Participacion p = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SELECT_BY_ID);
            ps.setLong(1, idBuscado);
            rs = ps.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("id");
                Long idVoluntario = rs.getLong("voluntario_id");
                Long idProyecto = rs.getLong("proyecto_id");
                Date fechaInicio = rs.getDate("fecha_inicio");
                Date fechaFin = rs.getDate("fecha_fin");
                int horas = rs.getInt("horas_realizadas");

                p = new Participacion(id, idVoluntario, idProyecto, fechaInicio, fechaFin, horas);
            }

            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando participación: " + e.getMessage(), e);
        }

        return Optional.ofNullable(p);
    }



    // ============================================================
    //                      ACTUALIZAR
    // ============================================================
    public Participacion update(Participacion p) {
        Connection conn;
        PreparedStatement ps;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(UPDATE);

            ps.setDate(1, p.getFechaFin());
            ps.setInt(2, p.getHorasRealizadas());
            ps.setLong(3, p.getId());

            int rows = ps.executeUpdate();

            Conexion.close(ps);
            Conexion.close(conn);

            return rows > 0 ? p : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando participación: " + e.getMessage(), e);
        }
    }


    // ============================================================
    //                      ELIMINAR
    // ============================================================
    public boolean delete(Long id) {
        Connection conn;
        PreparedStatement ps;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(DELETE);
            ps.setLong(1, id);

            int rows = ps.executeUpdate();

            Conexion.close(ps);
            Conexion.close(conn);

            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando participación: " + e.getMessage(), e);
        }
    }

    // ============================================================
    //                      ELIMINAR participaciones por Voluntario
    // ============================================================
    @Override
    public boolean deleteByIdVoluntario(Long id) throws SQLException {
        Connection conn;
        PreparedStatement ps;


        conn = Conexion.getConnection();
        ps = conn.prepareStatement(DELETE_BY_VOLUNTARIO);
        ps.setLong(1, id);

        int rows = ps.executeUpdate();

        Conexion.close(ps);
        Conexion.close(conn);

        return rows > 0;

    }


    @Override
    public boolean deleteByIdProyecto(Long id) throws SQLException {
        Connection conn;
        PreparedStatement ps;

        conn = Conexion.getConnection();
        ps = conn.prepareStatement(DELETE_BY_PROYECTO);
        ps.setLong(1, id);

        int rows = ps.executeUpdate();

        Conexion.close(ps);
        Conexion.close(conn);

        return rows > 0;
    }

    
}
