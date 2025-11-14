-- Tabla Participaciones (Voluntario "participa" en Proyecto)
CREATE TABLE participaciones (
    id SERIAL PRIMARY KEY,
    voluntario_id BIGINT NOT NULL,
    proyecto_id INT NOT NULL,
    fecha_inicio DATE,
    fecha_fin DATE,
    horas_realizadas NUMERIC(5,2)
);