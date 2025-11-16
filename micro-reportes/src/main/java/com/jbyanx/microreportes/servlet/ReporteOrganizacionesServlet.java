package com.jbyanx.microreportes.servlet;

import com.google.gson.Gson;

import com.jbyanx.microreportes.dto.ReporteOrganizacionDTO;
import com.jbyanx.microreportes.service.ReporteOrganizacionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/reportes/organizaciones")
public class ReporteOrganizacionesServlet extends HttpServlet {

    private final ReporteOrganizacionService service = new ReporteOrganizacionService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String idParam = req.getParameter("id");
            String topParam = req.getParameter("top");
            String porTipoParam = req.getParameter("porTipo");

            if (idParam != null) {
                // Reporte de una organización específica
                Long id = Long.parseLong(idParam);
                ReporteOrganizacionDTO reporte = service.generarReporteOrganizacion(id);
                resp.getWriter().write(gson.toJson(reporte));

            } else if (topParam != null) {
                // Top N organizaciones más activas
                int limite = Integer.parseInt(topParam);
                List<ReporteOrganizacionDTO> reporte = service.generarTopOrganizaciones(limite);
                resp.getWriter().write(gson.toJson(reporte));

            } else if ("true".equalsIgnoreCase(porTipoParam)) {
                // Organizaciones agrupadas por tipo
                Map<String, Object> reporte = service.generarReportePorTipo();
                resp.getWriter().write(gson.toJson(reporte));

            } else {
                // Reporte de todas las organizaciones
                List<ReporteOrganizacionDTO> reporte = service.generarReporteOrganizaciones();
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