package com.jbyanx.microreportes.servlet;

import com.google.gson.Gson;

import com.jbyanx.microreportes.service.ReporteCategoriaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/reportes/categorias")
public class ReporteCategoriaServlet extends HttpServlet {

    private final ReporteCategoriaService service = new ReporteCategoriaService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Obtener reporte de categorías con conteo de proyectos
            Map<String, Object> reporte = service.generarReporteCategorias();
            resp.getWriter().write(gson.toJson(reporte));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}