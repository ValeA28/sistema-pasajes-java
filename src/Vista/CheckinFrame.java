/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.*;
import Util.CheckinPDFGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.imageio.ImageIO;
/**
 *
 * @author julic
 */
public class CheckinFrame extends JFrame {

    private Pasajero pasajero;
    private JTable tabla;
    private DefaultTableModel modelo;

    public CheckinFrame(Pasajero pasajero) {
        this.pasajero = pasajero;

        setTitle("🧍‍♀ Check-in en Línea – " + pasajero.getNombre());
        setSize(760, 520); // aumentamos tamaño de ventana
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        FondoPanel fondo = new FondoPanel("/Vista/imagenes/ticket_fondo.jpg");
        fondo.setLayout(null);
        setContentPane(fondo);

        modelo = new DefaultTableModel();
        modelo.addColumn("Reserva ID");
        modelo.addColumn("Vuelo");
        modelo.addColumn("Fecha");
        modelo.addColumn("Hora");
        modelo.addColumn("Asiento");

        tabla = new JTable(modelo);
        tabla.setOpaque(false);
        tabla.setBackground(new Color(255, 255, 255, 180));
        tabla.setForeground(Color.BLACK);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setRowHeight(30); // altura por fila
        tabla.setPreferredScrollableViewportSize(new Dimension(680, 300)); // más alto
        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setBackground(new Color(0, 0, 0, 180));
        tabla.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.setBounds(30, 30, 680, 320); // área de tabla más grande
        fondo.add(scroll);

        JButton checkinBtn = new JButton("✔ Realizar Check-in");
        checkinBtn.setBounds(280, 380, 200, 40); // botón más abajo
        fondo.add(checkinBtn);

        cargarReservasPendientes();

        checkinBtn.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠ Seleccioná una reserva primero.");
                return;
            }

            int idReserva = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
            ReservaDAO rdao = new ReservaDAO();
            Reserva reserva = rdao.buscarPorId(idReserva);
            Vuelo vuelo = new VueloDAO().buscarPorId(reserva.getVueloId());

            LocalDateTime vueloDate = LocalDateTime.parse(vuelo.getFecha() + "T" + vuelo.getHora());
            LocalDateTime ahora = LocalDateTime.now();

            if (vueloDate.isAfter(ahora.plusHours(24))) {
                JOptionPane.showMessageDialog(this, "⛔ El check-in solo está habilitado dentro de las 24h previas al vuelo.");
                return;
            }

            rdao.realizarCheckin(idReserva);
            CheckinPDFGenerator.generar(vuelo, pasajero, reserva.getAsiento(), reserva.getMetodoPago(), reserva.getTotalPagado());

            JOptionPane.showMessageDialog(this, "✅ Check-in realizado y tarjeta de embarque generada.");
            cargarReservasPendientes();
        });
    }

    private void cargarReservasPendientes() {
        modelo.setRowCount(0);
        ReservaDAO rdao = new ReservaDAO();
        VueloDAO vdao = new VueloDAO();

        List<Reserva> reservas = rdao.getReservasActivasPorPasajero(pasajero.getId());
        for (Reserva r : reservas) {
            if (!r.getEstado().equals("check-in")) {
                Vuelo vuelo = vdao.buscarPorId(r.getVueloId());
                modelo.addRow(new Object[]{
                        r.getId(), vuelo.getOrigen() + " → " + vuelo.getDestino(),
                        vuelo.getFecha(), vuelo.getHora(), r.getAsiento()
                });
            }
        }
    }

    // Fondo con imagen oscurecida
    static class FondoPanel extends JPanel {
        private final Image imagen;

        public FondoPanel(String ruta) {
            Image original = null;
            try {
                original = ImageIO.read(getClass().getResource(ruta));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (original != null) {
                BufferedImage buffered = new BufferedImage(
                        original.getWidth(null),
                        original.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2 = buffered.createGraphics();
                g2.drawImage(original, 0, 0, null);
                g2.setColor(new Color(0, 0, 0, 100)); // oscurecer
                g2.fillRect(0, 0, buffered.getWidth(), buffered.getHeight());
                g2.dispose();
                this.imagen = buffered;
            } else {
                this.imagen = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}