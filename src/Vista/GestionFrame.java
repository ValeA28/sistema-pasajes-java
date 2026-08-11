/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Pasajero;
import Util.GestorReservas;
import javax.swing.*;
/**
 *
 * @author julic
 */
public class GestionFrame extends JFrame {

    private Pasajero pasajero;

    public GestionFrame(Pasajero pasajero) {
        this.pasajero = pasajero;

        setTitle("🧳 Gestión de Reservas");
        setSize(400, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel codigoLabel = new JLabel("Código de reserva:");
        codigoLabel.setBounds(30, 30, 140, 25);
        add(codigoLabel);

        JTextField codigoField = new JTextField();
        codigoField.setBounds(180, 30, 160, 25);
        add(codigoField);

        JLabel destinoLabel = new JLabel("Nuevo destino:");
        destinoLabel.setBounds(30, 70, 140, 25);
        add(destinoLabel);

        JTextField destinoField = new JTextField();
        destinoField.setBounds(180, 70, 160, 25);
        add(destinoField);

        JButton verBtn = new JButton("🗂 Ver historial");
        verBtn.setBounds(30, 120, 150, 30);
        add(verBtn);

        JButton cancelarBtn = new JButton("🛑 Cancelar reserva");
        cancelarBtn.setBounds(30, 160, 150, 30);
        add(cancelarBtn);

        JButton modificarBtn = new JButton("🔄 Cambiar destino");
        modificarBtn.setBounds(200, 160, 150, 30);
        add(modificarBtn);

        verBtn.addActionListener(e -> {
            new HistorialFrame(pasajero).setVisible(true);
        });

        cancelarBtn.addActionListener(e -> {
            String cod = codigoField.getText().trim();
            if (cod.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresá un código primero.");
                return;
            }
            String msg = GestorReservas.cancelarReserva(cod);
            JOptionPane.showMessageDialog(this, msg);
        });

        modificarBtn.addActionListener(e -> {
            String cod = codigoField.getText().trim();
            String nuevo = destinoField.getText().trim();
            if (cod.isEmpty() || nuevo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completá ambos campos para modificar.");
                return;
            }
            String msg = GestorReservas.cambiarReserva(cod, nuevo);
            JOptionPane.showMessageDialog(this, msg);
        });
    }
}
