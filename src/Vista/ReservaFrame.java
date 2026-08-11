/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Vuelo;
import Modelo.Pasajero;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 *
 * @author julic
 */
public class ReservaFrame extends JFrame {

    private Vuelo vuelo;
    private JTextField nombreField, fechaField, dniField;

    public ReservaFrame(Vuelo vueloSeleccionado) {
        this.vuelo = vueloSeleccionado;

        setTitle("Reservar Pasaje");
        setSize(360, 260);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel nombreLabel = new JLabel("Nombre completo:");
        nombreLabel.setBounds(20, 20, 130, 25);
        add(nombreLabel);

        nombreField = new JTextField();
        nombreField.setBounds(160, 20, 160, 25);
        add(nombreField);

        JLabel fechaLabel = new JLabel("Fecha nac. (yyyy-MM-dd):");
        fechaLabel.setBounds(20, 60, 150, 25);
        add(fechaLabel);

        fechaField = new JTextField();
        fechaField.setBounds(160, 60, 160, 25);
        add(fechaField);

        JLabel dniLabel = new JLabel("DNI:");
        dniLabel.setBounds(20, 100, 130, 25);
        add(dniLabel);

        dniField = new JTextField();
        dniField.setBounds(160, 100, 160, 25);
        add(dniField);

        JButton reservarBtn = new JButton("Reservar");
        reservarBtn.setBounds(100, 150, 140, 30);
        add(reservarBtn);

        reservarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText().trim();
                String fecha = fechaField.getText().trim();
                String dni = dniField.getText().trim();

                if (nombre.isEmpty() || fecha.isEmpty() || dni.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor completá todos los campos.");
                    return;
                }

                if (!dni.matches("\\d{7,9}")) {
                    JOptionPane.showMessageDialog(null, "El DNI debe tener entre 7 y 9 dígitos.");
                    return;
                }

                ArrayList<Pasajero> lista = new ArrayList<>();
                lista.add(new Pasajero(nombre, fecha, dni, null)); // Pasaporte = null para vuelo nacional

                JOptionPane.showMessageDialog(null, "Reserva creada ✅");
                new PagoFrame(vuelo, lista).setVisible(true);
                dispose();
            }
        });
    }
}


