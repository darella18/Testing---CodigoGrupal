package com.sinfloo.controlador;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        com.sinfloo.config.Conexion conexion = new com.sinfloo.config.Conexion();
        try (Connection conn = conexion.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{CALL registrar_cliente(?, ?, ?)}");
            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword); // Guarda el hash
            stmt.execute();
            response.sendRedirect("login.jsp");
        } catch (SQLIntegrityConstraintViolationException e) {
            request.setAttribute("error", "El email ya está registrado.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al registrar usuario.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }
}