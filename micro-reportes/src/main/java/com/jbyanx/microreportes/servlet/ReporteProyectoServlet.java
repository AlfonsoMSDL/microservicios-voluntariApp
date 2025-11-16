package com.jbyanx.microreportes.servlet;

import com.google.gson.Gson;

import com.jbyanx.microreportes.dto.ReporteProyectoDTO;
import com.jbyanx.microreportes.service.ReporteProyectoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/reportes/proyectos")
public class ReporteProyectoServlet extends HttpServlet {

    private final ReporteProyectoService service = new ReporteProyectoService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String idParam = req.getParameter("id");
            String topParam = req.getParameter("top");
            String porCategoriaParam = req.getParameter("porCategoria");

            if (idParam != null) {
                // Reporte de un proyecto específico
                Long id = Long.parseLong(idParam);
                ReporteProyectoDTO reporte = service.generarReporteProyecto(id);
                resp.getWriter().write(gson.toJson(reporte));

            } else if (topParam != null) {
                // Top N proyectos más populares
                int limite = Integer.parseInt(topParam);
                List<ReporteProyectoDTO> reporte = service.generarProyectosMasPopulares(limite);
                resp.getWriter().write(gson.toJson(reporte));

            } else if ("true".equalsIgnoreCase(porCategoriaParam)) {
                // Proyectos agrupados por categoría
                Map<String, List<ReporteProyectoDTO>> reporte = service.generarReportePorCategoria();
                resp.getWriter().write(gson.toJson(reporte));

            } else {
                // Reporte de todos los proyectos
                List<ReporteProyectoDTO> reporte = service.generarReporteProyectos();
                resp.getWriter().write(gson.toJson(reporte));
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Parámetro inválido\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}