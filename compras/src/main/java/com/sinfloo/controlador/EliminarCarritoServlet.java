package com.sinfloo.controlador;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet("/EliminarCarritoServlet")
public class EliminarCarritoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer idCliente = (Integer) session.getAttribute("id_cliente");
        if (idCliente == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int idProducto = Integer.parseInt(request.getParameter("id_producto"));

        com.sinfloo.config.Conexion conexion = new com.sinfloo.config.Conexion();
        try (Connection conn = conexion.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{CALL eliminar_producto_del_carrito(?, ?)}");
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idProducto);
            stmt.execute();
        } catch (Exception e) {
            throw new ServletException("Error al eliminar producto del carrito", e);
        }
        response.sendRedirect("carrito");
    }
}