package com.inscripciones.persistence;


import com.inscripciones.model.Inscripcion;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface InscripcionDao {

    // CREATE
    Inscripcion save(Inscripcion inscripcion);

    // UPDATE (motivación)
    Inscripcion update(Inscripcion inscripcion);

    // UPDATE estado
    Inscripcion updateEstado(Inscripcion inscripcion);

    // READ ALL by proyecto
    List<Inscripcion> findAllInscripcionesByIdProyecto(Long idProyecto);

    // READ ALL by voluntario
    List<Inscripcion> findAllInscripcionesByIdVoluntario(Long idVoluntario);

    // READ BY ID
    Optional<Inscripcion> findById(Long id);

    // DELETE
    boolean delete(Long id);

    boolean deleteByIdVoluntario(Long id) throws SQLException;
}
