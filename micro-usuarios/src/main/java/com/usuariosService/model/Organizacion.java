package com.usuariosService.model;

import com.usuariosService.model.TipoOrganizacion;

public class Organizacion extends Usuario{
    private TipoOrganizacion tipoOrganizacion;
    private String descripcion;


    public Organizacion(Long id, String nombre, String correo, String telefono, String clave, Rol rol, TipoOrganizacion tipoOrganizacion, String descripcion, String nombreUsuario) {
        super(id, nombre, correo, telefono, clave, rol, nombreUsuario);
        this.tipoOrganizacion = tipoOrganizacion;
        this.descripcion = descripcion;

    }

    //Este constructor será para actualizar la organizacion
    public Organizacion(Long id, String nombre, String correo, String telefono, String clave, String nombreUsuario, TipoOrganizacion tipoOrganizacion, String descripcion) {
        super(id, nombre, correo, telefono, clave, nombreUsuario);
        this.tipoOrganizacion = tipoOrganizacion;
        this.descripcion = descripcion;
    }

    //Este constructor es para guardar las organizaciones en la bd
    public Organizacion(String nombre, String nombreUsuario, String correo, String clave) {
        super(nombre, nombreUsuario, correo, clave);
    }

    //Este constructor es para obtener las organizaciones de la bd
    public Organizacion(Long idORganizacion, String nombre, String correo, String clave,String nombreUsuario,Rol rol) {
        super(idORganizacion,nombre,correo,clave,nombreUsuario,rol);

    }

    public Organizacion(Long id, TipoOrganizacion tipoOrganizacion) {
        super.setId(id);
        this.tipoOrganizacion = tipoOrganizacion;
    }

    public TipoOrganizacion getTipoOrganizacion() {
        return tipoOrganizacion;
    }

    public void setTipoOrganizacion(TipoOrganizacion tipoOrganizacion) {
        this.tipoOrganizacion = tipoOrganizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "\nOrganizacion:\n" +
                "Id: " + getId() + "\n" +
                "Nombre: " + super.getNombre() + "\n"+
                "Nombre Usuario: " + super.getNombreUsuario()+"\n"+
                "Rol: "+super.getRol().getNombre()+"\n"+
                "Telefono: "+super.getTelefono()+"\n"+
                "TipoOrganizacion: "+ tipoOrganizacion.getNombre() +"\n"+
                "Correo: "+super.getCorreo()+"\n"+
                "Clave: "+ super.getClave()+"\n"+
                "Descripcion: "+descripcion+"\n";

    }


}
