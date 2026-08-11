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
public class ReservaDAO {

    private Connection con;

    public ReservaDAO() {
        con = ConexionBD.conectar();
    }

    public void guardarReserva(int vueloId, int pasajeroId, String asiento) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO reservas (vuelo_id, pasajero_id, asiento, estado, metodo_pago, total_pagado) VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setInt(1, vueloId);
            ps.setInt(2, pasajeroId);
            ps.setString(3, asiento);
            ps.setString(4, "confirmada");
            ps.setString(5, "no definido");
            ps.setDouble(6, 0.0);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarPago(int pasajeroId, int vueloId, String metodoPago, double totalPagado) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE reservas SET metodo_pago = ?, total_pagado = ? WHERE pasajero_id = ? AND vuelo_id = ?"
            );
            ps.setString(1, metodoPago);
            ps.setDouble(2, totalPagado);
            ps.setInt(3, pasajeroId);
            ps.setInt(4, vueloId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Reserva buscarPorId(int idReserva) {
        Reserva r = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reservas WHERE id = ?");
            ps.setInt(1, idReserva);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                r = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("vuelo_id"),
                    rs.getInt("pasajero_id"),
                    rs.getString("asiento"),
                    rs.getString("estado"),
                    rs.getString("metodo_pago"),
                    rs.getDouble("total_pagado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    public List<Reserva> getReservasActivasPorPasajero(int pasajeroId) {
        List<Reserva> lista = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reservas WHERE pasajero_id = ? AND estado = 'confirmada'");
            ps.setInt(1, pasajeroId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reserva r = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("vuelo_id"),
                    rs.getInt("pasajero_id"),
                    rs.getString("asiento"),
                    rs.getString("estado"),
                    rs.getString("metodo_pago"),
                    rs.getDouble("total_pagado")
                );
                lista.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void cancelarReserva(int idReserva) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE reservas SET estado = 'cancelada' WHERE id = ?");
            ps.setInt(1, idReserva);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cambiarAsiento(int idReserva, String nuevoAsiento) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE reservas SET asiento = ? WHERE id = ?");
            ps.setString(1, nuevoAsiento);
            ps.setInt(2, idReserva);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean puedeCancelar(int idReserva) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT estado FROM reservas WHERE id = ?");
            ps.setInt(1, idReserva);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("estado").equals("confirmada");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void realizarCheckin(int idReserva) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE reservas SET estado = 'check-in' WHERE id = ?");
            ps.setInt(1, idReserva);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
