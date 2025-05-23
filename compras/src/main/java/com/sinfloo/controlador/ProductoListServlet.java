package com.sinfloo.controlador;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/productos")
public class ProductoListServlet extends HttpServlet {
    public static class Producto {
        public int id;
        public String nombre;
        public String descripcion;
        public String urlImagen;
        public double precio;
        public int stock;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Producto> productos = new ArrayList<>();
        com.sinfloo.config.Conexion conexion = new com.sinfloo.config.Conexion();
        try (Connection conn = conexion.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{CALL listar_productos_activos()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.id = rs.getInt("id_producto");
                p.nombre = rs.getString("nombre");
                p.descripcion = rs.getString("descripcion");
                p.precio = rs.getDouble("precio");
                p.stock = rs.getInt("stock");
                p.urlImagen = rs.getString("url_imagen");
                productos.add(p);
            }
        } catch (Exception e) {
            throw new ServletException("Error al consultar productos", e);
        }
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }
}