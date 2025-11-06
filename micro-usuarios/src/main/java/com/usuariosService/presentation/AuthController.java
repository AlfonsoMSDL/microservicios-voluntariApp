package com.usuariosService.presentation;

import com.usuariosService.dto.GetUsuario;
import com.usuariosService.mapper.JsonMapper;
import com.usuariosService.model.Usuario;
import com.usuariosService.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

@WebServlet("/auth")
public class AuthController extends HttpServlet {
    private final AuthService authService = new AuthService();
    private static final Logger log = Logger.getLogger(AuthController.class);
    private JsonMapper<GetUsuario> jsonMapper = new JsonMapper();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Iniciando el proceso de post");
        resp.setContentType("application/json; charset=UTF-8");

        String correo = req.getParameter("correo");
        String clave = req.getParameter("clave");

        GetUsuario usuarioLogin = authService.Login(correo, clave);

        if (usuarioLogin != null) {
            log.info("Usuario logueado: " + usuarioLogin.correo() + " | Rol: " + usuarioLogin.rol().nombre()+" | Nombre usuario: "+usuarioLogin.nombreUsuario());
            HttpSession session = req.getSession();
            session.setAttribute("usuarioLogin", usuarioLogin);
            String jsonResponse = jsonMapper.toJson(usuarioLogin);

            resp.getWriter().println(jsonResponse);



        } else {
            log.warn("Intento de login con credenciales inválidas: " + correo);
            resp.getWriter().println("{\"error\": \"Credenciales invalidas\"}");
        }
    }


}
