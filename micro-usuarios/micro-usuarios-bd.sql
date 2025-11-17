-- Tabla Roles
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500)
);

-- Tabla Usuarios
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    clave VARCHAR(255) NOT NULL,
    id_rol INT NOT NULL,
    url_imagen VARCHAR(255),
    nombre_usuario VARCHAR(255),
    FOREIGN KEY (id_rol) REFERENCES roles(id)
);

-- Tabla Tipo de Organización
CREATE TABLE tipo_organizacion (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500)
);

-- Tabla Organizaciones
CREATE TABLE organizaciones (
    id BIGINT PRIMARY KEY,
    tipo_organizacion_id INT NOT NULL,
    descripcion TEXT default 'Sin descripción',
    FOREIGN KEY (id) REFERENCES usuarios(id),
    FOREIGN KEY (tipo_organizacion_id) REFERENCES tipo_organizacion(id)
);

-- Tabla Voluntarios
CREATE TABLE voluntarios (
    id BIGINT PRIMARY KEY,
    apellido VARCHAR(100) NOT NULL,
    habilidades TEXT,
    experiencia TEXT,
    disponibilidad VARCHAR(100),
    areas_interes TEXT,
    FOREIGN KEY (id) REFERENCES usuarios(id)
);

-- Inserciones iniciales
INSERT INTO tipo_organizacion (tipo, descripcion) VALUES
    ('Ambiental', 'Organización dedicada a la protección del medio ambiente, reforestación y limpieza de espacios públicos.'),
    ('Educativa', 'Entidad que promueve la educación gratuita, tutorías y alfabetización en comunidades vulnerables.'),
    ('Salud Comunitaria', 'Grupo que realiza jornadas médicas, campañas de vacunación y promoción de hábitos saludables.'),
    ('Animalista', 'Organización orientada al rescate, adopción y bienestar de animales en situación de calle.'),
    ('Cultural', 'Asociación que impulsa actividades artísticas, festivales locales y preservación de la cultura regional.'),
    ('Juvenil', 'Colectivo enfocado en el liderazgo y participación de jóvenes en proyectos de desarrollo comunitario.'),
    ('Social y Humanitaria', 'Organización que brinda apoyo a personas en situación de pobreza o emergencias.'),
    ('Deportiva Comunitaria', 'Grupo que organiza actividades deportivas para fomentar la integración y la salud física.'),
    ('Tecnológica y de Innovación Social', 'Iniciativa que busca acercar la tecnología y la capacitación digital a comunidades con bajos recursos.'),
    ('Voluntariado Religioso', 'Grupo de servicio social promovido por instituciones religiosas para apoyar causas solidarias.');

INSERT INTO roles (nombre, descripcion) VALUES
    ('Voluntario','Usuario que quiere ayudar'),
    ('Organizacion','Entidad que publica proyectos comunitarios'),
    ('Administrador','Gestiona la plataforma');



---------------------------------------------------------
-- ROLES (ya existen) → NO SE MODIFICAN
---------------------------------------------------------
-- 1 Voluntario
-- 2 Organizacion
-- 3 Administrador

---------------------------------------------------------
-- TIPO DE ORGANIZACIÓN (ya existen) → NO SE MODIFICAN
---------------------------------------------------------

---------------------------------------------------------
-- USUARIOS
---------------------------------------------------------

-- ADMINISTRADOR
INSERT INTO usuarios (id, nombre, correo, telefono, clave, id_rol, url_imagen, nombre_usuario)
VALUES 
(1, 'Admin Master', 'admin@gmail.com', '3000000000', 'admin', 3, NULL, 'Admin');

---------------------------------------------------------
-- ORGANIZACIONES (Usuarios con rol Organizacion)
---------------------------------------------------------

INSERT INTO usuarios (id, nombre, correo, telefono, clave, id_rol, url_imagen, nombre_usuario)
VALUES
(2, 'Fundación Verde Futuro', 'contacto@verdefuturo.org', '3001112233', 'pass123', 2, NULL, 'verdefuturo'),
(3, 'Red Escolar Solidaria', 'info@redescolar.org', '3002223344', 'pass123', 2, NULL, 'redescolar'),
(4, 'Brigada Médica Caribe', 'salud@brigadacaribe.org', '3003334455', 'pass123', 2, NULL, 'brigadacaribe'),
(5, 'Hogar Animal Colombia', 'contacto@hogaranimal.co', '3004445566', 'pass123', 2, NULL, 'hogaranimal'),
(6, 'Cultura para Todos', 'contacto@culturatodos.org', '3005556677', 'pass123', 2, NULL, 'culturatodos');

