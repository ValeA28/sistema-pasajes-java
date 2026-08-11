/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.ConexionBD;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.*;
import javax.swing.*;
/**
 *
 * @author julic
 */
public class PanelAsientos extends JPanel {
    private int filas = 6;
    private int columnas = 4;
    private JButton[][] asientos;
    private int vueloId;
    private int pasajeroId;

    public PanelAsientos(int vueloId, int pasajeroId) {
        this.vueloId = vueloId;
        this.pasajeroId = pasajeroId;

        setLayout(new BorderLayout());

        JLabel imagenAvion = new JLabel(new ImageIcon("src/Imagenes/avion_simulado.png"));
        imagenAvion.setHorizontalAlignment(JLabel.CENTER);
        add(imagenAvion, BorderLayout.NORTH);

        JPanel panelAsientos = new JPanel(new GridLayout(filas, columnas));
        asientos = new JButton[filas][columnas];

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                String codigo = (f + 1) + "" + (char) ('A' + c);
                JButton boton = new JButton(codigo);
                boton.setBackground(Color.GREEN);
                final int fila = f, col = c;
                boton.addActionListener(e -> seleccionarAsiento(fila, col));
                asientos[f][c] = boton;
                panelAsientos.add(boton);
            }
        }

        add(panelAsientos, BorderLayout.CENTER);
        cargarAsientosOcupados();
    }

    private void seleccionarAsiento(int fila, int columna) {
        String codigo = (fila + 1) + "" + (char) ('A' + columna);

        if (asientos[fila][columna].getBackground() == Color.RED) {
            JOptionPane.showMessageDialog(this, "Este asiento ya está ocupado");
            return;
        }

        try (Connection con = ConexionBD.conectar()) {
            // Validar si el pasajero ya tiene reserva
            PreparedStatement check = con.prepareStatement(
                "SELECT COUNT(*) FROM reservas WHERE vuelo_id = ? AND pasajero_id = ?");
            check.setInt(1, vueloId);
            check.setInt(2, pasajeroId);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Ya seleccionaste un asiento para este pasajero.");
                return;
            }

            // Insertar asiento reservado
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO reservas (vuelo_id, pasajero_id, estado, asiento) VALUES (?, ?, 'Activa', ?)");
            ps.setInt(1, vueloId);
            ps.setInt(2, pasajeroId);
            ps.setString(3, codigo);
            ps.executeUpdate();

            asientos[fila][columna].setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "✅ Asiento reservado: " + codigo);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error al guardar reserva");
        }
    }

    private void cargarAsientosOcupados() {
        try (Connection con = ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement("SELECT asiento FROM reservas WHERE vuelo_id = ?");
            ps.setInt(1, vueloId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String asiento = rs.getString("asiento");
                Matcher m = Pattern.compile("(\\d+)([A-Z])").matcher(asiento);
                if (m.matches()) {
                    int fila = Integer.parseInt(m.group(1)) - 1;
                    int col = m.group(2).charAt(0) - 'A';
                    if (fila >= 0 && fila < filas && col >= 0 && col < columnas) {
                        asientos[fila][col].setBackground(Color.RED);
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<String> getAsientosSeleccionados() {
        ArrayList<String> seleccionados = new ArrayList<>();
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (asientos[f][c].getBackground() == Color.RED) {
                    String codigo = (f + 1) + "" + (char) ('A' + c);
                    seleccionados.add(codigo);
                }
            }
        }
        return seleccionados;
    }
}