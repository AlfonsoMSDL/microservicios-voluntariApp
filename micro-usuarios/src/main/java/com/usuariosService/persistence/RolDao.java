package com.usuariosService.persistence;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.usuariosService.model.Rol;

public class RolDao {

    private final String INSERT = "INSERT INTO roles (nombre, descripcion) VALUES (?, ?)";
    private final String SELECT_ALL = "SELECT * FROM roles";
    private final String SELECT_BY_ID = "SELECT * FROM roles WHERE id = ?";
    private final String SELECT_BY_NOMBRE = "SELECT * FROM roles WHERE nombre = ?";
    private final String UPDATE = "UPDATE roles SET nombre = ?, descripcion = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM roles WHERE id = ?";

    // CREATE
    public Rol save(Rol rol) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, rol.getNombre());
            stmt.setString(2, rol.getDescripcion());

            int registrosAfectados = stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                rol.setId(rs.getLong(1));
            }

            Conexion.close(rs);
            return registrosAfectados != 0 ? rol : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el rol", e);
        }
    }

    // READ ALL
    public List<Rol> findAll() {
        List<Rol> roles = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                roles.add(rol);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los roles", e);
        }

        return roles;
    }

    // READ BY ID
    public Optional<Rol> findById(Long id) {
        Rol rol = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rol = new Rol(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

            Conexion.close(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el rol por ID", e);
        }

        return Optional.ofNullable(rol);
    }

    public Optional<Rol> findByNombre(String nombre) {
        Rol rol = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_NOMBRE)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rol = new Rol(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

            Conexion.close(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el rol por ID", e);
        }

        return Optional.ofNullable(rol);
    }

    // UPDATE
    public Rol update(Rol rol) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setString(1, rol.getNombre());
            stmt.setString(2, rol.getDescripcion());
            stmt.setLong(3, rol.getId());

            return stmt.executeUpdate() > 0 ? rol : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el rol", e);
        }
    }

    // DELETE
    public boolean delete(Long id) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el rol", e);
        }
    }
}
