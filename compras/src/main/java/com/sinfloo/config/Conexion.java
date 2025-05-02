/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sinfloo.config;
/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Richard
 */
/*public class Conexion{
    public static Connection getConnection(){
        Connection cn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdcompras", "root", "");
        } catch (Exception e) {
            System.out.println(String.valueOf(e));
        }
        return cn;
    }
}*/

/*public class Conexion {

    Connection con;

    public Connection getConnection() {
        try {
            String myBD = "jdbc:mysql://localhost:3306/bdcompras";
            con = DriverManager.getConnection(myBD, "root", "");
            return con;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

}*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Connection con;

    public Connection getConnection() {
        try {
            String myBD = "jdbc:mysql://localhost:3306/bdcompras";
            con = DriverManager.getConnection(myBD, "root", "");
            if (con != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            }
            return con;
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

    @Override
    protected void finalize() throws Throwable {
        try {
            closeConnection();
        } finally {
            super.finalize();
        }
    }
}