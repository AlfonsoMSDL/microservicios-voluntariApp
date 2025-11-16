package com.jbyanx.microreportes.servlet;

import com.google.gson.Gson;
import com.jbyanx.microreportes.dto.ReporteVoluntarioDTO;
import com.jbyanx.microreportes.service.ReporteVoluntarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/reportes/voluntarios")
public class ReporteVoluntarioServlet extends HttpServlet {

    private final ReporteVoluntarioService service = new ReporteVoluntarioService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String idParam = req.getParameter("id");
            String topParam = req.getParameter("top");

            if (idParam != null) {
                // Reporte de un voluntario específico
                Long id = Long.parseLong(idParam);
                ReporteVoluntarioDTO reporte = service.generarReporteVoluntario(id);
                resp.getWriter().write(gson.toJson(reporte));

            } else if (topParam != null) {
                // Top N voluntarios más activos
                int limite = Integer.parseInt(topParam);
                List<ReporteVoluntarioDTO> reporte = service.generarTopVoluntarios(limite);
                resp.getWriter().write(gson.toJson(reporte));

            } else {
                // Reporte de todos los voluntarios
                List<ReporteVoluntarioDTO> reporte = service.generarReporteVoluntarios();
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