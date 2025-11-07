package com.proyectos.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.sql.Date;

public class SqlDateFromTimestampDeserializer extends StdDeserializer<Date> {

    public SqlDateFromTimestampDeserializer() {
        super(Date.class);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        try {
            // Si el valor es un número dentro de comillas ("1680566400000")
            long millis = Long.parseLong(value);
            return new Date(millis);
        } catch (NumberFormatException e) {
            // Si no es número, intenta parsear como yyyy-MM-dd
            try {
                return Date.valueOf(value);
            } catch (IllegalArgumentException ex) {
                throw new IOException("Formato de fecha no reconocido: " + value);
            }
        }
    }
}
