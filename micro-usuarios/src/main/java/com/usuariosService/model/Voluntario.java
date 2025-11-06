package com.usuariosService.model;

public class Voluntario extends Usuario {
    private String apellido;
    private String habilidades;
    private String experiencia;
    private String disponibilidad;
    private String areas_interes;


    public Voluntario(Long id,String apellido) {
        super.setId(id);
        this.apellido = apellido;
    }

    //Este constructor sera usado para actualizar
    public Voluntario(Long id, String nombre, String apellido, String correo, String telefono, String clave, String habilidades, String experiencia, String disponibilidad, String areas_interes, String nombreUsuario) {
        super(id, nombre, correo, telefono, clave,nombreUsuario);
        this.habilidades = habilidades;
        this.experiencia = experiencia;
        this.disponibilidad = disponibilidad;
        this.areas_interes = areas_interes;
        this.apellido = apellido;
    }
    //Este constructor es para obtener los voluntarios de la bd
    public Voluntario(Long idVoluntario, String nombre, String apellido, String correo, String clave, String nombreUsuario,Rol rol) {
        super(idVoluntario,nombre,correo,clave,nombreUsuario,rol);
        this.apellido = apellido;

    }

    //Este constructor es para guardar los voluntarios en la bd
    public Voluntario(String nombre, String apellido, String nombreUsuario, String correo, String clave) {
        super(nombre,nombreUsuario,correo,clave);
        this.apellido = apellido;

    }




    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getAreas_interes() {
        return areas_interes;
    }

    public void setAreas_interes(String areas_interes) {
        this.areas_interes = areas_interes;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "\nVoluntario:\n" +
                "Id voluntario: "+this.getId()+"\n" +
                "Nombre: " + super.getNombre() +"\n"+
                "Apellido: "+this.apellido+"\n"+
                "Telefono: "+super.getTelefono()+"\n"+
                "Nombre Usuario: " + super.getNombreUsuario()+"\n"+
                "Correo: "+super.getCorreo()+"\n"+
                "Clave: "+ super.getClave()+"\n"+
                "Habilidades: "+this.habilidades+"\n"+
                "Experiencia: "+this.experiencia+"\n"+
                "Disponibilidad: "+this.disponibilidad+"\n"+
                "Areas de interes: "+this.areas_interes+"\n";


    }
}