# VoluntariApp - Documento Descriptivo del Proyecto

## 1. Introducción
**VoluntariApp** es una plataforma diseñada para conectar organizaciones sin ánimo de lucro con voluntarios interesados en participar en proyectos sociales, ambientales, educativos, culturales, humanitarios, etc.  
La aplicación permite que las organizaciones publiquen proyectos y que los voluntarios puedan inscribirse, participar y hacer seguimiento de sus actividades.

---

## 2. Fundamentación del Proyecto
El proyecto se basa en la necesidad de centralizar y facilitar la interacción entre voluntarios y organizaciones, ofreciendo un sistema donde ambas partes puedan gestionar sus actividades de manera eficiente.  
La plataforma busca promover el impacto social y mejorar la visibilidad de iniciativas comunitarias.

---

## 3. Arquitectura del Sistema
El sistema está desarrollado bajo una **arquitectura de microservicios**. Cada microservicio cuenta con:

- Lógica independiente  
- Base de datos propia en **PostgreSQL**  
- Servidor propio con **Java y Servlets**  
- Comunicación vía **HTTP**, intercambiando datos en formato **JSON**

### Microservicios implementados:

- **Microservicio para el Frontend:** Contiene los html, css y js para todas las vistas.
- **Microservicio de Usuarios:** gestión de usuarios, roles, organizaciones y voluntarios.  
- **Microservicio de Proyectos:** permite a las organizaciones registrar y administrar proyectos.  
- **Microservicio de Inscripciones:** gestiona las inscripciones de los voluntarios a los proyectos.  
- **Microservicio de Participaciones:** administra el progreso y participación del voluntario.  
- **Microservicio de Reportes:** genera información consolidada del sistema.

---

## 4. Tecnologías Implementadas

### Backend
- Java con Servlets  
- Arquitectura de microservicios  
- PostgreSQL  
- DAO, DTO y Factory  

### Frontend
- HTML5  
- CSS3  
- JavaScript  

### Contenedores
- Docker  
- Docker Compose  

---

## 5. Funcionamiento General

- Las organizaciones se registran y publican proyectos.  
- Los voluntarios crean su cuenta y consultan los proyectos disponibles.  
- Los voluntarios pueden postularse a proyectos.  
- El microservicio de inscripciones valida solicitudes y gestiona sus estados.  
- Una vez aprobada la inscripción, el voluntario puede participar y registrar avances.  
- El microservicio de reportes consolida la información general del sistema.

---

## 6. Bases de Datos
Cada microservicio utiliza su propia **base de datos PostgreSQL** aislada, permitiendo:

- Independencia  
- Escalabilidad  
- Mantenimiento más sencillo  

---

## 7. Cómo Correr el Proyecto con Docker Compose

El sistema está completamente containerizado con Docker.  
Para ejecutarlo, es necesario tener instalados:

- **Docker**
- **Docker Compose**

Estas herramientas vienen instaladas con ```Docker Desktop```, así que es recomendable instalarlo.

### Pasos para ejecutar el proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/AlfonsoMSDL/microservicios-voluntariApp.git
   cd voluntary-app
    ```
2. Asegurarse de que el archivo ```compose.yml``` se encuentra en la raíz del proyecto.
3. Abre ```Docker Desktop``` dejalo corriendo en segundo plano.
3. Construir y levantar todos los microservicios con: ```docker compose up --build```
4. Esperar a que todos los contenedores inicialicen sus servicios.
5. Acceder al sistema a través del navegador en la URL correspondiente:
```http://localhost:8080/voluntariApp```


### ¿Qué levanta Docker Compose?

- Cada microservicio (usuarios, proyectos, inscripciones, participaciones, reportes).
- Sus bases de datos PostgreSQL.
- La red interna de comunicación entre contenedores.

Todo con un solo comando.

## 8. Estado Actual del Proyecto

Actualmente el proyecto se encuentra en:
- Fase de desarrollo activo.
- Microservicios funcionando y comunicándose correctamente.
- CRUD completo para usuarios, voluntarios, organizaciones y proyectos.
- Sistema de inscripciones con estados:

    * En revisión
    * Aprobada
    * Rechazada
- Participaciones funcionales.
- Microservicio de reportes en fase inicial.
- Contenedores estables con Docker Compose.


## 10. Conclusión

VoluntariApp es un proyecto sólido, modular y escalable, diseñado para generar impacto social y apoyar la colaboración entre organizaciones y voluntarios.
Su arquitectura basada en microservicios y su despliegue mediante contenedores lo posicionan como una solución moderna, organizada y preparada para seguir creciendo.