---------------------------------------------------------
-- ORGANIZACIONES (tabla organizaciones)
---------------------------------------------------------

INSERT INTO organizaciones (id, tipo_organizacion_id, descripcion)
VALUES
(2, 1, 'Promueve la reforestación y el cuidado ambiental.'),
(3, 2, 'Programa educativo para comunidades vulnerables.'),
(4, 3, 'Entidad de salud comunitaria con jornadas médicas.'),
(5, 4, 'Refugio de rescate y adopción animal.'),
(6, 5, 'Promueve actividades culturales y talleres artísticos.');

---------------------------------------------------------
-- VOLUNTARIOS (Usuarios con rol Voluntario)
---------------------------------------------------------

INSERT INTO usuarios (id, nombre, correo, telefono, clave, id_rol, url_imagen, nombre_usuario)
VALUES
(10, 'Alfonso', 'alfonso@gmail.com', '3045440396', '1234', 1, NULL, 'almasag'),
(11, 'Daniela Ruiz', 'dani@gmail.com', '3011112222', 'pass123', 1, NULL, 'druiz'),
(12, 'Carlos Gómez', 'carlosg@gmail.com', '3022223333', 'pass123', 1, NULL, 'cgomez'),
(13, 'Mariana Torres', 'maritor@gmail.com', '3033334444', 'pass123', 1, NULL, 'mtorres'),
(14, 'Juan Pérez', 'jperez@gmail.com', '3044445555', 'pass123', 1, NULL, 'jperez'),
(15, 'Lucía Mendoza', 'luciam@gmail.com', '3055556666', 'pass123', 1, NULL, 'lmendoza'),
(16, 'Santiago Rojas', 'srojas@gmail.com', '3066667777', 'pass123', 1, NULL, 'srojas'),
(17, 'Elena Duarte', 'eduarte@gmail.com', '3077778888', 'pass123', 1, NULL, 'eduarte'),
(18, 'Miguel Castro', 'miguelc@gmail.com', '3088889999', 'pass123', 1, NULL, 'mcastro'),
(19, 'Adriana López', 'adri@gmail.com', '3099990000', 'pass123', 1, NULL, 'adlopez');

---------------------------------------------------------
-- VOLUNTARIOS (Tabla voluntarios)
---------------------------------------------------------

INSERT INTO voluntarios (id, apellido, habilidades, experiencia, disponibilidad, areas_interes)
VALUES
(10, 'Salazar', 'Programación, liderazgo', 'Participación en proyectos sociales', 'Fines de semana', 'Tecnología, educación'),
(11, 'Ruiz', 'Diseño gráfico', 'Voluntariado en campañas educativas', 'Tardes', 'Cultura y educación'),
(12, 'Gómez', 'Primeros auxilios', 'Apoyo en brigadas médicas', 'Mañanas', 'Salud'),
(13, 'Torres', 'Enseñanza', 'Talleres comunitarios', 'Fines de semana', 'Educación'),
(14, 'Pérez', 'Trabajo manual', 'Construcción de espacios comunitarios', 'Cualquier horario', 'Desarrollo rural'),
(15, 'Mendoza', 'Fotografía', 'Registro de eventos comunitarios', 'Tardes', 'Arte y cultura'),
(16, 'Rojas', 'Programación', 'Mentorías digitales', 'Noches', 'Tecnología'),
(17, 'Duarte', 'Cuidado animal', 'Apoyo en refugios', 'Fines de semana', 'Animalista'),
(18, 'Castro', 'Logística', 'Eventos y jornadas comunitarias', 'Mañanas', 'Social'),
(19, 'López', 'Cocina', 'Apoyo en comedores comunitarios', 'Tardes', 'Social y humanitario');

SELECT setval(pg_get_serial_sequence('usuarios', 'id'), (SELECT MAX(id) FROM usuarios));
