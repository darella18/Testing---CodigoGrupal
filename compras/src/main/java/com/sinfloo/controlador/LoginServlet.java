package com.sinfloo.controlador;

import java.io.IOException;
import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        com.sinfloo.config.Conexion conexion = new com.sinfloo.config.Conexion();
        try (Connection conn = conexion.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{CALL iniciar_sesion(?)}");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("contraseña");
                int idCliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                if (hashedPassword != null && BCrypt.checkpw(password, hashedPassword)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", nombre);
                    session.setAttribute("id_cliente", idCliente);
                    response.sendRedirect("index.jsp");
                } else {
                    request.setAttribute("error", "Email o contraseña incorrectos.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Email o contraseña incorrectos.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("ERROR LOGIN: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al conectar con la base de datos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}