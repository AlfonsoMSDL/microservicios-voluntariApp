package com.jbyanx.microreportes.servlet;

import com.google.gson.Gson;

import com.jbyanx.microreportes.service.ReporteInscripcionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/reportes/inscripciones")
public class ReporteInscripcionServlet extends HttpServlet {

    private final ReporteInscripcionService service = new ReporteInscripcionService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String porEstadoParam = req.getParameter("porEstado");
            String estadoParam = req.getParameter("estado");
            String tendenciaParam = req.getParameter("tendencia");

            if ("true".equalsIgnoreCase(porEstadoParam)) {
                // Inscripciones agrupadas por estado
                Map<String, Object> reporte = service.generarReportePorEstado();
                resp.getWriter().write(gson.toJson(reporte));

            } else if (estadoParam != null) {
                // Inscripciones filtradas por un estado específico
                Map<String, Object> reporte = service.generarReportePorEstadoEspecifico(estadoParam);
                resp.getWriter().write(gson.toJson(reporte));

            } else if ("true".equalsIgnoreCase(tendenciaParam)) {
                // Tendencia de inscripciones (últimos 30 días)
                Map<String, Object> reporte = service.generarTendenciaInscripciones();
                resp.getWriter().write(gson.toJson(reporte));

            } else {
                // Reporte general de inscripciones
                Map<String, Object> reporte = service.generarReporteGeneral();
                resp.getWriter().write(gson.toJson(reporte));
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}