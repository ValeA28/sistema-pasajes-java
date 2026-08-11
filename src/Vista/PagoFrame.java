/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.EmailService;
import Modelo.Pasajero;
import Modelo.ReservaDAO;
import Modelo.Vuelo;
import Modelo.VueloDAO;
import Util.TicketGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PagoFrame extends JFrame {

    private Vuelo vuelo;
    private ArrayList<Pasajero> pasajeros;
    private PanelAsientosMulti panelAsientos;

    public PagoFrame(Vuelo vuelo, ArrayList<Pasajero> pasajeros) {
        this.vuelo = vuelo;
        this.pasajeros = pasajeros;

        setTitle("💳 Confirmación de compra");
        setSize(680, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel vueloLabel = new JLabel("✈️ " + vuelo.getOrigen() + " → " + vuelo.getDestino() +
                " | " + vuelo.getFecha() + " " + vuelo.getHora());
        vueloLabel.setBounds(30, 20, 600, 25);
        vueloLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(vueloLabel);

        JLabel cantidadLabel = new JLabel("👥 Pasajeros: " + pasajeros.size());
        cantidadLabel.setBounds(30, 60, 200, 25);
        add(cantidadLabel);

        double total = vuelo.getPrecio() * pasajeros.size();
        JLabel totalLabel = new JLabel("💰 Total a pagar: $" + total);
        totalLabel.setBounds(30, 100, 300, 25);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(totalLabel);

        JLabel metodoLabel = new JLabel("Método de pago:");
        metodoLabel.setBounds(30, 140, 150, 25);
        add(metodoLabel);

        JComboBox<String> metodoBox = new JComboBox<>(new String[]{
                "Tarjeta de crédito", "Tarjeta de débito", "Transferencia", "Efectivo"
        });
        metodoBox.setBounds(180, 140, 220, 25);
        add(metodoBox);

        JLabel asientoLabel = new JLabel("🪑 Elegí tus asientos:");
        asientoLabel.setBounds(30, 180, 200, 25);
        add(asientoLabel);

        panelAsientos = new PanelAsientosMulti(vuelo.getId(), pasajeros);
        panelAsientos.setBounds(30, 210, 600, 280);
        add(panelAsientos);

        JButton confirmarBtn = new JButton("✔️ Confirmar compra");
        confirmarBtn.setBounds(240, 510, 180, 35);
        confirmarBtn.setBackground(new Color(0, 153, 51));
        confirmarBtn.setForeground(Color.WHITE);
        add(confirmarBtn);

        confirmarBtn.addActionListener(e -> {
            String metodo = (String) metodoBox.getSelectedItem();
            HashMap<Pasajero, String> asignaciones = panelAsientos.getAsignaciones();

            if (asignaciones.size() < pasajeros.size()) {
                JOptionPane.showMessageDialog(this, "⚠️ Debés seleccionar " + pasajeros.size() + " asientos.");
                return;
            }

            if (metodo.equals("Tarjeta de crédito") || metodo.equals("Tarjeta de débito")) {
                new FormularioTarjetaFrame(datosTarjeta -> {
                    procesarCompra(metodo, asignaciones, total);
                }).setVisible(true);
            } else {
                procesarCompra(metodo, asignaciones, total);
            }
        });
    }

    private void procesarCompra(String metodo, HashMap<Pasajero, String> asignaciones, double total) {
        Pasajero pasajeroPrincipal = pasajeros.get(0);

        VueloDAO dao = new VueloDAO();
        dao.guardarCompra(vuelo.getId(), pasajeroPrincipal.getId(), metodo, total);

        ReservaDAO rdao = new ReservaDAO();
        for (Pasajero p : asignaciones.keySet()) {
            String asiento = asignaciones.get(p);
            rdao.guardarReserva(vuelo.getId(), p.getId(), asiento);
            rdao.actualizarPago(p.getId(), vuelo.getId(), metodo, total);
            TicketGenerator.generar(vuelo, p, asiento, metodo, total);
        }

        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("🎫 Confirmación de compra\n");
        cuerpo.append("Vuelo: ").append(vuelo.getOrigen()).append(" → ").append(vuelo.getDestino()).append("\n");
        cuerpo.append("Fecha: ").append(vuelo.getFecha()).append(" ").append(vuelo.getHora()).append("\n");
        cuerpo.append("Pasajeros: ").append(pasajeros.size()).append("\n");
        cuerpo.append("Total pagado: $").append(total).append("\n\n");
        cuerpo.append("Asientos:\n");
        for (Pasajero p : asignaciones.keySet()) {
            cuerpo.append("- ").append(p.getNombre()).append(": ").append(asignaciones.get(p)).append("\n");
        }

        EmailService.enviarCorreo("cliente@ejemplo.com", "🛫 Confirmación de vuelo", cuerpo.toString());

        JOptionPane.showMessageDialog(this, "✅ Compra registrada correctamente");
        dispose();
        new MenuPrincipalFrame(pasajeroPrincipal).setVisible(true);
    }
}
