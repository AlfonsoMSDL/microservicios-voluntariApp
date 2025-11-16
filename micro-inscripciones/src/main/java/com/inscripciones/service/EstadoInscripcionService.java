package com.inscripciones.service;

import java.util.List;

import com.inscripciones.dto.GetEstadoInscripcion;
import com.inscripciones.mapper.GenericMapper;
import com.inscripciones.model.EstadoInscripcion;
import com.inscripciones.persistence.EstadoInscripcionDao;

public class EstadoInscripcionService {
    private EstadoInscripcionDao estadoInscripcionDao;
    private GenericMapper<GetEstadoInscripcion, EstadoInscripcion> genericMapper  = new GenericMapper();
    
    public EstadoInscripcionService(){
        estadoInscripcionDao = new EstadoInscripcionDao();
    }

    public List<GetEstadoInscripcion> findAll(){
        List<EstadoInscripcion> estadosInscripcion = estadoInscripcionDao.findAll();

        return estadosInscripcion
                .stream()
                .map(e -> genericMapper.toDto(e,GetEstadoInscripcion.class))
                .toList();
    }
}
