package com.usuariosService.presentation;

import com.usuariosService.dto.GetOrganizacion;
import com.usuariosService.dto.GetVoluntario;
import com.usuariosService.mapper.JsonMapper;
import com.usuariosService.model.Voluntario;
import com.usuariosService.service.VoluntarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

@WebServlet("/voluntarios")
public class VoluntarioController extends HttpServlet {
    private final VoluntarioService voluntarioService = new VoluntarioService();
    Logger log = Logger.getLogger(VoluntarioController.class);
    private JsonMapper<GetVoluntario> jsonMapperGetVol = new JsonMapper();
    private JsonMapper<Voluntario> jsonMapperVol = new JsonMapper();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        String accion = req.getParameter("action");

        if(accion == null) accion = "default";
        switch (accion) {
            case "save":

                guardarVoluntario(req,resp);

                break;

            case "update":
                actualizarVoluntario(req,resp);
                break;
            case "delete":
                eliminarVoluntario(req,resp);
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
        switch (accion) {
            case "getById":
                obtenerVoluntarioPorId(req,resp);
                break;
            case "getAllVoluntarios":
                obtenerVoluntarios(req,resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                break;

        }
    }

    

    private void guardarVoluntario(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String correo = req.getParameter("correo");
        String clave = req.getParameter("clave");
        String telefono = req.getParameter("telefono");
        String nombreUsuario = req.getParameter("nombreUsuario");

        Voluntario resultado = voluntarioService.save(nombre, apellido,nombreUsuario, correo, clave,telefono);

        if(resultado != null){ // Si se guardo
            log.info(resultado);
            log.info("Voluntario guardado correctamente\n");

            String resultadoJson = jsonMapperVol.toJson(resultado);

            resp.getWriter().println(resultadoJson);
        }else{
            resp.getWriter().println("{\"error\": \"Acción no válida\"}");
            log.info("No se pudo guardar el voluntario\n");
        }
        
    }

    private void actualizarVoluntario(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong( req.getParameter("id"));
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String correo = req.getParameter("correo");
        String clave = req.getParameter("clave");
        String telefono = req.getParameter("telefono");
        String nombreUsuario = req.getParameter("nombreUsuario");
        String hablidades = req.getParameter("habilidades");
        String experiencia = req.getParameter("experiencia");
        String disponibilidad = req.getParameter("disponibilidad");
        String areas_interes = req.getParameter("areas_interes");

        Voluntario actualizado = voluntarioService.update(
                id,
                nombre,
                apellido,
                correo,
                telefono,
                clave,
                hablidades,
                experiencia,
                disponibilidad,
                areas_interes,
                nombreUsuario);

        if(actualizado != null){
            //Si se actualizo
            resp.getWriter().println("{\"mensaje\":\"Actualizado correctamente\"}");
            log.info("Actualizado correctamente");
            req.getSession().setAttribute("usuarioLogin",actualizado);
        }else{
            resp.getWriter().println("{\"mensaje\":\"No se pudo actualizar\"}");
            log.info("No se pudo actualizar");
        }


    }

    private void obtenerVoluntarioPorId(HttpServletRequest req, HttpServletResponse resp) {
        log.info(req.getParameter("id"));
        Long id = Long.valueOf(req.getParameter("id"));
        GetVoluntario voluntarioDto = voluntarioService.findById(id);

        String voluntarioJson = jsonMapperGetVol.toJson(voluntarioDto);

        try {
            resp.getWriter().println(voluntarioJson);
        } catch (IOException e) {}




    }

    private void obtenerVoluntarios(HttpServletRequest req, HttpServletResponse resp) {

        String voluntariosJson = jsonMapperGetVol.toJson(voluntarioService.findAllVoluntarios());
        try {
            resp.getWriter().println(voluntariosJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void eliminarVoluntario(HttpServletRequest req, HttpServletResponse resp) {
        req.getParameterMap();
        Long idVoluntario = Long.parseLong(req.getParameter("id"));
        boolean eliminado = voluntarioService.delete(idVoluntario);

        try {
            if(eliminado){
                resp.getWriter().println("{\"status\":\"success\"}");
                log.info("Eliminado correctamente");
            }else{
                resp.getWriter().println("{\"status\":\"error\"}");
                log.info("Hubo un error eliminando");
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
