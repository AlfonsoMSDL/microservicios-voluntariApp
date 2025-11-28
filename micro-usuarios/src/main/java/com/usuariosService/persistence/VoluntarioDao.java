package com.usuariosService.persistence;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.usuariosService.model.*;
import com.usuariosService.persistence.impl.UsuarioPostgresqlDao;

public class VoluntarioDao {

    private final String INSERT = "INSERT INTO voluntarios (id,apellido) VALUES (?,?)";
    private final String SELECT_ALL = "SELECT v.id, u.nombre, v.apellido, u.correo, u.clave, u.nombre_usuario,u.id_rol,u.telefono, v.habilidades, v.experiencia, v.disponibilidad, v.areas_interes FROM usuarios u JOIN voluntarios v on u.id = v.id ";

    private final String SELECT_BY_ID = "SELECT v.id, u.nombre, v.apellido, u.correo, u.clave, u.nombre_usuario,u.id_rol,u.telefono, v.habilidades, v.experiencia, v.disponibilidad, v.areas_interes FROM usuarios u JOIN voluntarios v on u.id = v.id  WHERE v.id = ?";
    private final String FIND_BY_CORREO = "SELECT v.id, u.nombre, v.apellido, u.correo, u.clave, u.nombre_usuario,u.id_rol,u.telefono, v.habilidades, v.experiencia, v.disponibilidad, v.areas_interes FROM usuarios u JOIN voluntarios v on u.id = v.id  WHERE u.correo = ?";
    private final String UPDATE = "UPDATE voluntarios SET apellido=?, habilidades = ?, experiencia = ?, disponibilidad = ?, areas_interes = ? WHERE id=?";

    private final String DELETE = "DELETE FROM voluntarios WHERE id=?";

    private final RolDao rolDao = new RolDao();
    private final  UsuarioPostgresqlDao usuarioDao = new UsuarioPostgresqlDao();

    // CREATE
    public Voluntario save(Voluntario voluntario) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, voluntario.getId());
            stmt.setString(2, voluntario.getApellido());

            int registrosAfectados = stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                voluntario.setId(rs.getLong(1));
            }

            Conexion.close(rs);
            return registrosAfectados != 0 ? voluntario : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el voluntario", e);
        }
    }


    // READ ALL
    public List<Voluntario> findAll() {
        List<Voluntario> voluntarios = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Long idVoluntario = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String clave = rs.getString("clave");
                String nombreUsuario = rs.getString("nombre_usuario");
                Rol rol = rolDao.findById(rs.getLong("id_rol")).get();
                String telefono = rs.getString("telefono");
                String  habilidades = rs.getString("habilidades");
                String experiencia = rs.getString("experiencia");
                String disponibilidad = rs.getString("disponibilidad");
                String areasInteres = rs.getString("areas_interes");

                Voluntario voluntario = new Voluntario(idVoluntario, nombre, apellido, correo, clave, nombreUsuario, rol);
                voluntario.setTelefono(telefono);
                voluntario.setHabilidades(habilidades);
                voluntario.setExperiencia(experiencia);
                voluntario.setDisponibilidad(disponibilidad);
                voluntario.setAreas_interes(areasInteres);

                voluntarios.add(voluntario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los voluntarios", e);
        }

        return voluntarios;
    }


    // READ BY ID
    public Optional<Voluntario> findById(Long idBuscar) {
        Voluntario voluntario = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setLong(1, idBuscar);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Long idVoluntario = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String clave = rs.getString("clave");
                String nombreUsuario = rs.getString("nombre_usuario");
                Rol rol = rolDao.findById(rs.getLong("id_rol")).get();
                String telefono = rs.getString("telefono");
                String  habilidades = rs.getString("habilidades");
                String experiencia = rs.getString("experiencia");
                String disponibilidad = rs.getString("disponibilidad");
                String areasInteres = rs.getString("areas_interes");

                voluntario = new Voluntario(idVoluntario, nombre, apellido, correo, clave, nombreUsuario, rol);
                voluntario.setTelefono(telefono);
                voluntario.setHabilidades(habilidades);
                voluntario.setExperiencia(experiencia);
                voluntario.setDisponibilidad(disponibilidad);
                voluntario.setAreas_interes(areasInteres);
            }

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar voluntario por ID", e);
        }

        return Optional.ofNullable(voluntario);
    }


    // READ BY EMAIL
    public Optional<Voluntario> findByCorreo(String correoBuscar) {
        Voluntario voluntario = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_CORREO)) {

            stmt.setString(1, correoBuscar);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Long idVoluntario = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String clave = rs.getString("clave");
                String nombreUsuario = rs.getString("nombre_usuario");
                Rol rol = rolDao.findById(rs.getLong("id_rol")).get();
                String telefono = rs.getString("telefono");
                String  habilidades = rs.getString("habilidades");
                String experiencia = rs.getString("experiencia");
                String disponibilidad = rs.getString("disponibilidad");
                String areasInteres = rs.getString("areas_interes");

                voluntario = new Voluntario(idVoluntario, nombre, apellido, correo, clave, nombreUsuario, rol);
                voluntario.setTelefono(telefono);
                voluntario.setHabilidades(habilidades);
                voluntario.setExperiencia(experiencia);
                voluntario.setDisponibilidad(disponibilidad);
                voluntario.setAreas_interes(areasInteres);
            }

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar voluntario por correo", e);
        }

        return Optional.ofNullable(voluntario);
    }


    // UPDATE
    public Voluntario update(Voluntario voluntario) {
        try  {
            //Primero actualizo los datos para el usuario
            Usuario usuario = voluntario;
            usuarioDao.update(usuario);

            //Luego actualizo los datos para el voluntario

            Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE);


            stmt.setString(1, voluntario.getApellido());
            stmt.setString(2, voluntario.getHabilidades());
            stmt.setString(3, voluntario.getExperiencia());
            stmt.setString(4, voluntario.getDisponibilidad());
            stmt.setString(5, voluntario.getAreas_interes());
            stmt.setLong(6, voluntario.getId());

            return stmt.executeUpdate() > 0 ? voluntario: null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar voluntario", e);
        }
    }

    
    // DELETE
    public boolean delete(Long idVoluntario) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setLong(1, idVoluntario);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar voluntario", e);
        }
    }


    
}
