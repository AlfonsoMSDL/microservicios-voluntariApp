package com.participaciones.mapper;

import java.util.logging.Logger;

public class GenericMapper<Dto, Entity> {
    private final JsonMapper<Entity> entityMapper = new JsonMapper<>();
    private final JsonMapper<Dto> dtoMapper = new JsonMapper<>();
    private final Logger logger = Logger.getLogger(GenericMapper.class.getName());

    /*Este metodo generico es para convertir de un objeto de una clase generica Entity
    a un Dto generico. Lo que hace es convertir la entidad a Json y luego ese
    Json lo transforma a un Dto. Esto es posible gracias a JsonMapper, el cual usa jackson
    para estos mapeos
    */
    public Dto toDto(Entity objEntidad, Class<Dto> dtoClass) {
        String json = entityMapper.toJson(objEntidad);
        return dtoMapper.fromJson(json, dtoClass);
    }

    public Entity toEntity(Dto dto, Class<Entity> entityClass) {
        String json = dtoMapper.toJson(dto);
        return entityMapper.fromJson(json, entityClass);
    }
}
