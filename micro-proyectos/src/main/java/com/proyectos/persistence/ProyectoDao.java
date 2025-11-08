package com.proyectos.persistence;

import com.proyectos.model.Categoria;
import com.proyectos.model.Proyecto;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProyectoDao {

    private static final String SELECT_BY_ORGANIZACION = "SELECT * FROM proyectos WHERE organizacion_id = ?";
    private static final String INSERT = "INSERT INTO proyectos (nombre, descripcion, ubicacion, requisitos, fecha_inicio, fecha_fin, voluntarios_requeridos, id_categoria, organizacion_id) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE proyectos SET nombre = ?, descripcion = ?, ubicacion = ?, requisitos = ?, fecha_inicio = ?, fecha_fin = ?, voluntarios_requeridos = ?, id_categoria = ? WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM proyectos WHERE id = ?";

    public Proyecto save(Proyecto proyecto) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, proyecto.getNombre());
            ps.setString(2,proyecto.getDescripcion());
            ps.setString(3,proyecto.getUbicacion());
            ps.setString(4,proyecto.getRequisitos());
            ps.setDate(5,proyecto.getFecha_inicio());
            ps.setDate(6,proyecto.getFecha_fin());
            ps.setInt(7,proyecto.getVoluntarios_requeridos());
            ps.setLong(8,proyecto.getCategoria().getId());
            ps.setLong(9,proyecto.getIdOrganizacion());

            int registrosAfectados = ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                proyecto.setId(rs.getLong(1));
            }
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
            return registrosAfectados !=0 ? proyecto : null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Proyecto> findAllProyectosByOrganizacion(Long idOrganizacion){
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        List<Proyecto> proyectos = new ArrayList<>();
        Proyecto proyecto;

        try {
            connection = Conexion.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_ORGANIZACION);
            preparedStatement.setLong(1, idOrganizacion);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                String ubicacion = resultSet.getString("ubicacion");
                String requisitos = resultSet.getString("requisitos");
                Date fechaInicio = resultSet.getDate("fecha_inicio");
                Date fechaFin = resultSet.getDate("fecha_fin");
                int voluntariosRequeridos = resultSet.getInt("voluntarios_requeridos");


                Categoria categoria = (new CategoriaDao()).findById(resultSet.getLong("id_categoria")).get();

                Long idOrganizacionBd = resultSet.getLong("organizacion_id");

                proyecto = new Proyecto(id,nombre,descripcion,ubicacion,requisitos,fechaInicio,fechaFin,voluntariosRequeridos,categoria,idOrganizacionBd);

                proyectos.add(proyecto);


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return proyectos;

    }

    public Proyecto update(Proyecto proyecto){
        Connection conn;
        PreparedStatement stmt;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(UPDATE);

            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.setString(3, proyecto.getUbicacion());
            stmt.setString(4, proyecto.getRequisitos());
            stmt.setDate(5,proyecto.getFecha_inicio());
            stmt.setDate(6,proyecto.getFecha_fin());
            stmt.setInt(7,proyecto.getVoluntarios_requeridos());
            stmt.setLong(8,proyecto.getCategoria().getId());

            stmt.setLong(9,proyecto.getId());

            int registrosAfectados = stmt.executeUpdate();

            Conexion.close(conn);
            Conexion.close(stmt);

            return registrosAfectados > 0 ? proyecto : null;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Optional<Proyecto> findById(Long idProyecto) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Proyecto proyecto = null;

    final String SELECT_BY_ID = "SELECT * FROM proyectos WHERE id = ?";

    try {
        connection = Conexion.getConnection();
        preparedStatement = connection.prepareStatement(SELECT_BY_ID);
        preparedStatement.setLong(1, idProyecto);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String nombre = resultSet.getString("nombre");
            String descripcion = resultSet.getString("descripcion");
            String ubicacion = resultSet.getString("ubicacion");
            String requisitos = resultSet.getString("requisitos");
            Date fechaInicio = resultSet.getDate("fecha_inicio");
            Date fechaFin = resultSet.getDate("fecha_fin");
            int voluntariosRequeridos = resultSet.getInt("voluntarios_requeridos");

            Long idCategoria = resultSet.getLong("id_categoria");
            Categoria categoria = (new CategoriaDao()).findById(idCategoria).orElse(null);

            Long idOrganizacion = resultSet.getLong("organizacion_id");

            proyecto = new Proyecto(
                    id,
                    nombre,
                    descripcion,
                    ubicacion,
                    requisitos,
                    fechaInicio,
                    fechaFin,
                    voluntariosRequeridos,
                    categoria,
                    idOrganizacion
            );
        }

        Conexion.close(resultSet);
        Conexion.close(preparedStatement);
        Conexion.close(connection);

    } catch (SQLException e) {
        throw new RuntimeException("Error al obtener proyecto por ID: " + e.getMessage(), e);
    }

    return Optional.ofNullable(proyecto);
}


}
