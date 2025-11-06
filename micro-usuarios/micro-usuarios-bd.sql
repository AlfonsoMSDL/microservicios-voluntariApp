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