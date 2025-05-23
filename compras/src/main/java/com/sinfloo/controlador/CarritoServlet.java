package com.sinfloo.controlador;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/carrito")
public class CarritoServlet extends HttpServlet {
    public static class ItemCarrito {
        public int idProducto;
        public String nombre;
        public String descripcion;
        public String urlImagen;
        public int cantidad;
        public double precioUnitario;
        public double subtotal;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer idCliente = (Integer) session.getAttribute("id_cliente");
        if (idCliente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<ItemCarrito> items = new ArrayList<>();
        double total = 0.0;

        com.sinfloo.config.Conexion conexion = new com.sinfloo.config.Conexion();
        try (Connection conn = conexion.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{CALL consultar_carrito(?)}");
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ItemCarrito item = new ItemCarrito();
                item.idProducto = rs.getInt("id_producto");
                item.nombre = rs.getString("nombre");
                item.descripcion = rs.getString("descripcion");
                item.urlImagen = rs.getString("url_imagen");
                item.cantidad = rs.getInt("cantidad");
                item.precioUnitario = rs.getDouble("precio_unitario");
                item.subtotal = rs.getDouble("subtotal");
                total += item.subtotal;
                items.add(item);
            }
        } catch (Exception e) {
            throw new ServletException("Error al consultar el carrito", e);
        }
        request.setAttribute("items", items);
        request.setAttribute("total", total);
        request.getRequestDispatcher("carrito.jsp").forward(request, response);
    }
}