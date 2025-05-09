package com.controller;

import com.utils.ErrorUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

public class CarritoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            // gestionar el carrito
        } catch (Exception e) {
            String mensaje = ErrorUtil.getUserFriendlyMessage("INVALID_INPUT");
            request.setAttribute("errorMessage", mensaje);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            // la solicitud POST
        } catch (Exception e) {
            String mensaje = ErrorUtil.getUserFriendlyMessage("INVALID_INPUT");
            request.setAttribute("errorMessage", mensaje);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
