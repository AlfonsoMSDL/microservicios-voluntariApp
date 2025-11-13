package com.inscripciones.service;

import java.util.List;
import java.util.logging.Logger;

import com.inscripciones.mapper.GenericMapper;
import com.inscripciones.model.EstadoInscripcion;
import com.inscripciones.model.Inscripcion;
import com.inscripciones.persistence.EstadoInscripcionDao;
import com.inscripciones.persistence.InscripcionDao;

public class InscripcionService {
    private final EstadoInscripcionDao estadoInscripcionDao = new EstadoInscripcionDao();
    private final InscripcionDao inscripcionDao = new InscripcionDao();
    private final GenericMapper<GetInscripcion, Inscripcion> genericMapper = new GenericMapper();
    private final Cliente<GetProyecto> clienteProyecto = new Cliente<>();
    private final Cliente<GetVoluntario> clienteVoluntario = new Cliente<>();
    private final Logger logger = Logger.getLogger(InscripcionService.class.getName());

    public Inscripcion save(String motivacion, Date fechaInscripcion, Long idEstadoInscripcion, Long idProyecto, Long idVoluntario){

        EstadoInscripcion estadoInscripcion = estadoInscripcionDao.findByID(idEstadoInscripcion).get();
        Inscripcion nuevaInscripcion = inscripcionDao.save(new Inscripcion(idProyecto, idVoluntario, motivacion, fechaInscripcion, estadoInscripcion));

        return nuevaInscripcion;
    }

    public List<GetInscripcion> findAllInscripcionesByVoluntario(Long idVoluntario){
        List<Inscripcion> inscripciones = inscripcionDao.findAllInscripcionesByIdVoluntario(idVoluntario);

        GetVoluntario voluntario = cliente.getById(idVoluntario, "http://usuarios:8080/voluntarios", GetVoluntario.class);

        return inscripciones.stream()
                                .map(p -> {
                                    p.setVoluntario(voluntario);
                                    return p;
                                })
                                .map(p -> genericMapper.toDto(p, GetInscripcion.class))
                                .toList();
    }

    public List<GetInscripcion> findAllInscripcionesByProyecto(Long idProyecto){
        List<Inscripcion> inscripciones = inscripcionDao.findAllInscripcionesByIdProyecto(idProyecto);

        GetProyecto proyecto = cliente.getById(idProyecto, "http://proyectos:8080/proyectos", GetProyecto.class);

        return inscripciones.stream()
                                .map(p -> {
                                    p.setProyecto(proyecto);
                                    return p;
                                })
                                .map(p -> genericMapper.toDto(p, GetInscripcion.class))
                                .toList();
    }

    public Inscripcion update(Long id, String motivacion, Date fechaInscripcion, Long idEstadoInscripcion){
        EstadoInscripcion estadoInscripcion = (new EstadoInscripcionDao()).findByID(idEstadoInscripcion).get();
        Inscripcion inscripcionUpdate = new Inscripcion(id, motivacion, fechaInscripcion, estadoInscripcion);
        return inscripcionDao.update(inscripcionUpdate);

    }

    public GetInscripcion findById(Long id){
        Inscripcion inscripcion = inscripcionDao.findById(id).get();
        GetVoluntario voluntario = cliente.getById(inscripcion.getIdVoluntario(), "http://usuarios:8080/voluntarios", GetVoluntario.class);
        GetProyecto proyecto = cliente.getById(inscripcion.getIdProyecto(), "http://proyectos:8080/proyectos", GetProyecto.class);

        logger.info("Voluntario: \n"+voluntario);
        logger.info("Proyecto: \n"+proyecto);

        inscripcion.setVoluntario(voluntario);
        inscripcion.setProyecto(proyecto);

        return genericMapper.toDto(inscripcion, GetInscripcion.class);
    }
}
