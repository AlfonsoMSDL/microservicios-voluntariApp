package com.usuariosService.service;

import com.usuariosService.dto.GetOrganizacion;
import com.usuariosService.dto.GetTipoOrganizacion;
import com.usuariosService.mapper.GenericMapper;
import com.usuariosService.model.Organizacion;
import com.usuariosService.model.TipoOrganizacion;
import com.usuariosService.persistence.TipoOrganizacionDao;

import java.util.List;

public class TipoOrganizacionService {
    private TipoOrganizacionDao tipoOrganizacionDao = new TipoOrganizacionDao();
    private final GenericMapper<GetTipoOrganizacion, TipoOrganizacion> genericMapper = new  GenericMapper<>();
    public List<GetTipoOrganizacion> findAll(){
        List<TipoOrganizacion> tiposOrganizaciones =  tipoOrganizacionDao.findAll();


        return tiposOrganizaciones.stream()
                .map(t -> genericMapper.toDto(t,GetTipoOrganizacion.class))
                .toList();
    }
}
