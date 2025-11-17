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


---------------------------------------------------------
-- PROYECTOS
---------------------------------------------------------

-- Organización 2: Fundación Verde Futuro (Ambiental)
INSERT INTO proyectos 
(nombre, descripcion, ubicacion, requisitos, fecha_inicio, fecha_fin, voluntarios_requeridos, id_categoria, organizacion_id)
VALUES
('Reforestación Sierra Nevada',
 'Proyecto para restaurar áreas afectadas por incendios mediante jornadas de siembra de árboles nativos.',
 'Sierra Nevada de Santa Marta',
 'Buen estado físico, gusto por actividades al aire libre.',
 '2025-03-01', '2025-06-30', 40, 1, 2),

('Limpieza de Playas',
 'Campaña mensual de limpieza y sensibilización ambiental en playas de la región Caribe.',
 'Santa Marta - El Rodadero',
 'Disponibilidad fines de semana, hidratación personal.',
 '2025-01-10', '2025-12-20', 25, 1, 2);

---------------------------------------------------------
-- Organización 3: Red Escolar Solidaria (Educativa)
INSERT INTO proyectos 
(nombre, descripcion, ubicacion, requisitos, fecha_inicio, fecha_fin, voluntarios_requeridos, id_categoria, organizacion_id)
VALUES
('Tutorías Escolares Gratuitas',
 'Apoyo académico para niños de primaria en áreas como matemáticas, lectura y ciencias.',
 'Barrio Pescaito, Santa Marta',
 'Paciencia, gusto por enseñar.',
 '2025-02-01', '2025-11-30', 30, 2, 3),

('Donación de Útiles Escolares',
 'Recolección y entrega de kits escolares a comunidades rurales de difícil acceso.',
 'Zona Bananera, Magdalena',
 'Capacidad para cargar cajas ligeras, trabajo en equipo.',
 '2025-04-01', '2025-04-30', 20, 2, 3);

---------------------------------------------------------
-- Organización 4: Brigada Médica Caribe (Salud Comunitaria)
INSERT INTO proyectos 
(nombre, descripcion, ubicacion, requisitos, fecha_inicio, fecha_fin, voluntarios_requeridos, id_categoria, organizacion_id)
VALUES
('Jornadas Médicas Rurales',
 'Brigadas móviles con atención general, control de signos vitales y vacunación.',
 'Aracataca, Magdalena',
 'Conocimientos básicos en salud o disposición para apoyar logística.',
 '2025-01-15', '2025-08-15', 50, 3, 4),

('Prevención de Enfermedades Respiratorias',
 'Charlas, entrega de kits y campañas informativas en barrios vulnerables.',
 'Santa Marta',
 'Disponibilidad en tardes, habilidades comunicativas.',
 '2025-05-10', '2025-10-30', 35, 3, 4);

---------------------------------------------------------
-- Organización 5: Hogar Animal Colombia (Bienestar Animal)
INSERT INTO proyectos 
(nombre, descripcion, ubicacion, requisitos, fecha_inicio, fecha_fin, voluntarios_requeridos, id_categoria, organizacion_id)
VALUES
('Rescate de Animales en Calle',
 'Apoyo en rescates, limpieza de heridas superficiales y entrega en refugios.',
 'Santa Marta',
 'Amor por los animales, no tener alergias severas.',
 '2025-02-15', '2025-09-30', 20, 4, 5),

('Censo y Esterilización Animal',
 'Recorrido por barrios para censar animales y apoyar jornadas de esterilización.',
 'Gaira, Santa Marta',
 'Mayor de edad, disposición para caminar largas distancias.',
 '2025-03-20', '2025-07-20', 15, 4, 5);

---------------------------------------------------------
-- Organización 6: Cultura para Todos (Cultura y Arte)
INSERT INTO proyectos 
(nombre, descripcion, ubicacion, requisitos, fecha_inicio, fecha_fin, voluntarios_requeridos, id_categoria, organizacion_id)
VALUES
('Festival de Arte Comunitario',
 'Organización de talleres, muralismo y actividades artísticas en espacios públicos.',
 'Centro Histórico de Santa Marta',
 'Creatividad, habilidades artísticas básicas.',
 '2025-06-01', '2025-06-30', 30, 7, 6),

('Taller de Fotografía para Jóvenes',
 'Capacitación en fotografía y producción artística para jóvenes de escasos recursos.',
 'Plenitud Cultural, Santa Marta',
 'Manejo básico de cámaras o dispositivos móviles.',
 '2025-08-01', '2025-11-01', 15, 7, 6);
