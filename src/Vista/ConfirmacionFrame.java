/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.ControladorReservas;
import Modelo.Vuelo;

import javax.swing.*;
/**
 *
 * @author julic
 */
public class ConfirmacionFrame extends JFrame {

    public ConfirmacionFrame(Vuelo vuelo, String nombre, String fechaNac, String pasaporte) {
        setTitle("Confirmar Reserva");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JTextArea resumenArea = new JTextArea();
        resumenArea.setBounds(30, 30, 380, 200);
        resumenArea.setEditable(false);
        resumenArea.setText("CONFIRMAR RESERVA:\n\n" +
            "Pasajero: " + nombre + "\n" +
            "Nacimiento: " + fechaNac + "\n" +
            (pasaporte.isEmpty() ? "" : "Pasaporte: " + pasaporte + "\n") +
            "\nVUELO:\n" +
            vuelo.toString()
        );
        add(resumenArea);

        JButton confirmarBtn = new JButton("Confirmar y pagar");
        confirmarBtn.setBounds(130, 250, 180, 30);
        add(confirmarBtn);

        confirmarBtn.addActionListener(e -> {
            boolean ok = ControladorReservas.guardarReserva(
                    vuelo.getAerolinea(),
                    vuelo.getOrigen(),
                    vuelo.getDestino(),
                    vuelo.getFecha(),      // formato yyyy-MM-dd
                    vuelo.getHora(),       // formato hh:mm:ss
                    vuelo.getDuracion(),
                    1,                     // cantidad = 1 pasajero
                     vuelo.getPrecio()
            );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Reserva guardada correctamente. Proceder a pago...");
            // Aquí podrías abrir la ventana de pago
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar la reserva.");
        }
        });
    }
}
