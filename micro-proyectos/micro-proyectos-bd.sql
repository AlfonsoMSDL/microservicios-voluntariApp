-- Tabla Categorías
CREATE TABLE categorias (
                            id SERIAL PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL,
                            descripcion VARCHAR(500)
);

-- Tabla Proyectos
CREATE TABLE proyectos (
                           id SERIAL PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL,
                           descripcion TEXT,
                           ubicacion VARCHAR(100),
                           requisitos TEXT,
                           fecha_inicio DATE,
                           fecha_fin DATE,
                           voluntarios_requeridos INT,
                           id_categoria INT NOT NULL,
                           organizacion_id BIGINT NOT NULL,
                           FOREIGN KEY (id_categoria) REFERENCES categorias(id)
);


-- Inserciones de ejemplo para la tabla categorias

INSERT INTO categorias (nombre, descripcion) VALUES
('Medio Ambiente', 'Proyectos enfocados en la conservación de recursos naturales, reforestación y protección de ecosistemas.'),
('Educación', 'Iniciativas para mejorar la calidad educativa, ofrecer tutorías o donar materiales escolares.'),
('Salud Comunitaria', 'Actividades relacionadas con brigadas médicas, promoción de la salud y apoyo a hospitales.'),
('Bienestar Animal', 'Proyectos orientados al rescate, adopción y cuidado responsable de animales.'),
('Inclusión Social', 'Programas que buscan integrar a poblaciones vulnerables o en situación de riesgo.'),
('Desarrollo Rural', 'Iniciativas para mejorar la calidad de vida en comunidades campesinas o rurales.'),
('Cultura y Arte', 'Proyectos que promueven la expresión artística y preservación del patrimonio cultural.'),
('Tecnología y Educación Digital', 'Voluntariados que fomentan la alfabetización digital y el acceso a la tecnología.'),
('Emergencias y Desastres', 'Apoyo en situaciones de catástrofes naturales o emergencias humanitarias.'),
('Emprendimiento Social', 'Proyectos que fortalecen pequeñas empresas comunitarias y fomentan la economía solidaria.');
