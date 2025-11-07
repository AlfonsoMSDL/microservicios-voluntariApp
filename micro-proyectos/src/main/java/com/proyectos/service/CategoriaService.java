package com.proyectos.service;

import com.proyectos.dto.GetCategoria;
import com.proyectos.mapper.GenericMapper;
import com.proyectos.model.Categoria;
import com.proyectos.persistence.CategoriaDao;

import java.util.List;

public class CategoriaService {
    private CategoriaDao categoriaDao;
    private GenericMapper<GetCategoria,Categoria> genericMapper = new GenericMapper();
    public CategoriaService() {
        categoriaDao = new CategoriaDao();
    }

    public List<GetCategoria> findAll() {
        List<Categoria> categorias =  categoriaDao.findAll();
        return categorias.stream()
                .map(c -> genericMapper.toDto(c,GetCategoria.class))
                .toList();
    }
}
