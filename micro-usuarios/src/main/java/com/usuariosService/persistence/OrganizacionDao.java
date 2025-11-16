package com.usuariosService.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.usuariosService.model.*;
import com.usuariosService.persistence.impl.UsuarioPostgresqlDao;

public class OrganizacionDao {
    private static final String INSERT = "INSERT INTO organizaciones (id,tipo_organizacion_id) VALUES(?,?)";
    private static final String SELECT = "SELECT u.id, u.nombre, u.correo, u.clave, u.nombre_usuario,u.id_rol,u.telefono, o.tipo_organizacion_id, o.descripcion  FROM usuarios u JOIN organizaciones o on u.id = o.id";
    private static final String FIND_BY_CORREO = "SELECT u.id, u.nombre, u.correo, u.clave, u.nombre_usuario,u.id_rol,u.telefono, o.tipo_organizacion_id, o.descripcion  FROM usuarios u JOIN organizaciones o on u.id = o.id WHERE u.correo = ?";
    private static final String FIND_BY_ID = "SELECT u.id, u.nombre, u.correo, u.clave, u.nombre_usuario,u.id_rol,u.telefono, o.tipo_organizacion_id, o.descripcion  FROM usuarios u JOIN organizaciones o on u.id = o.id WHERE u.id = ?";
    private static final String UPDATE = "UPDATE organizaciones SET tipo_organizacion_id = ?, descripcion = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM organizaciones WHERE id_organizacion = ?";
    private final UsuarioPostgresqlDao usuarioDao = new UsuarioPostgresqlDao();

    RolDao  rolDao = new RolDao();
    TipoOrganizacionDao  tipoOrganizacionDao = new TipoOrganizacionDao();


    public Organizacion save(Organizacion organizacion){
        Connection conn = null;
        PreparedStatement stmt = null;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            stmt.setLong(1,organizacion.getId());
            stmt.setLong(2,organizacion.getTipoOrganizacion().getId());


            int registrosAfectados = stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                organizacion.setId(rs.getLong(1));
            }

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

            return registrosAfectados !=0 ? organizacion:null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }



    public List<Organizacion> findAll(){
        Connection conn = null;
        PreparedStatement stmt = null;

        List<Organizacion> organizaciones =  new ArrayList<>();
        Organizacion organizacion = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SELECT);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Long idOrganizacion = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String clave = rs.getString("clave");
                String nombreUsuario = rs.getString("nombre_usuario");
                Rol rol = rolDao.findById(rs.getLong("id_rol")).get();
                String telefono = rs.getString("telefono");
                TipoOrganizacion tipo = tipoOrganizacionDao.findById( rs.getLong("tipo_organizacion_id")).get();
                String descripcion = rs.getString("descripcion");


                organizacion = new Organizacion(idOrganizacion,nombre,correo,clave,nombreUsuario,rol);
                organizacion.setTelefono(telefono);
                organizacion.setTipoOrganizacion(tipo);
                organizacion.setDescripcion(descripcion);

                organizaciones.add(organizacion);
            }

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
            return organizaciones;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<Organizacion> findByCorreo(String correoBuscar){
        Connection conn;
        PreparedStatement stmt;
        Organizacion organizacion = null;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(FIND_BY_CORREO);
            stmt.setString(1, correoBuscar);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Long idOrganizacion = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String clave = rs.getString("clave");
                String nombreUsuario = rs.getString("nombre_usuario");
                Rol rol = rolDao.findById(rs.getLong("id_rol")).get();
                String telefono = rs.getString("telefono");
                TipoOrganizacion tipo = tipoOrganizacionDao.findById( rs.getLong("tipo_organizacion_id")).get();
                String descripcion = rs.getString("descripcion");


                organizacion = new Organizacion(idOrganizacion,nombre,correo,clave,nombreUsuario,rol);
                organizacion.setTelefono(telefono);
                organizacion.setTipoOrganizacion(tipo);
                organizacion.setDescripcion(descripcion);
            }

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

            return Optional.ofNullable(organizacion);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    // 🔹 Buscar por ID
    public Optional<Organizacion> findById(Long idBuscar){
        Connection conn;
        PreparedStatement stmt;
        Organizacion organizacion = null;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setLong(1, idBuscar);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Long idOrganizacion = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String clave = rs.getString("clave");
                String nombreUsuario = rs.getString("nombre_usuario");
                Rol rol = rolDao.findById(rs.getLong("id_rol")).get();
                String telefono = rs.getString("telefono");
                TipoOrganizacion tipo = tipoOrganizacionDao.findById( rs.getLong("tipo_organizacion_id")).get();
                String descripcion = rs.getString("descripcion");


                organizacion = new Organizacion(idOrganizacion,nombre,correo,clave,nombreUsuario,rol);
                organizacion.setTelefono(telefono);
                organizacion.setTipoOrganizacion(tipo);
                organizacion.setDescripcion(descripcion);

            }

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

            return Optional.ofNullable(organizacion);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


    // Modificar
    public Organizacion update(Organizacion organizacion){
        Connection conn;
        PreparedStatement stmt;

        try{

            //Primero actualizo los datos para el usuario
            Usuario usuario = organizacion;
            usuarioDao.update(usuario);

            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(UPDATE);

            stmt.setLong(1, organizacion.getTipoOrganizacion().getId());
            stmt.setString(2, organizacion.getDescripcion());

            stmt.setLong(3, organizacion.getId());

            int registrosAfectados = stmt.executeUpdate();

            Conexion.close(stmt);
            Conexion.close(conn);

            return registrosAfectados > 0? organizacion: null;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    
    // 🔹 Eliminar por ID
    public boolean deleteById(Long id){
        Connection conn;
        PreparedStatement stmt;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(DELETE);
            stmt.setLong(1, id);

            int registrosAfectados = stmt.executeUpdate();

            Conexion.close(stmt);
            Conexion.close(conn);

            return registrosAfectados > 0;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

     

}
