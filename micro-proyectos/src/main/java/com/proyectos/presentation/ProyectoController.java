package com.proyectos.presentation;

import com.proyectos.dto.GetProyecto;
import com.proyectos.client.Cliente;
import com.proyectos.dto.GetCategoria;
import com.proyectos.mapper.JsonMapper;
import com.proyectos.model.Proyecto;
import com.proyectos.service.ProyectoService;
import com.proyectos.service.CategoriaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;


@WebServlet("/proyectos")
public class ProyectoController extends HttpServlet {
    private final ProyectoService proyectoService = new ProyectoService();
    private final CategoriaService categoriaService = new CategoriaService();

    private final JsonMapper<GetProyecto> jsonMapperProyecto = new JsonMapper();
    private final JsonMapper<GetCategoria> jsonMapperCategoria = new JsonMapper();
    private final Logger log = Logger.getLogger(Cliente.class.getName());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("application/json");
        String accion = req.getParameter("action");

        if(accion == null) accion = "default";

        switch (accion){
            case "save":
                guardarProyecto(req,resp);
                break;
            case "update":
                actualizarProyecto(req,resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String accion = req.getParameter("action");

        if(accion == null) accion = "default";
        switch (accion){
            case "getProyectosByOrganizacion":
                obtenerProyectosByOrganizacion(req,resp);
                break;
            case "getProyectos":
                obtenerTodosLosProyectos(req,resp);
                break;    
            case "getById":
                obtenerProyectoById(req,resp);
                break;
            case "getCategorias":
                obtenerCategoriasDeProyectos(req,resp);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                break;
        }
    }


    

    private void guardarProyecto(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        String ubicacion = req.getParameter("ubicacion");
        String requisitos = req.getParameter("requisitos");
        Date fechaInicio = Date.valueOf(req.getParameter("fechaInicio"));
        Date fechaFin = Date.valueOf(req.getParameter("fechaFin"));
        Integer voluntarios_requeridos = Integer.parseInt(req.getParameter("voluntariosRequeridos"));
        Long idCategoria = Long.parseLong(req.getParameter("idCategoria"));
        Long idOrganizacion = Long.parseLong(req.getParameter("idOrganizacion"));

        Proyecto resultado = proyectoService.save(nombre,descripcion,ubicacion,requisitos,fechaInicio,fechaFin, voluntarios_requeridos,idCategoria,idOrganizacion);
        if(resultado != null){
            resp.getWriter().println("{\"status\": \"success\"}");
        }else{
            resp.getWriter().println("{\"status\": \"error\"}");
        }
    }

    private void actualizarProyecto(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        String ubicacion = req.getParameter("ubicacion");
        String requisitos = req.getParameter("requisitos");
        Date fechaInicio = Date.valueOf(req.getParameter("fechaInicio"));
        Date fechaFin = Date.valueOf(req.getParameter("fechaFin"));
        Integer voluntarios_requeridos = Integer.parseInt(req.getParameter("voluntariosRequeridos"));
        Long idCategoria = Long.parseLong(req.getParameter("idCategoria"));
        Long idProyecto = Long.parseLong(req.getParameter("idProyecto"));

        Proyecto actualizado = proyectoService.update(idProyecto,nombre,descripcion,ubicacion,requisitos,fechaInicio,fechaFin,voluntarios_requeridos,idCategoria);

        if(actualizado != null){
            resp.getWriter().println("{\"mensaje\":\"Actualizado correctamente\"}");
        }else {
            resp.getWriter().println("{\"mensaje\":\"Hubo un error actualizando\"}");
        }
    }

    private void obtenerProyectosByOrganizacion(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.parseLong(req.getParameter("id"));
        List<GetProyecto> proyectos = proyectoService.findAllProyectosByOrganizacion(id);
        //log.info("Get Proyectos: " + proyectos);
        String json = jsonMapperProyecto.toJson(proyectos);

        try {
            resp.getWriter().println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void obtenerTodosLosProyectos(HttpServletRequest req, HttpServletResponse resp) {
        List<GetProyecto> proyectos = proyectoService.findAllProyectos();
        //log.info("Get Proyectos: " + proyectos);
        String json = jsonMapperProyecto.toJson(proyectos);

        try {
            resp.getWriter().println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void obtenerCategoriasDeProyectos(HttpServletRequest req, HttpServletResponse resp) {

        List<GetCategoria> categorias = categoriaService.findAll();

        String json = jsonMapperCategoria.toJson(categorias);

        try {
            resp.getWriter().println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void obtenerProyectoById(HttpServletRequest req, HttpServletResponse resp) {
        
        Long id = Long.parseLong(req.getParameter("id"));
        GetProyecto proyecto = proyectoService.findById(id);
        log.info("Get Proyectos: " + proyecto);
        String json = jsonMapperProyecto.toJson(proyecto);
        try {
            resp.getWriter().println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

