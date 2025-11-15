package com.usuariosService.service;

import com.usuariosService.dto.GetOrganizacion;
import com.usuariosService.mapper.GenericMapper;
import com.usuariosService.model.*;
import com.usuariosService.persistence.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class OrganizacionService {
    private final OrganizacionDao organizacionDao = new OrganizacionDao();
    private final UsuarioDao  usuarioDao = new UsuarioDao();
    private final TipoOrganizacionDao tipoOrganizacionDao = new TipoOrganizacionDao();
    private final GenericMapper<GetOrganizacion,Organizacion> genericMapper = new  GenericMapper<>();
    private final RolDao rolDao = new RolDao();
    private final Logger  log = Logger.getLogger(OrganizacionService.class);


    public Organizacion save(String nombre, String nombreUsuario, String correo, String clave, String telefono, Long idTipo){

        //Primero busco el rol en la BD para insertarlo en el usuario nuevo
        Rol rolInsertar = (new RolDao()).findByNombre("Organizacion").get();
        //Se guarda primero el usuario

        Usuario usuarioGuardado = usuarioDao.save(new Usuario(nombre,correo,telefono,clave,rolInsertar,nombreUsuario));

        //Luego busco el tipo de organizacion en la BD para para agregarselo a la organizacion
        TipoOrganizacion tipoOrganizacion = tipoOrganizacionDao.findById(idTipo).get();

        Organizacion nuevaOrganizacion = new Organizacion(usuarioGuardado.getId(),tipoOrganizacion);


        Organizacion insertada = organizacionDao.save(nuevaOrganizacion);
        insertada.establecerValoresUsuario(usuarioGuardado);

        return insertada;
    }



    public List<GetOrganizacion> findAllOrganizaciones(){
        List<Organizacion> organizaciones =  organizacionDao.findAll();
        return organizaciones.stream()
                .map(o -> genericMapper.toDto(o,GetOrganizacion.class))
                .toList();
    }

    public GetOrganizacion findByCorreo(String correo){
        Organizacion organizacion = organizacionDao.findByCorreo(correo).get();
        return  genericMapper.toDto(organizacion,GetOrganizacion.class);
    }

    public GetOrganizacion findById(Long id){
        Organizacion organizacion = organizacionDao.findById(id).get();
        return  genericMapper.toDto(organizacion,GetOrganizacion.class);
    }


    public Organizacion update(Long id, String nombre, String correo, String telefono, String clave, Long idTipo, String descripcion, String nombreUsuario){

        if(clave == null || clave.trim().isEmpty()){
            //No se proporciono una clave, por lo tanto, se deja la misma de antes
            Organizacion organizacionActual =  organizacionDao.findById(id).get();
            clave = organizacionActual.getClave();
        }

        TipoOrganizacion tipoOrganizacion  = tipoOrganizacionDao.findById(idTipo).get();

        Organizacion organizacionUpdate = new Organizacion(id,nombre,correo,telefono,clave,nombreUsuario, tipoOrganizacion,descripcion);
        return organizacionDao.update(organizacionUpdate);
    }

    public boolean delete(Long id){
        boolean eliminando = organizacionDao.deleteById(id);
        if(eliminando){ // Si se elimino la organizacion, borro su usuario
            boolean usuarioEliminado =  usuarioDao.delete(id);
            return usuarioEliminado;
        }
        return false;
        
    }


}
