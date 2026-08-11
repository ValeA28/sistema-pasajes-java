/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author julic
 */
public class ConexionBD {

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sistemapasajes", // ✅ nombre correcto
                "root", "" // Usuario y contraseña de XAMPP
            );
            System.out.println("✅ Conexión exitosa a 'sistemapasajes'");
            return conn;
        } catch (Exception e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            return null;
        }
    }
}


