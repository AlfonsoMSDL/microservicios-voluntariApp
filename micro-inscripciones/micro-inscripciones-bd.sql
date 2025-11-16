-- Tabla Estados de Inscripción
CREATE TABLE estados_inscripcion (
                                     id SERIAL PRIMARY KEY,
                                     nombre VARCHAR(50) NOT NULL,
                                     descripcion VARCHAR(500)
);

-- Tabla Inscripcion (Voluntario "realiza" inscripción en Proyecto)
CREATE TABLE inscripciones (
                               id SERIAL PRIMARY KEY,
                               voluntario_id BIGINT NOT NULL,
                               proyecto_id BIGINT NOT NULL,
                               motivacion TEXT,
                               fecha_inscripcion DATE,
                               id_estado INT NOT NULL,
                               FOREIGN KEY (id_estado) REFERENCES estados_inscripcion(id)
);

--Inserciones en Estados de Inscripción
INSERT INTO estados_inscripcion (nombre, descripcion)
VALUES
    ('En revisión', 'La solicitud de inscripción ha sido recibida y está siendo evaluada.'),
    ('Aprobada', 'La solicitud de inscripción ha sido aceptada exitosamente.'),
    ('Rechazada', 'La solicitud de inscripción no cumple con los requisitos y ha sido denegada.');
