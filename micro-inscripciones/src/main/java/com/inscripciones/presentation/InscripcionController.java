package com.inscripciones.presentation;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
            case "updateEstado":
                actualizarEstadoInscripcion(req, resp);
                break;
            case "delete":
                eliminarInscripcion(req, resp);
                break;  
            case "deleteByIdVoluntario":
                eliminarInscripcionesPorVoluntario(req, resp);
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

        if(accion == null) accion = "default";

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

    private void guardarInscripcion(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String motivacion = req.getParameter("motivacion");
        Long idProyecto = Long.parseLong(req.getParameter("idProyecto"));
        Long idvoluntario = Long.parseLong(req.getParameter("idVoluntario"));
        Date fechaInscripcion = Date.valueOf(LocalDate.now());
        Long idEstadoInscripcion = 1L;

        Inscripcion resultado = inscripcionService.save(motivacion, idProyecto, idvoluntario, fechaInscripcion,idEstadoInscripcion);
        if(resultado != null){
            resp.getWriter().println("{\"status\": \"success\"}");
        }else{
            resp.getWriter().println("{\"status\": \"error\"}");
        }
    }

    private void actualizarInscripcion(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String motivacion = req.getParameter("motivacion");
        Long idInscripcion = Long.parseLong(req.getParameter("idInscripcion"));

        Inscripcion actualizado = inscripcionService.update(idInscripcion, motivacion);

        if(actualizado != null){
            resp.getWriter().println("{\"mensaje\": \"Actualizado correctamente\"}");
        }else {
            resp.getWriter().println("{\"mensaje\": \"Actualizado correctamente\"}");
        }
    }

    private void actualizarEstadoInscripcion(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        Long idInscripcion = Long.parseLong(req.getParameter("idInscripcion"));
        Long idEstadoNuevo = Long.parseLong(req.getParameter("idEstado"));

        Inscripcion actualizado = inscripcionService.updateEstado(idInscripcion, idEstadoNuevo);

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

    private void eliminarInscripcion(HttpServletRequest req, HttpServletResponse resp) {
        
        Long idInscripcion = Long.parseLong(req.getParameter("id"));
        boolean eliminada = inscripcionService.delete(idInscripcion);

        try {
            if(eliminada){
                resp.getWriter().println("{\"status\": \"success\"}");
            }else{
                resp.getWriter().println("{\"status\": \"error\"}");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    private void eliminarInscripcionesPorVoluntario(HttpServletRequest req, HttpServletResponse resp) {
        req.getParameterMap();
        Long idVoluntario = Long.valueOf(req.getParameter("id"));
        String status;

        try {
            //la primera excepcion es la de la base de datos, en donde si hubo un error eliminando
            // le mando un status de error al cliente
            inscripcionService.deleteByIdVoluntario(idVoluntario);
            status = "{\"status\":\"success\"}";
        }catch (SQLException e) {
            status = "{\"status\":\"error\"}";
        }

        //la segunda excepcion es para mandar el status al cliente
        try {
            resp.getWriter().println(status);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
