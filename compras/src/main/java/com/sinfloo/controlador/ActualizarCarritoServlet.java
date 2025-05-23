package com.sinfloo.controlador;

import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ActualizarCarritoServlet")
public class ActualizarCarritoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer idCliente = (Integer) session.getAttribute("id_cliente");
        if (idCliente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        com.sinfloo.config.Conexion conexion = new com.sinfloo.config.Conexion();
        try (Connection conn = conexion.getConnection()) {
            // Obtener el id_carrito activo del usuario
            int idCarrito = -1;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT id_carrito FROM carrito WHERE id_cliente = ? AND estado = 'activo'")) {
                ps.setInt(1, idCliente);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    idCarrito = rs.getInt("id_carrito");
                }
            }
            if (idCarrito == -1) {
                response.sendRedirect("carrito");
                return;
            }

            // Recorrer los parámetros para actualizar cantidades
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String param = paramNames.nextElement();
                if (param.startsWith("cantidades_")) {
                    int idProducto = Integer.parseInt(param.substring("cantidades_".length()));
                    int cantidad = Integer.parseInt(request.getParameter(param));
                    if (cantidad > 0) {
                        try (CallableStatement cs = conn.prepareCall("{CALL actualizar_cantidad_carrito(?, ?, ?)}")) {
                            cs.setInt(1, idCliente);
                            cs.setInt(2, idProducto);
                            cs.setInt(3, cantidad);
                            cs.execute();
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error al actualizar el carrito", e);
        }
        response.sendRedirect("carrito");
    }
}