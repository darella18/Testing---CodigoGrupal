package com.sinfloo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Connection con;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String myBD = "jdbc:mysql://localhost:3306/bdcompras";
            
            con = DriverManager.getConnection(myBD, "root", "root");
            if (con != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            }
            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver de MySQL: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace(); // Imprime el stack trace para más detalles
        }
        return null;
    }

    public void closeConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}