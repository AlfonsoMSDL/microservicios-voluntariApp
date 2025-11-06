package com.usuariosService.corsConfig;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*") // Aplica a todas las rutas
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Permitir origen específico (más seguro)
        //httpResponse.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");

        // O permitir cualquier origen (menos seguro, solo para desarrollo)
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");

        // Métodos HTTP permitidos
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        // Headers permitidos
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");

        // Permitir credenciales (cookies, auth)
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // Tiempo de caché para preflight (1 hora)
        httpResponse.setHeader("Access-Control-Max-Age", "3600");


        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }

}
