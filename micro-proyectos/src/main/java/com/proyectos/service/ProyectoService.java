package com.proyectos.service;

import com.proyectos.dto.GetProyecto;
import com.proyectos.client.Cliente;
import com.proyectos.dto.GetOrganizacion;
import com.proyectos.mapper.GenericMapper;
import com.proyectos.model.Categoria;
import com.proyectos.model.Proyecto;
import com.proyectos.persistence.dao.CategoriaDao;
import com.proyectos.persistence.dao.CategoriaDaoPostgres;
import com.proyectos.persistence.dao.ProyectoDaoPostgres;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

public class ProyectoService {
    private final ProyectoDaoPostgres proyectoDaoPostgres = new ProyectoDaoPostgres();
    private final CategoriaDao dao = new CategoriaDaoPostgres();
    private final GenericMapper<GetProyecto,Proyecto> genericMapper = new GenericMapper<>();
    private final Cliente<GetOrganizacion> cliente = new Cliente<>();
    private final Logger logger = Logger.getLogger(ProyectoService.class.getName());
    

    public Proyecto save(String nombre, String descripcion, String ubicacion, String requisitos, Date fechaInicio, Date fechaFin, Integer voluntarios_requeridos, Long idCategoria, Long idOrganizacion){

        Categoria categoria = dao.findById(idCategoria).get();

        Proyecto nuevoProyecto = proyectoDaoPostgres.save(new Proyecto(nombre,descripcion,ubicacion,requisitos,fechaInicio,fechaFin,voluntarios_requeridos,categoria,idOrganizacion)) ;

        return nuevoProyecto;
    }

    public List<GetProyecto> findAllProyectosByOrganizacion(Long idOrganizacion){
        List<Proyecto> proyectos = proyectoDaoPostgres.findAllProyectosByOrganizacion(idOrganizacion);

        GetOrganizacion organizacion = cliente.getById(idOrganizacion,"http://usuarios:8080/organizaciones", GetOrganizacion.class);

        

        return proyectos.stream()
                                .map(p -> {
                                    p.setOrganizacion(organizacion);
                                    return p; // importante devolver el mismo objeto
                                })
                                .map(p -> genericMapper.toDto(p, GetProyecto.class))
                                .toList();
    }

    public Proyecto update(Long id, String nombre, String descripcion, String ubicacion, String requisitos, Date fechaInicio, Date fechaFin, Integer voluntarios_requeridos, Long idCategoria){
        
        Categoria categoria = (new CategoriaDaoPostgres()).findById(idCategoria).get();
        Proyecto proyectoUpdate = new Proyecto(id,nombre,descripcion,ubicacion,requisitos,fechaInicio,fechaFin,voluntarios_requeridos,categoria);
        return proyectoDaoPostgres.update(proyectoUpdate);
    }

    public GetProyecto findById(Long id) {
        Proyecto proyecto = proyectoDaoPostgres.findById(id).get();
        GetOrganizacion organizacion = cliente.getById(proyecto.getIdOrganizacion(), "http://usuarios:8080/organizaciones", GetOrganizacion.class);

        logger.info("Organizacion:\n"+organizacion);

        proyecto.setOrganizacion(organizacion);
        return genericMapper.toDto(proyecto, GetProyecto.class);
    }
}

