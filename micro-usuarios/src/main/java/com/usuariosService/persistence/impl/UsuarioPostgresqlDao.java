package com.usuariosService.persistence.impl;

import com.usuariosService.model.*;
import com.usuariosService.persistence.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioPostgresqlDao implements UsuarioDao {

    private final String INSERT = "INSERT INTO usuarios (nombre, correo, telefono, clave, id_rol, nombre_usuario) VALUES (?, ?, ?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM usuarios";
    private final String SELECT_BY_ID = "SELECT * FROM usuarios WHERE id = ?";
    private final String SELECT_BY_EMAIL = "SELECT * FROM usuarios WHERE correo = ?";
    private final String SELECT_BY_USERNAME = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
    private final String UPDATE = "UPDATE usuarios SET nombre = ?, correo = ?, telefono = ?, clave = ?, nombre_usuario = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM usuarios WHERE id = ?";

    private final RolDao rolDao = new RolDao();

    // CREATE
    public Usuario save(Usuario usuario) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getClave());
            stmt.setLong(5, usuario.getRol().getId());
            stmt.setString(6, usuario.getNombreUsuario());

            int registrosAfectados = stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getLong(1));
            }

            Conexion.close(rs);
            return registrosAfectados != 0 ? usuario : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el usuario", e);
        }
    }

    // READ ALL
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Rol rol = rolDao.findById(rs.getLong("id_rol")).orElse(null);
                Usuario usuario = mapUsuario(rs, rol);
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los usuarios", e);
        }

        return usuarios;
    }

    // READ BY ID
    public Optional<Usuario> findById(Long id) {
        Usuario usuario = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Rol rol = rolDao.findById(rs.getLong("id_rol")).orElse(null);
                usuario = mapUsuario(rs, rol);
            }

            Conexion.close(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por ID", e);
        }

        return Optional.ofNullable(usuario);
    }

    // READ BY EMAIL
    public Optional<Usuario> findByEmail(String email) {
        Usuario usuario = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_EMAIL)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Rol rol = rolDao.findById(rs.getLong("id_rol")).orElse(null);
                usuario = mapUsuario(rs, rol);
            }

            Conexion.close(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por correo", e);
        }

        return Optional.ofNullable(usuario);
    }

    // READ BY USERNAME
    public Optional<Usuario> findByUsername(String username) {
        Usuario usuario = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USERNAME)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Rol rol = rolDao.findById(rs.getLong("id_rol")).orElse(null);
                usuario = mapUsuario(rs, rol);
            }

            Conexion.close(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por nombre de usuario", e);
        }

        return Optional.ofNullable(usuario);
    }

    // UPDATE
    public Usuario update(Usuario usuario) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getClave());
            stmt.setString(5, usuario.getNombreUsuario());
            stmt.setLong(6, usuario.getId());

            int updated = stmt.executeUpdate();
            return updated > 0 ? usuario : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario", e);
        }
    }

    // DELETE
    public boolean delete(Long id) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el usuario", e);
        }
    }

    // Mapeo común de ResultSet → Usuario
    private Usuario mapUsuario(ResultSet rs, Rol rol) throws SQLException {
        return new Usuario(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("telefono"),
                rs.getString("clave"),
                rol,
                rs.getString("nombre_usuario")
        );
    }
}