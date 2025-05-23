package com.sinfloo.controlador;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ComprarServlet")
public class ComprarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer idCliente = (Integer) session.getAttribute("id_cliente");
        if (idCliente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        boolean error = false;
        int idProductoError = 0;
        int stockActual = 0;

        com.sinfloo.config.Conexion conexion = new com.sinfloo.config.Conexion();
        try (Connection conn = conexion.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{CALL confirmar_compra(?, ?, ?, ?)}");
            stmt.setInt(1, idCliente);
            stmt.registerOutParameter(2, Types.BOOLEAN);
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.execute();

            error = stmt.getBoolean(2);
            idProductoError = stmt.getInt(3);
            stockActual = stmt.getInt(4);

            if (error) {
                // Busca el nombre del producto con error
                String nombreProducto = "";
                PreparedStatement ps = conn.prepareStatement("SELECT nombre FROM producto WHERE id_producto = ?");
                ps.setInt(1, idProductoError);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    nombreProducto = rs.getString("nombre");
                }
                request.setAttribute("nombreProducto", nombreProducto);
                request.setAttribute("stockActual", stockActual);
                RequestDispatcher rd = request.getRequestDispatcher("compra_error.jsp");
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("compra_exitosa.jsp");
                rd.forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error al confirmar la compra", e);
        }
    }
}