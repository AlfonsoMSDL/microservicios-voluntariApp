package com.participaciones.presentation;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.participaciones.dto.GetParticipacion;
import com.participaciones.mapper.JsonMapper;
import com.participaciones.service.ParticipacionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/participaciones")
public class ParticipacionController extends HttpServlet {

    private final ParticipacionService participacionService = new ParticipacionService();
    private final JsonMapper<GetParticipacion> mapperGetParticipacion = new JsonMapper<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        String accion = req.getParameter("action");

        if(accion == null) accion = "default";
        switch (accion){
            case "getParticipacionesByProyecto":
                obtenerParticipacionesByProyecto(req,resp);
                break;
            case "getParticipacionesByVoluntario":
                obtenerParticipacionesByVoluntario(req,resp);
                break;    
            case "getById":
                obtenerParticipacionById(req,resp);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                break;
        }
    }







    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        String accion = req.getParameter("action");

        if(accion == null) accion = "default";

        switch (accion){
            case "save":
                guardarParticipacion(req, resp);
                break;
            case "update":
                actualizarParticipacion(req,resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                break;
        }
    }

    private void obtenerParticipacionesByProyecto(HttpServletRequest req, HttpServletResponse resp) {
        req.getParameterMap();

        Long idProyecto =Long.valueOf( req.getParameter("idProyecto"));

        List<GetParticipacion> participaciones = participacionService.findAllParticipacionesByIdProyecto(idProyecto);

        String json = mapperGetParticipacion.toJson(participaciones);

        try {
            resp.getWriter().println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void obtenerParticipacionesByVoluntario(HttpServletRequest req, HttpServletResponse resp) {
        req.getParameterMap();

        Long idVoluntario =Long.valueOf( req.getParameter("idVoluntario"));

        List<GetParticipacion> participaciones = participacionService.findAllParticipacionesByIdVoluntario(idVoluntario);

        String json = mapperGetParticipacion.toJson(participaciones);

        try {
            resp.getWriter().println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void guardarParticipacion(HttpServletRequest req, HttpServletResponse resp) {
        //Datos a guardar
        Long idVoluntario = Long.valueOf(req.getParameter("idVoluntario"));
        Long idProyecto = Long.valueOf(req.getParameter("idProyecto"));
        Date fechaInicio = Date.valueOf(LocalDate.now());
        int horasRealizadas = 0;

        GetParticipacion participacion = participacionService.save(idVoluntario, idProyecto, fechaInicio, horasRealizadas);

        String json = mapperGetParticipacion.toJson(participacion);
        try {
            resp.getWriter().println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void actualizarParticipacion(HttpServletRequest req, HttpServletResponse resp) {

    }

    

    private void obtenerParticipacionById(HttpServletRequest req, HttpServletResponse resp) {

    }

    
    

}
