package com.usuariosService.model;

public class Usuario {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private String clave;
    private Rol rol;
    private String nombreUsuario;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String correo, String telefono, String clave, Rol rol, String nombreUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = clave;
        this.rol = rol;
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario(String nombre, String nombreUsuario, String correo, String clave) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.nombreUsuario = nombreUsuario;

    }

    public Usuario(Long id, String nombre, String correo, String clave, String nombreUsuario,Rol rol) {
        this.id=id;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    public Usuario(Long id, String nombre, String correo, String telefono, String clave, String nombreUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = clave;
        this.nombreUsuario = nombreUsuario;
    }

    //Constructor para guardar un usuario
    public Usuario(String nombre, String correo, String telefono, String clave, Rol rolInsertar, String nombreUsuario) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = clave;
        this.rol = rolInsertar;
        this.nombreUsuario = nombreUsuario;
    }

    public void establecerValoresUsuario(Usuario usuarioGuardado) {
        this.setNombre(usuarioGuardado.getNombre());
        this.setCorreo(usuarioGuardado.getCorreo());
        this.setTelefono(usuarioGuardado.getTelefono());
        this.setRol(usuarioGuardado.getRol());
        this.setClave(usuarioGuardado.getClave());
        this.setNombreUsuario(usuarioGuardado.getNombreUsuario());


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}