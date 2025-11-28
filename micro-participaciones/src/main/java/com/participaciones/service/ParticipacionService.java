package com.participaciones.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.participaciones.client.Cliente;
import com.participaciones.dto.GetParticipacion;
import com.participaciones.dto.GetProyecto;
import com.participaciones.dto.GetVoluntario;
import com.participaciones.mapper.GenericMapper;
import com.participaciones.model.Participacion;
import com.participaciones.persistence.ParticipacionDao;
import com.participaciones.persistence.fabrica.ParticipacionFabrica;
import com.participaciones.persistence.impl.ParticipacionPostgresqlDao;

public class ParticipacionService {

    private  final ParticipacionDao participacionDao = ParticipacionFabrica.getImplementacion("postgresql");
    private final GenericMapper<GetParticipacion, Participacion> mapperParticipacion = new GenericMapper();
    private final Cliente<GetVoluntario> voluntarioClient = new Cliente<>();
    private final Cliente<GetProyecto> proyectoClient = new Cliente<>();

    public List<GetParticipacion> findAllParticipacionesByIdProyecto(Long id) {
        List<Participacion> participaciones =  participacionDao.findAllParticipacionesByIdProyecto(id);
        GetProyecto proyecto = proyectoClient.getById(id, "http://proyectos:8080/proyectos", GetProyecto.class);

        for (Participacion p : participaciones) {
            //Traigo al voluntario
            Long idVoluntario = p.getIdVoluntario();
            GetVoluntario voluntario = voluntarioClient.getById(idVoluntario, "http://usuarios:8080/voluntarios", GetVoluntario.class);
            p.setVoluntario(voluntario);

            p.setProyecto(proyecto);
        }

        return participaciones.stream().
                                    map(p -> mapperParticipacion.toDto(p, GetParticipacion.class))
                                    .toList();
    }

    public List<GetParticipacion> findAllParticipacionesByIdVoluntario(Long id){
        List<Participacion> participaciones = participacionDao.findAllParticipacionesByIdVoluntario(id);
        GetVoluntario voluntario = voluntarioClient.getById(id, "http://usuarios:8080/voluntarios", GetVoluntario.class);

        for (Participacion p : participaciones){
            Long idProyecto = p.getIdProyecto();
            GetProyecto proyecto = proyectoClient.getById(idProyecto, "http://proyectos:8080/proyectos", GetProyecto.class);
            p.setProyecto(proyecto);

            p.setVoluntario(voluntario);
        }
        return participaciones.stream().
                                    map(p -> mapperParticipacion.toDto(p, GetParticipacion.class))
                                    .toList();
    }

    public GetParticipacion save(Long idVoluntario, Long idProyecto, Date fecha_inicio, int horas_realizadas) {
        Participacion p = new Participacion(idVoluntario, idProyecto, fecha_inicio, horas_realizadas);
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

    public Participacion update(Long idParticipacion, Date fechaFin, int horasRealizadas) {
        Participacion p = new Participacion(idParticipacion, fechaFin, horasRealizadas);
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

    public boolean eliminarParticipacionesPorVoluntario(Long idVoluntario) throws SQLException {
        return participacionDao.deleteByIdVoluntario(idVoluntario);
    }
}
