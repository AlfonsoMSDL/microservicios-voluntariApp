package com.inscripciones.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.inscripciones.dto.GetInscripcion;
import com.inscripciones.dto.GetProyecto;
import com.inscripciones.dto.GetVoluntario;
import com.inscripciones.mapper.GenericMapper;
import com.inscripciones.model.EstadoInscripcion;
import com.inscripciones.model.Inscripcion;
import com.inscripciones.persistence.EstadoInscripcionDao;
import com.inscripciones.persistence.InscripcionDao;
import com.inscripciones.persistence.fabrica.InscripcionFabrica;
import com.inscripciones.persistence.impl.InscripcionPostgresqlDao;

public class InscripcionService {
    private final EstadoInscripcionDao estadoInscripcionDao = new EstadoInscripcionDao();
    private final InscripcionDao inscripcionDao = InscripcionFabrica.getImplementacion("postgresql");
    private final GenericMapper<GetInscripcion, Inscripcion> genericMapper = new GenericMapper();
    private final Cliente<GetProyecto> clienteProyecto = new Cliente<>();
    private final Cliente<GetVoluntario> clienteVoluntario = new Cliente<>();

    public Inscripcion save(String motivacion, Long idProyecto, Long idVoluntario, Date fechaInscripcion, Long idEstadoInscripcion){
        EstadoInscripcion estadoInscripcion = estadoInscripcionDao.findByID(idEstadoInscripcion).get();
        Inscripcion inscripcion = new Inscripcion(idProyecto,idVoluntario,motivacion,fechaInscripcion,estadoInscripcion);
        Inscripcion nuevaInscripcion = inscripcionDao.save(inscripcion);

        return nuevaInscripcion;
    }

    public List<GetInscripcion> findAllInscripcionesByVoluntario(Long idVoluntario){
        List<Inscripcion> inscripciones = inscripcionDao.findAllInscripcionesByIdVoluntario(idVoluntario);
        GetVoluntario voluntario = clienteVoluntario.getById(idVoluntario, "http://usuarios:8080/voluntarios", GetVoluntario.class);

        for(Inscripcion i : inscripciones){
            Long idProyecto = i.getIdProyecto();
            GetProyecto proyecto = clienteProyecto.getById(idProyecto, "http://proyectos:8080/proyectos", GetProyecto.class);
            i.setProyecto(proyecto);

            i.setVoluntario(voluntario);
        }
        return inscripciones.stream()
                                .map(i -> genericMapper.toDto(i, GetInscripcion.class))
                                .toList();
    }

    public List<GetInscripcion> findAllInscripcionesByProyecto(Long idProyecto){
        List<Inscripcion> inscripciones = inscripcionDao.findAllInscripcionesByIdProyecto(idProyecto);
        GetProyecto proyecto = clienteProyecto.getById(idProyecto, "http://proyectos:8080/proyectos", GetProyecto.class);

        for (Inscripcion i : inscripciones){
            Long idVoluntario = i.getIdVoluntario();
            GetVoluntario voluntario = clienteVoluntario.getById(idVoluntario, "http://usuarios:8080/voluntarios", GetVoluntario.class);
            i.setVoluntario(voluntario);

            i.setProyecto(proyecto);
        }
        

        return inscripciones.stream()
                                .map(i -> genericMapper.toDto(i, GetInscripcion.class))
                                .toList();
    }

    public Inscripcion update(Long id, String motivacion){
        Inscripcion inscripcionUpdate = new Inscripcion(id, motivacion);
        return inscripcionDao.update(inscripcionUpdate);

    }

    public Inscripcion updateEstado(Long id, Long idEstadoInscripcion){
        EstadoInscripcion estadoInscripcion = estadoInscripcionDao.findByID(idEstadoInscripcion).get();
        Inscripcion inscripcionUpdate = new Inscripcion(id, estadoInscripcion);

        return inscripcionDao.updateEstado(inscripcionUpdate);
    }

    public GetInscripcion findById(Long id){
        Optional<Inscripcion> inscripcion = inscripcionDao.findById(id);

        if(inscripcion.isPresent()){
            Inscripcion i = inscripcion.get();

            Long idVoluntario = i.getIdVoluntario();
            GetVoluntario voluntario = clienteVoluntario.getById(idVoluntario, "http://usuarios:8080/voluntarios", GetVoluntario.class);
            i.setVoluntario(voluntario);

            Long idProyecto = i.getIdProyecto();
            GetProyecto proyecto = clienteProyecto.getById(idProyecto, "http://proyectos:8080/proyectos", GetProyecto.class);
            i.setProyecto(proyecto);

            return genericMapper.toDto(i, GetInscripcion.class);
        }
        return null;
    }

    public boolean delete(Long id){
        return inscripcionDao.delete(id);
    }

    public boolean deleteByIdVoluntario(Long id) throws SQLException{
        return inscripcionDao.deleteByIdVoluntario(id);
    }
}
