# Endpoints Disponibles
## 📊 Dashboard
GET /api/reportes/dashboard

##  Voluntarios
GET /api/reportes/voluntarios
GET /api/reportes/voluntarios?top=5
GET /api/reportes/voluntarios?id=1

##  Proyectos
GET /api/reportes/proyectos
GET /api/reportes/proyectos?top=5
GET /api/reportes/proyectos?id=1
GET /api/reportes/proyectos?porCategoria=true

##  Categorías
GET /api/reportes/categorias

##  Inscripciones
GET /api/reportes/inscripciones
GET /api/reportes/inscripciones?porEstado=true
GET /api/reportes/inscripciones?estado=Aprobada
GET /api/reportes/inscripciones?tendencia=true

##  Organizaciones
GET /api/reportes/organizaciones
GET /api/reportes/organizaciones?top=5
GET /api/reportes/organizaciones?id=1
GET /api/reportes/organizaciones?porTipo=true

# Endpoints Requeridos en Otros Microservicios
##  Checklist de Endpoints en micro-usuarios (puerto 8081):

###  Voluntarios
@WebServlet("/api/voluntarios")
GET /api/voluntarios                      // Lista todos
GET /api/voluntarios?action=getById&id=1  // Obtiene uno

###  Organizaciones
@WebServlet("/api/organizaciones")
GET /api/organizaciones                      // Lista todas
GET /api/organizaciones?action=getById&id=1  // Obtiene una

#### verifica:
curl http://localhost:8081/api/voluntarios
curl http://localhost:8081/api/organizaciones

##  Checklist de Endpoints en micro-proyectos (puerto 8082):
###  Proyectos
@WebServlet("/api/proyectos")
GET /api/proyectos                      // Lista todos
GET /api/proyectos?action=getById&id=1  // Obtiene uno

###  Categorías
@WebServlet("/api/categorias")
GET /api/categorias                      // Lista todas
GET /api/categorias?action=getById&id=1  // Obtiene una (opcional)

#### Verifica:
curl http://localhost:8082/api/proyectos
curl http://localhost:8082/api/categorias

##  Checklist de Endpoints en micro-inscripciones (puerto 8083):
###  Inscripciones
@WebServlet("/api/inscripciones")
GET /api/inscripciones                      // Lista todas
GET /api/inscripciones?action=getById&id=1  // Obtiene una
GET /api/inscripciones?idVoluntario=1       // Por voluntario
GET /api/inscripciones?idProyecto=1         // Por proyecto

#### Verifica:
curl http://localhost:8083/api/inscripciones
curl "http://localhost:8083/api/inscripciones?idVoluntario=1"
curl "http://localhost:8083/api/inscripciones?idProyecto=1"

| Endpoint | Método | Descripción | Parámetros |
|----------|--------|-------------|------------|
| `/api/reportes/dashboard` | GET | Dashboard general | - |
| `/api/reportes/voluntarios` | GET | Todos los voluntarios | - |
| `/api/reportes/voluntarios` | GET | Top N voluntarios | `?top=5` |
| `/api/reportes/voluntarios` | GET | Voluntario específico | `?id=1` |
| `/api/reportes/proyectos` | GET | Todos los proyectos | - |
| `/api/reportes/proyectos` | GET | Top N proyectos | `?top=5` |
| `/api/reportes/proyectos` | GET | Proyecto específico | `?id=1` |
| `/api/reportes/proyectos` | GET | Por categoría | `?porCategoria=true` |
| `/api/reportes/categorias` | GET | Reporte de categorías | - |
| `/api/reportes/inscripciones` | GET | Reporte general | - |
| `/api/reportes/inscripciones` | GET | Por estado | `?porEstado=true` |
| `/api/reportes/inscripciones` | GET | Estado específico | `?estado=Aprobada` |
| `/api/reportes/inscripciones` | GET | Tendencia | `?tendencia=true` |
| `/api/reportes/organizaciones` | GET | Todas las organizaciones | - |
| `/api/reportes/organizaciones` | GET | Top N organizaciones | `?top=5` |
| `/api/reportes/organizaciones` | GET | Organización específica | `?id=1` |
| `/api/reportes/organizaciones` | GET | Por tipo | `?porTipo=true` |
