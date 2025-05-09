package com.sinfloo.controlador;

import com.sinfloo.modelo.ItemCarrito;
import com.sinfloo.modelo.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/carrito")
public class Carrito extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<Integer, ItemCarrito> carrito = (Map<Integer, ItemCarrito>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new HashMap<>();
        }

        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        Producto producto = new Producto(id, nombre, null, "", precio, 0);

        if (carrito.containsKey(id)) {
            carrito.get(id).setCantidad(cantidad);
        } else {
            carrito.put(id, new ItemCarrito(producto, cantidad));
        }

        session.setAttribute("carrito", carrito);
        response.sendRedirect("carrito.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("carrito.jsp").forward(request, response);
    }
}

