/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionBD;

import java.sql.*;

/**
 *
 * @author julic
 */
public class ControladorReservas {

    public static boolean guardarReserva(String aerolinea, String origen, String destino, String fecha,
                                         String hora, String duracion, int cantidad, double total) {

        String sql = "INSERT INTO reservas (aerolinea, origen, destino, fecha, hora, duracion, cantidad, total) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, aerolinea);
            ps.setString(2, origen);
            ps.setString(3, destino);
            ps.setDate(4, java.sql.Date.valueOf(fecha));
            ps.setTime(5, java.sql.Time.valueOf(hora));
            ps.setString(6, duracion);
            ps.setInt(7, cantidad);
            ps.setDouble(8, total);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}