package com.usuariosService.service;

import com.usuariosService.dto.GetOrganizacion;
import com.usuariosService.dto.GetVoluntario;
import com.usuariosService.mapper.GenericMapper;
import com.usuariosService.model.Organizacion;
import com.usuariosService.model.Rol;
import com.usuariosService.model.Usuario;
import com.usuariosService.model.Voluntario;
import com.usuariosService.persistence.RolDao;
import com.usuariosService.persistence.UsuarioDao;
import com.usuariosService.persistence.VoluntarioDao;
import com.usuariosService.persistence.fabrica.UsuarioFabrica;
import com.usuariosService.persistence.impl.UsuarioPostgresqlDao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class VoluntarioService {
    private final VoluntarioDao voluntarioDao = new VoluntarioDao();
    private final UsuarioDao usuarioDao = UsuarioFabrica.getImplementacion("postgresql");
    private final GenericMapper<GetVoluntario,Voluntario> genericMapper = new  GenericMapper<>();

    public Voluntario save(String nombre, String apellido , String nombreUsuario, String correo, String clave,String telefono) {

        Rol rolInsertar = (new RolDao()).findByNombre("Voluntario").get();

        //Se guarda primero el usuario
        Usuario usuarioGuardado = usuarioDao.save(new Usuario(nombre,correo,telefono,clave,rolInsertar,nombreUsuario));

        Voluntario  voluntarioGuardado= voluntarioDao.save(new Voluntario(usuarioGuardado.getId(),apellido));

        voluntarioGuardado.establecerValoresUsuario(usuarioGuardado);
        return voluntarioGuardado;
    }


    public List<GetVoluntario> findAllVoluntarios(){

        List<GetVoluntario> voluntarios = voluntarioDao.findAll().stream().map(p -> genericMapper.toDto(p,GetVoluntario.class)).toList();

        return voluntarios;
    }

    public Optional<Voluntario> findByCorreo(String correo){
        return voluntarioDao.findByCorreo(correo);
    }

    public Voluntario update(Long id, String nombre, String apellido, String correo, String telefono, String clave, String habilidades, String experiencia, String disponibilidad, String areas_interes, String nombreUsuario){

        if(clave == null || clave.trim().isEmpty()){
            //No se proporciono una clave, por lo tanto, se deja la misma de antes
            Voluntario voluntarioActual =  voluntarioDao.findById(id).get();
            clave = voluntarioActual.getClave();
        }

        Voluntario voluntarioActualizar = new Voluntario(id,nombre,apellido,correo,telefono,clave,habilidades,experiencia,disponibilidad,areas_interes,nombreUsuario);

        return  voluntarioDao.update(voluntarioActualizar);
    }

    public GetVoluntario findById(Long id){
        Voluntario voluntarioActual = voluntarioDao.findById(id).get();
        return genericMapper.toDto(voluntarioActual,GetVoluntario.class);
    }

    public boolean delete(Long id){
        boolean eliminando = voluntarioDao.delete(id);
        if(eliminando){ // Si se elimino el voluntario, borro su usuario
            boolean usuarioEliminado =  usuarioDao.delete(id);
            return usuarioEliminado;
        }
        return false;
    }

}
