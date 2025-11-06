package com.usuariosService.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.usuariosService.model.TipoOrganizacion;

public class TipoOrganizacionDao {

    private final String INSERT = "INSERT INTO tipo_organizacion (tipo, descripcion) VALUES (?, ?)";
    private final String SELECT_ALL = "SELECT * FROM tipo_organizacion";
    private final String SELECT_BY_ID = "SELECT * FROM tipo_organizacion WHERE id = ?";
    private final String SELECT_BY_TIPO = "SELECT * FROM tipo_organizacion WHERE tipo = ?";
    private final String UPDATE = "UPDATE tipo_organizacion SET tipo = ?, descripcion = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM tipo_organizacion WHERE id = ?";

    // CREATE
    public TipoOrganizacion save(TipoOrganizacion tipoOrg) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tipoOrg.getNombre());
            stmt.setString(2, tipoOrg.getDescripcion());

            int registrosAfectados = stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                tipoOrg.setId(rs.getLong(1));
            }

            Conexion.close(rs);
            return registrosAfectados != 0 ? tipoOrg : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el tipo de organización", e);
        }
    }

    // READ ALL
    public List<TipoOrganizacion> findAll() {
        List<TipoOrganizacion> tipos = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TipoOrganizacion tipo = new TipoOrganizacion(
                        rs.getLong("id"),
                        rs.getString("tipo"),
                        rs.getString("descripcion")
                );
                tipos.add(tipo);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los tipos de organización", e);
        }

        return tipos;
    }

    // READ BY ID
    public Optional<TipoOrganizacion> findById(Long id) {
        TipoOrganizacion tipoOrg = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tipoOrg = new TipoOrganizacion(
                        rs.getLong("id"),
                        rs.getString("tipo"),
                        rs.getString("descripcion")
                );
            }

            Conexion.close(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el tipo de organización por ID", e);
        }

        return Optional.ofNullable(tipoOrg);
    }

    // READ BY NOMBRE (tipo)
    public Optional<TipoOrganizacion> findByTipo(String tipo) {
        TipoOrganizacion tipoOrg = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_TIPO)) {

            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tipoOrg = new TipoOrganizacion(
                        rs.getLong("id"),
                        rs.getString("tipo"),
                        rs.getString("descripcion")
                );
            }

            Conexion.close(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el tipo de organización por nombre", e);
        }

        return Optional.ofNullable(tipoOrg);
    }

    // UPDATE
    public TipoOrganizacion update(TipoOrganizacion tipoOrg) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setString(1, tipoOrg.getNombre());
            stmt.setString(2, tipoOrg.getDescripcion());
            stmt.setLong(3, tipoOrg.getId());

            return stmt.executeUpdate() > 0 ? tipoOrg : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el tipo de organización", e);
        }
    }

    // DELETE
    public boolean delete(Long id) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el tipo de organización", e);
        }
    }
}

