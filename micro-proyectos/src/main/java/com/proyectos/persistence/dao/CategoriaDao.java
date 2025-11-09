package com.proyectos.persistence.dao;

import com.proyectos.model.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaDao {

    Categoria save(Categoria categoria);
    Optional<Categoria> findById(Long id);
    List<Categoria> findAll();
    Categoria update(Categoria categoria);
    boolean delete(Long id);
}
