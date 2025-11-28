package com.proyectos.persistence;

import com.proyectos.model.Proyecto;

import java.util.List;
import java.util.Optional;

public interface ProyectoDao {

    // CREATE
    Proyecto save(Proyecto proyecto);

    // READ ALL BY ORGANIZACION
    List<Proyecto> findAllProyectosByOrganizacion(Long idOrganizacion);

    // READ ALL
    List<Proyecto> findAllProyectos();

    // UPDATE
    Proyecto update(Proyecto proyecto);

    // READ BY ID
    Optional<Proyecto> findById(Long idProyecto);

    boolean delete(Long id);
}
