package com.participaciones.persistence;


import com.participaciones.model.Participacion;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ParticipacionDao {

    // CREATE
    Participacion save(Participacion participacion);

    // READ ALL by proyecto
    List<Participacion> findAllParticipacionesByIdProyecto(Long idProyecto);

    // READ ALL by voluntario
    List<Participacion> findAllParticipacionesByIdVoluntario(Long idVoluntario);

    // READ by ID
    Optional<Participacion> findById(Long id);

    // UPDATE
    Participacion update(Participacion participacion);

    // DELETE
    boolean delete(Long id);

    boolean deleteByIdVoluntario(Long id) throws SQLException;
    boolean deleteByIdProyecto(Long id) throws SQLException;
}
