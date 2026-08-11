/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julic
 */
public class VueloDAO {

    public static List<Vuelo> obtenerTodos() {
        List<Vuelo> lista = new ArrayList<>();
        try (Connection con = ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM vuelos");
            ResultSet rs = ps.executeQuery();
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
                    rs.getString("estado")
                );
                lista.add(v);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public Vuelo buscarPorId(int id) {
        try (Connection con = ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM vuelos WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Vuelo(
                    rs.getInt("id"),
                    rs.getString("origen"),
                    rs.getString("destino"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("aerolinea"),
                    rs.getString("duracion"),
                    rs.getDouble("precio"),
                    rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vuelo> obtenerVuelos() {
        return obtenerTodos();
    }

    public void actualizarEstadoVuelo(int vueloId, String nuevoEstado) {
        try (Connection con = ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement("UPDATE vuelos SET estado = ? WHERE id = ?");
            ps.setString(1, nuevoEstado);
            ps.setInt(2, vueloId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean guardarCompra(int vueloId, int pasajeroId, String metodo, double total) {
        try (Connection con = ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO compras (vuelo_id, pasajero_id, metodo_pago, total) VALUES (?, ?, ?, ?)");
            ps.setInt(1, vueloId);
            ps.setInt(2, pasajeroId);
            ps.setString(3, metodo);
            ps.setDouble(4, total);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int guardarPasajero(Pasajero p) {
        try (Connection con = ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO pasajeros (nombre, fecha_nacimiento, dni, pasaporte) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getFechaNacimiento());
            ps.setString(3, p.getDni());
            ps.setString(4, p.getPasaporte());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // ID generado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
