package com.sinfloo.controlador;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/AgregarCarritoServlet")
public class AgregarCarritoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer idCliente = (Integer) session.getAttribute("id_cliente");
        if (idCliente == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int idProducto = Integer.parseInt(request.getParameter("id_producto"));
        int cantidad = 1; // Siempre agrega 1 por defecto

        com.sinfloo.config.Conexion conexion = new com.sinfloo.config.Conexion();
        try (Connection conn = conexion.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{CALL agregar_producto_a_carrito(?, ?, ?)}");
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidad);
            stmt.execute();
        } catch (Exception e) {
            throw new ServletException("Error al agregar al carrito", e);
        }
        response.sendRedirect("productos");
    }
}