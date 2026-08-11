/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Vuelo;
import Modelo.ConexionBD;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author julic
 */

public class ControladorVuelos {

    // Método que trae todos los vuelos disponibles desde la base de datos
    public ArrayList<Vuelo> obtenerTodos() {
        ArrayList<Vuelo> resultados = new ArrayList<>();
        Connection con = ConexionBD.conectar();
        String sql = "SELECT * FROM vuelos";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Vuelo v = new Vuelo(
                    rs.getInt("id"),
                    rs.getString("origen"),
                    rs.getString("destino"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("aerolinea"),
                    rs.getString("duracion"),
                    rs.getDouble("precio"),
                    rs.getString("estado") // ✅ campo agregado
                );
                resultados.add(v);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error al obtener vuelos: " + e.getMessage());
        }
        return resultados;
    }

    // Método que busca vuelos por origen, destino y fecha
    public ArrayList<Vuelo> buscar(String origen, String destino, String fecha) {
        ArrayList<Vuelo> resultados = new ArrayList<>();
        Connection con = ConexionBD.conectar();
        String sql = "SELECT * FROM vuelos WHERE origen = ? AND destino = ? AND fecha = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, origen);
            stmt.setString(2, destino);
            stmt.setString(3, fecha);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Vuelo v = new Vuelo(
                    rs.getInt("id"),
                    rs.getString("origen"),
                    rs.getString("destino"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("aerolinea"),
                    rs.getString("duracion"),
                    rs.getDouble("precio"),
                    rs.getString("estado") // ✅ campo agregado
                );
                resultados.add(v);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error al buscar vuelos: " + e.getMessage());
        }
        return resultados;
    }
}
