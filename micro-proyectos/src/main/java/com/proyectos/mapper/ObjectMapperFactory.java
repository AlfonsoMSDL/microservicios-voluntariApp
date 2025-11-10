package com.proyectos.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.text.SimpleDateFormat;

public class ObjectMapperFactory {

    private static final ObjectMapper MAPPER = create();

    private static ObjectMapper create() {
        ObjectMapper mapper = new ObjectMapper();

        // Ignorar propiedades desconocidas
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // No escribir fechas como timestamps (números)
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Incluir solo campos no nulos
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Usar formato yyyy-MM-dd para TODAS las fechas
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        return mapper;
    }

    public static ObjectMapper get() {
        return MAPPER;
    }
}
