package com.jbyanx.microreportes.servlet;

import com.google.gson.Gson;

import com.jbyanx.microreportes.dto.DashboardDTO;
import com.jbyanx.microreportes.service.DashboardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/reportes/dashboard")
public class DashboardServlet extends HttpServlet {

    private final DashboardService service = new DashboardService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            DashboardDTO dashboard = service.generarDashboard();
            resp.getWriter().write(gson.toJson(dashboard));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}