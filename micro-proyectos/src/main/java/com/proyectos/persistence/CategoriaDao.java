package com.proyectos.persistence;

import com.proyectos.model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaDao {

    private final String INSERT = "INSERT INTO categorias (nombre, descripcion) VALUES (?, ?)";
    private final String SELECT_ALL = "SELECT * FROM categorias";
    private final String SELECT_BY_ID = "SELECT * FROM categorias WHERE id = ?";
    private final String SELECT_BY_NOMBRE = "SELECT * FROM categorias WHERE nombre = ?";
    private final String UPDATE = "UPDATE categorias SET nombre = ?, descripcion = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM categorias WHERE id = ?";

    // CREATE
    public Categoria save(Categoria categoria) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());

            int registrosAfectados = stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                categoria.setId(rs.getLong(1));
            }

            Conexion.close(rs);
            return registrosAfectados != 0 ? categoria : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la categoría", e);
        }
    }

    // READ ALL
    public List<Categoria> findAll() {
        List<Categoria> categorias = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las categorías", e);
        }

        return categorias;
    }

    // READ BY ID
    public Optional<Categoria> findById(Long id) {
        Categoria categoria = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

            Conexion.close(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la categoría por ID", e);
        }

        return Optional.ofNullable(categoria);
    }

    // READ BY NOMBRE
    public Optional<Categoria> findByNombre(String nombre) {
        Categoria categoria = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_NOMBRE)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

            Conexion.close(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la categoría por nombre", e);
        }

        return Optional.ofNullable(categoria);
    }

    // UPDATE
    public Categoria update(Categoria categoria) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());
            stmt.setLong(3, categoria.getId());

            return stmt.executeUpdate() > 0 ? categoria : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la categoría", e);
        }
    }

    // DELETE
    public boolean delete(Long id) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la categoría", e);
        }
    }



}
