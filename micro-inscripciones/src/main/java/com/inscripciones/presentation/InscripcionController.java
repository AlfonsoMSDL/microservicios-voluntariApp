package com.inscripciones.presentation;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;

import com.inscripciones.dto.GetEstadoInscripcion;
import com.inscripciones.dto.GetInscripcion;
import com.inscripciones.mapper.JsonMapper;
import com.inscripciones.model.Inscripcion;
import com.inscripciones.service.Cliente;
import com.inscripciones.service.EstadoInscripcionService;
import com.inscripciones.service.InscripcionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/inscripciones")
public class InscripcionController extends HttpServlet {
    private final InscripcionService inscripcionService = new InscripcionService();
    private final EstadoInscripcionService estadoInscripcionService = new EstadoInscripcionService();

    private final JsonMapper<GetInscripcion> jsonMapperInscripcion = new JsonMapper<>();
    private final JsonMapper<GetEstadoInscripcion> jsonMapperEstadoInscripcion = new JsonMapper<>();
    private final Logger log = Logger.getLogger(Cliente.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("application/json");
        String accion = req.getParameter("action");

        if(accion == null) accion = "default";

        switch (accion) {
            case "save":
                guardarInscripcion(req,resp);
                break;
            case "update":
                actualizarInscripcion(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("application/json");

        String accion = req.getParameter("action");

        if(accion == null){
            switch (accion) {
                case "getInscripcionesByProyecto":
                    obtenerInscripcionesByProyecto(req, resp);
                    break;
                case "getInscripcionesByVoluntario":
                    obtenerInscripcionesByVoluntario(req, resp);
                    break;
                case "getById":
                    obtenerInscripcionById(req, resp);
                    break;
                case "getEstadosInscripcion":
                    obtenerEstadosInscripcionDeInscripciones(req, resp);
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        }
    }

    private void guardarInscripcion(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String motivacion = req.getParameter("nombre");
        Date fechaInscripcion = Date.valueOf(req.getParameter("fechaInscripcion"));
        Long idEstadoInscripcion = Long.parseLong(req.getParameter("idEstadoInscripcion"));
        Long idProyecto = Long.parseLong(req.getParameter("idProyecto"));
        Long idvoluntario = Long.parseLong(req.getParameter("idVoluntario"));

        Inscripcion resultado = inscripcionService.save(motivacion, fechaInscripcion, idEstadoInscripcion, idProyecto, idvoluntario);
        if(resultado != null){
            resp.getWriter().println("{\"status\": \"succes\"}");
        }else{
            resp.getWriter().println("{\"status\": \"error\"}");
        }
    }

    private void actualizarInscripcion(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.getParameterMap();
        String motivacion = req.getParameter("nombre");
        Date fechaInscripcion = Date.valueOf(req.getParameter("fechaInscripcion"));
        Long idEstadoInscripcion = Long.parseLong(req.getParameter("idEstadoInscripcion"));
        Long idInscripcion = Long.parseLong(req.getParameter("idInscripcion"));

        Inscripcion actualizado = inscripcionService.update(idInscripcion, motivacion, fechaInscripcion, idEstadoInscripcion);

        if(actualizado != null){
            resp.getWriter().println("{\"mensaje\": \"Actualizado correctamente\"}");
        }else {
            resp.getWriter().println("{\"mensaje\": \"Actualizado correctamente\"}");
        }
    }

    private void obtenerInscripcionesByVoluntario(HttpServletRequest req, HttpServletResponse resp){
        req.getParameterMap();
        Long id = Long.parseLong(req.getParameter("idVoluntario"));
        List<GetInscripcion> inscripciones = inscripcionService.findAllInscripcionesByVoluntario(id);

        String json = jsonMapperInscripcion.toJson(inscripciones);

        try{
            resp.getWriter().println(json);
        }catch(IOException e){
            throw new RuntimeException(e);
        }

    }

    private void obtenerInscripcionesByProyecto(HttpServletRequest req, HttpServletResponse resp){
        req.getParameterMap();
        Long id = Long.parseLong(req.getParameter("idProyecto"));
        List<GetInscripcion> inscripciones = inscripcionService.findAllInscripcionesByProyecto(id);

        String json = jsonMapperInscripcion.toJson(inscripciones);

        try{
            resp.getWriter().println(json);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private void obtenerEstadosInscripcionDeInscripciones(HttpServletRequest req, HttpServletResponse resp){
        List<GetEstadoInscripcion> estadosInscripcion = estadoInscripcionService.findAll();
        String json = jsonMapperEstadoInscripcion.toJson(estadosInscripcion);

        try{
            resp.getWriter().println(json);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private void obtenerInscripcionById(HttpServletRequest req, HttpServletResponse resp){
        req.getParameterMap();
        Long id = Long.parseLong(req.getParameter("idInscripcion"));
        GetInscripcion inscripcion = inscripcionService.findById(id);
        String json = jsonMapperInscripcion.toJson(inscripcion);

        try{
            resp.getWriter().println(json);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
