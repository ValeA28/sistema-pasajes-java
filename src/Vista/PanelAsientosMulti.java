/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.ConexionBD;
import Modelo.Pasajero;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
/**
 *
 * @author julic
 */
public class PanelAsientosMulti extends JPanel {

    private int filas = 6;
    private int columnas = 6; // 3–pasillo–3
    private JButton[][] asientos;
    private int vueloId;
    private ArrayList<Pasajero> pasajeros;

    private HashMap<Pasajero, String> asignaciones = new HashMap<>();
    private HashSet<String> ocupados = new HashSet<>();

    private int seleccionActual = 0;

    public PanelAsientosMulti(int vueloId, ArrayList<Pasajero> pasajeros) {
        this.vueloId = vueloId;
        this.pasajeros = pasajeros;

        setLayout(new BorderLayout());

        JPanel leyenda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        leyenda.add(crearItemLeyenda(Color.GREEN, "Disponible"));
        leyenda.add(Box.createHorizontalStrut(15));
        leyenda.add(crearItemLeyenda(Color.RED, "Ocupado"));
        leyenda.add(Box.createHorizontalStrut(15));
        leyenda.add(crearItemLeyenda(Color.BLUE, "Seleccionado"));
        add(leyenda, BorderLayout.NORTH);

        JPanel panelGrid = new JPanel(new GridLayout(filas, columnas + 1, 10, 8)); // +1 para pasillo
        asientos = new JButton[filas][columnas];

        cargarAsientosOcupados();

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (c == 3) {
                    panelGrid.add(Box.createRigidArea(new Dimension(25, 0))); // Pasillo
                    continue;
                }

                int realC = c < 3 ? c : c - 1; // Ajustar índice por pasillo

                String codigo = (f + 1) + "" + (char) ('A' + realC);
                JButton boton = new JButton(codigo);
                boton.setPreferredSize(new Dimension(70, 40));
                boton.setBackground(Color.GREEN);

                if (ocupados.contains(codigo)) {
                    boton.setBackground(Color.RED);
                    boton.setEnabled(false);
                }

                boton.addActionListener(e -> seleccionarAsiento(boton, codigo));
                asientos[f][realC] = boton;
                panelGrid.add(boton);
            }
        }

        add(panelGrid, BorderLayout.CENTER);
    }

    private void seleccionarAsiento(JButton boton, String codigo) {
        if (seleccionActual >= pasajeros.size()) {
            JOptionPane.showMessageDialog(this, "Ya seleccionaste los " + pasajeros.size() + " asientos.");
            return;
        }

        if (asignaciones.containsValue(codigo)) {
            JOptionPane.showMessageDialog(this, "Este asiento ya fue seleccionado.");
            return;
        }

        Pasajero p = pasajeros.get(seleccionActual);
        asignaciones.put(p, codigo);
        boton.setBackground(Color.BLUE);
        boton.setEnabled(false);

        seleccionActual++;
        if (seleccionActual < pasajeros.size()) {
            JOptionPane.showMessageDialog(this, "🧍 Siguiente: " + pasajeros.get(seleccionActual).getNombre());
        } else {
            JOptionPane.showMessageDialog(this, "✅ Todos los asientos fueron seleccionados.");
        }
    }

    private void cargarAsientosOcupados() {
        try (Connection con = ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement("SELECT asiento FROM reservas WHERE vuelo_id = ?");
            ps.setInt(1, vueloId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ocupados.add(rs.getString("asiento"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public HashMap<Pasajero, String> getAsignaciones() {
        return asignaciones;
    }

    private JPanel crearItemLeyenda(Color color, String texto) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel circulo = new JLabel("●");
        circulo.setForeground(color);

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(Color.BLACK);

        panel.add(circulo);
        panel.add(etiqueta);
        panel.setOpaque(false);

        return panel;
    }
}