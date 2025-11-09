package com.proyectos.service;

import com.proyectos.dto.GetCategoria;
import com.proyectos.mapper.GenericMapper;
import com.proyectos.model.Categoria;
import com.proyectos.persistence.dao.CategoriaDaoPostgres;

import java.util.List;

public class CategoriaService {
    private CategoriaDaoPostgres categoriaDaoPostgres;
    private GenericMapper<GetCategoria,Categoria> genericMapper = new GenericMapper();
    public CategoriaService() {
        categoriaDaoPostgres = new CategoriaDaoPostgres();
    }

    public List<GetCategoria> findAll() {
        List<Categoria> categorias =  categoriaDaoPostgres.findAll();
        return categorias.stream()
                .map(c -> genericMapper.toDto(c,GetCategoria.class))
                .toList();
    }
}
