package controlador;

import modelo.ItemCarrito;
import modelo.Producto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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

        Producto producto = new Producto(id, nombre, precio);

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

