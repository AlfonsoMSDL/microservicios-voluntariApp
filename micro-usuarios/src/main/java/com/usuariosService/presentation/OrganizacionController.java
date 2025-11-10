package com.usuariosService.presentation;

import com.usuariosService.dto.GetOrganizacion;
import com.usuariosService.dto.GetTipoOrganizacion;
import com.usuariosService.mapper.JsonMapper;
import com.usuariosService.model.Organizacion;
import com.usuariosService.model.TipoOrganizacion;
import com.usuariosService.service.OrganizacionService;
import com.usuariosService.service.TipoOrganizacionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;


@WebServlet("/organizaciones")
public class OrganizacionController extends HttpServlet {
    private final OrganizacionService organizacionService = new OrganizacionService();
    private final JsonMapper<GetTipoOrganizacion> jsonMapperTipoOrg = new JsonMapper<>();
    private final JsonMapper<GetOrganizacion> jsonMapperGetOrg = new JsonMapper<>();
    private final JsonMapper<Organizacion> jsonMapperOrg = new JsonMapper<>();
    private final TipoOrganizacionService tipoOrganizacionService = new TipoOrganizacionService();
    private static final Logger LOGGER = Logger.getLogger(OrganizacionController.class);

    Logger log = Logger.getLogger(OrganizacionController.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("application/json");

        String accion = req.getParameter("action");

        if(accion == null) accion = "default";
        switch (accion){
            case "save":
                guardarOrganizacion(req,resp);
                break;

            case "update":
                actualizarOrganizacion(req,resp);
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
            case "getTipos":
                obtenerTiposOrganizacion(req,resp);
                break;
            case "getById":
                obtenerOrganizacionPorId(req,resp);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                break;
        }
    }



    private void guardarOrganizacion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nombre = req.getParameter("nombreOrganizacion");
        String nombreUsuario = req.getParameter("nombreUsuario");
        String correo = req.getParameter("emailOrganizacion");
        String clave = req.getParameter("clave");
        String telefono = req.getParameter("telefono");
        Long idTipo = Long.parseLong(req.getParameter("tipo"));


        Organizacion resultado = organizacionService.save(nombre,nombreUsuario,correo,clave,telefono,idTipo);

        if(resultado != null){

            log.info("Organizacion guardada correctamente\n");

            String resultadoJson = jsonMapperOrg.toJson(resultado);

            resp.getWriter().println(resultadoJson);
        }else{
            resp.getWriter().println("{\"error\": \"Acción no válida\"}");
        }
    }

    private void actualizarOrganizacion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getParameterMap();
        String nombre = req.getParameter("nombreOrganizacion");
        String nombreUsuario = req.getParameter("nombreUsuario");
        String correo = req.getParameter("correo");
        String clave = req.getParameter("clave");
        String telefono = req.getParameter("telefono");
        Long id_tipo = Long.parseLong( req.getParameter("tipo"));
        Long idOrganizacion = Long.parseLong(req.getParameter("id"));
        String descripcion = req.getParameter("descripcion");

        Organizacion actualizado = organizacionService.update(idOrganizacion,nombre,correo,telefono,clave, id_tipo,descripcion,nombreUsuario);

        if(actualizado != null){
            resp.getWriter().println("{\"mensaje\":\"Actualizado correctamente\"}");
            log.info("Actualizado correctamente");
            req.getSession().setAttribute("usuarioLogin",actualizado);
        }else{
            resp.getWriter().println("{\"mensaje\":\"Hubo un error actualizando\"}");
            log.info("Hubo un error actualizando");
        }
    }

    private void obtenerTiposOrganizacion(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.info("Iniciando obtenerTiposOrganizacion");
        String listaJson = jsonMapperTipoOrg.toJson(tipoOrganizacionService.findAll());
        try {
            resp.getWriter().println(listaJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void obtenerOrganizacionPorId(HttpServletRequest req, HttpServletResponse resp) {
        req.getParameterMap();
        Long idOrganizacion = Long.parseLong(req.getParameter("id"));
        GetOrganizacion organizacionDto = organizacionService.findById(idOrganizacion);

        String organizacionJson = jsonMapperGetOrg.toJson(organizacionDto);

        try {
            resp.getWriter().println(organizacionJson);
        } catch (IOException e) {}




    }


}
