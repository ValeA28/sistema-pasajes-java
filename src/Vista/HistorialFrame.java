/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.ConexionBD;
import Modelo.Pasajero;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class HistorialFrame extends JFrame {

    private Pasajero pasajero;

    public HistorialFrame(Pasajero pasajero) {
        this.pasajero = pasajero;

        setTitle("🧾 Historial de compras");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Fondo con imagen
        JPanel fondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon(getClass().getResource("/Vista/imagenes/ticket_fondo.jpg"));
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        fondo.setLayout(null);
        setContentPane(fondo);

        // Panel intermedio translúcido
        JPanel capa = new JPanel(null);
        capa.setBounds(20, 20, 740, 320);
        capa.setBackground(new Color(0, 0, 0, 130));
        fondo.add(capa);

        // Tabla y modelo
        String[] columnas = {"ID", "Vuelo", "Fecha", "Hora", "Estado", "Método de pago", "Total"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    comp.setBackground(new Color(255, 255, 255, 210));
                    comp.setForeground(Color.BLACK);
                } else {
                    comp.setBackground(new Color(100, 150, 255, 200));
                    comp.setForeground(Color.BLACK);
                }
                return comp;
            }
        };
        tabla.setRowHeight(28);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setOpaque(false);
        tabla.setShowGrid(true);

        // Encabezado
        JTableHeader header = tabla.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(30, 30, 30)); // gris oscuro
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Scroll pane estilizado
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 10, 720, 230);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        capa.add(scroll);

        // Botón volver
        JButton volverBtn = new JButton("⬅️ Volver al menú");
        volverBtn.setBounds(250, 260, 240, 35);
        volverBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        capa.add(volverBtn);

        volverBtn.addActionListener(e -> {
            dispose();
            new MenuPrincipalFrame(pasajero).setVisible(true);
        });

        // Consulta SQL
        String query = """
                SELECT c.id, c.vuelo_id, v.origen, v.destino, v.fecha, v.hora, v.estado,
                       c.metodo_pago, c.total
                FROM compras c
                JOIN vuelos v ON c.vuelo_id = v.id
                WHERE c.pasajero_id = ?
                """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, pasajero.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String vuelo = rs.getString("origen") + " → " + rs.getString("destino");
                    Object[] fila = {
                        rs.getInt("id"),
                        vuelo,
                        rs.getDate("fecha"),
                        rs.getString("hora"),
                        rs.getString("estado"),
                        rs.getString("metodo_pago"),
                        "$" + rs.getDouble("total")
                    };
                    modelo.addRow(fila);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al cargar historial: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

