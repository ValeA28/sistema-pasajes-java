/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Reserva;
import Modelo.ReservaDAO;
import Modelo.Vuelo;
import Modelo.VueloDAO;
import Modelo.Pasajero;
import Modelo.SesionUsuario;
import Util.TicketGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class GestionReservaFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private ReservaDAO dao = new ReservaDAO();
    private VueloDAO vdao = new VueloDAO();
    private List<Reserva> reservas;

    public GestionReservaFrame() {
        setTitle("✈️ Gestión de Reserva");
        setSize(800, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel de fondo con imagen
        JPanel fondoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon(getClass().getResource("/Vista/imagenes/ticket_fondo.jpg"));
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        fondoPanel.setLayout(null);
        setContentPane(fondoPanel);

        // Capa translúcida sobre fondo
        JPanel capa = new JPanel(null);
        capa.setBounds(20, 20, 740, 320);
        capa.setBackground(new Color(0, 0, 0, 130)); // Gris oscuro translúcido
        fondoPanel.add(capa);

        Pasajero usuario = SesionUsuario.getUsuarioActual();
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión.");
            dispose();
            return;
        }

        reservas = dao.getReservasActivasPorPasajero(usuario.getId());

        modelo = new DefaultTableModel(new String[]{
                "ID Reserva", "Pasajero ID", "Vuelo", "Asiento", "Estado", "Método de Pago", "Total"
        }, 0);

        for (Reserva r : reservas) {
            Vuelo vuelo = vdao.buscarPorId(r.getIdVuelo());
            modelo.addRow(new Object[]{
                    r.getId(),
                    r.getPasajeroId(),
                    vuelo != null ? vuelo.getOrigen() + " → " + vuelo.getDestino() : "Desconocido",
                    r.getAsiento(),
                    r.getEstado(),
                    r.getMetodoPago(),
                    "$" + r.getTotalPagado()
            });
        }

        // Tabla estilizada
        tabla = new JTable(modelo) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    comp.setBackground(new Color(255, 255, 255, 200)); // blanco translúcido
                    comp.setForeground(Color.BLACK);
                } else {
                    comp.setBackground(new Color(100, 150, 255, 200)); // celeste de selección
                    comp.setForeground(Color.BLACK);
                }
                return comp;
            }
        };
        tabla.setRowHeight(28);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setOpaque(false);
        tabla.setShowGrid(true);

        // Encabezado de tabla
        JTableHeader header = tabla.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(30, 30, 30)); // gris oscuro
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Scroll estilizado
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 10, 720, 220);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        capa.add(scroll);

        // Botones
        JButton cancelarBtn = new JButton("❌ Cancelar Reserva");
        cancelarBtn.setBounds(100, 250, 200, 35);
        capa.add(cancelarBtn);

        JButton cambiarBtn = new JButton("🔁 Cambiar Asiento");
        cambiarBtn.setBounds(360, 250, 200, 35);
        capa.add(cambiarBtn);

        cancelarBtn.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un pasajero.");
                return;
            }

            int idReserva = (int) modelo.getValueAt(fila, 0);
            if (!dao.puedeCancelar(idReserva)) {
                JOptionPane.showMessageDialog(this, "Esta reserva no puede ser cancelada.");
                return;
            }

            dao.cancelarReserva(idReserva);
            JOptionPane.showMessageDialog(this, "✅ Reserva cancelada.");
            dispose();
        });

        cambiarBtn.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un pasajero.");
                return;
            }

            int idReserva = (int) modelo.getValueAt(fila, 0);
            Reserva reserva = dao.buscarPorId(idReserva);
            Vuelo vuelo = vdao.buscarPorId(reserva.getIdVuelo());

            if (reserva != null && vuelo != null) {
                Pasajero pasajero = SesionUsuario.getUsuarioActual();
                ArrayList<Pasajero> unoSolo = new ArrayList<>();
                unoSolo.add(pasajero);

                JFrame frame = new JFrame("Cambiar Asiento");
                frame.setSize(460, 420);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                PanelAsientosMulti panel = new PanelAsientosMulti(vuelo.getId(), unoSolo);
                frame.add(panel, BorderLayout.CENTER);

                JButton guardar = new JButton("Guardar");
                guardar.addActionListener(ev -> {
                    String nuevoAsiento = panel.getAsignaciones().get(pasajero);
                    if (nuevoAsiento != null) {
                        dao.cambiarAsiento(idReserva, nuevoAsiento);
                        JOptionPane.showMessageDialog(frame, "✅ Asiento cambiado.");

                        TicketGenerator.generar(
                                vuelo,
                                pasajero,
                                nuevoAsiento,
                                reserva.getMetodoPago(),
                                reserva.getTotalPagado()
                        );

                        frame.dispose();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Selecciona un asiento.");
                    }
                });

                JPanel abajo = new JPanel();
                abajo.add(guardar);
                frame.add(abajo, BorderLayout.SOUTH);
                frame.setVisible(true);
            }
        });
    }
}


