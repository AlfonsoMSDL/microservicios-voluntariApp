package com.inscripciones.mapper;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper<T> {
    private final ObjectMapper mapper = ObjectMapperFactory.get();

    //Para convertir de un objeto de una clase generica a un Json
    public String toJson(T toConvert) {
        try {
            return mapper.writeValueAsString(toConvert);
        } catch (JsonProcessingException e) {

            throw new RuntimeException("Error al convertir objeto a JSON", e);
        }
    }

    //Para convertir de un Json a un objeto de una clase generica
    public T fromJson(String json, Class<T> toConvert) {
        try {
            return mapper.readValue(json, toConvert);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir json a objeto", e);
        }
    }

    public String toJson(List<T> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {

            throw new RuntimeException("Error al convertir objeto a JSON", e);
        }
    }
}
