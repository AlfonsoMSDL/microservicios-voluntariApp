package com.participaciones.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.participaciones.client.Cliente;
import com.participaciones.dto.GetParticipacion;
import com.participaciones.dto.GetProyecto;
import com.participaciones.dto.GetVoluntario;
import com.participaciones.mapper.GenericMapper;
import com.participaciones.model.Participacion;
import com.participaciones.persistence.ParticipacionDao;

public class ParticipacionService {

    private  final ParticipacionDao participacionDao = new ParticipacionDao();
    private final GenericMapper<GetParticipacion, Participacion> mapperParticipacion = new GenericMapper();
    private final Cliente<GetVoluntario> voluntarioClient = new Cliente<>();
    private final Cliente<GetProyecto> proyectoClient = new Cliente<>();

    public List<GetParticipacion> findAllParticipacionesByIdProyecto(Long id) {
        List<Participacion> participaciones =  participacionDao.findAllParticipacionesByIdProyecto(id);

        for (Participacion p : participaciones) {
            //Traigo al voluntario
            Long idVoluntario = p.getIdVoluntario();
            GetVoluntario voluntario = voluntarioClient.getById(idVoluntario, "http://usuarios:8080/voluntarios", GetVoluntario.class);
            p.setVoluntario(voluntario);

            //Traigo al proyecto
            Long idProyecto = p.getIdProyecto();
            GetProyecto proyecto = proyectoClient.getById(idProyecto, "http://proyectos:8080/proyectos", GetProyecto.class);
            p.setProyecto(proyecto);
        }

        return participaciones.stream().
                                    map(p -> mapperParticipacion.toDto(p, GetParticipacion.class))
                                    .toList();
    }

    public GetParticipacion save(Long idVoluntario, Long idProyecto, Date fechaInicio, int horasRealizadas) {
        Participacion p = new Participacion(idVoluntario, idProyecto, fechaInicio, horasRealizadas);
        Participacion pGuardada = participacionDao.save(p);

        //Traigo al voluntario
        Long idVoluntarioGuardado = pGuardada.getIdVoluntario();
        GetVoluntario voluntario = voluntarioClient.getById(idVoluntarioGuardado, "http://usuarios:8080/voluntarios", GetVoluntario.class);
        pGuardada.setVoluntario(voluntario);

        //Traigo al proyecto
        Long idProyectoGuardado = pGuardada.getIdProyecto();
        GetProyecto proyecto = proyectoClient.getById(idProyectoGuardado, "http://proyectos:8080/proyectos", GetProyecto.class);
        pGuardada.setProyecto(proyecto);

        return mapperParticipacion.toDto(pGuardada, GetParticipacion.class);
    }

    public Participacion update(Long idParticipacion,Long idVoluntario, Long idProyecto, Date fechaInicio, Date fechaFin, int horasRealizadas) {
        Participacion p = new Participacion(idParticipacion, idVoluntario, idProyecto, fechaInicio, fechaFin, horasRealizadas);
        return participacionDao.update(p);
    }

    public GetParticipacion findById(Long idBuscado) {
        Optional<Participacion> p = participacionDao.findById(idBuscado);

        if (p.isPresent()) {

            Participacion participacion = p.get();

            //Traigo al voluntario
            Long idVoluntario = participacion.getIdVoluntario();
            GetVoluntario voluntario = voluntarioClient.getById(idVoluntario, "http://usuarios:8080/voluntarios", GetVoluntario.class);
            participacion.setVoluntario(voluntario);

            //Traigo al proyecto
            Long idProyecto = participacion.getIdProyecto();
            GetProyecto proyecto = proyectoClient.getById(idProyecto, "http://usuarios:8080/voluntarios", GetProyecto.class);
            participacion.setProyecto(proyecto);
            
            return mapperParticipacion.toDto(participacion, GetParticipacion.class);
        }

        return null;
    }

}
