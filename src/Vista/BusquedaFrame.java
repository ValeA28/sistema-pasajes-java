/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Vuelo;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author julic
 */
public class BusquedaFrame extends JFrame {
    private int cantidadPasajeros;

    public BusquedaFrame(int cantidad) {
        this.cantidadPasajeros = cantidad;

        setTitle("Buscar Vuelos");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        JLabel origenLabel = new JLabel("Origen:");
        origenLabel.setBounds(40, 30, 100, 25);
        add(origenLabel);

        JTextField origenField = new JTextField();
        origenField.setBounds(140, 30, 200, 25);
        add(origenField);

        JLabel destinoLabel = new JLabel("Destino:");
        destinoLabel.setBounds(40, 70, 100, 25);
        add(destinoLabel);

        JTextField destinoField = new JTextField();
        destinoField.setBounds(140, 70, 200, 25);
        add(destinoField);

        JLabel fechaLabel = new JLabel("Fecha (yyyy-MM-dd):");
        fechaLabel.setBounds(40, 110, 150, 25);
        add(fechaLabel);

        JTextField fechaField = new JTextField();
        fechaField.setBounds(190, 110, 150, 25);
        add(fechaField);

        JButton buscarButton = new JButton("Buscar vuelos ✈️");
        buscarButton.setBounds(120, 160, 150, 30);
        add(buscarButton);

        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String origen = origenField.getText().trim();
                String destino = destinoField.getText().trim();
                String fecha = fechaField.getText().trim();

                ArrayList<Vuelo> resultados = buscarVuelos(origen, destino, fecha);

                new ResultadosFrame(resultados, cantidadPasajeros).setVisible(true);
                dispose();
            }
        });
    }

    private ArrayList<Vuelo> buscarVuelos(String origen, String destino, String fecha) {
        ArrayList<Vuelo> vuelos = new ArrayList<>();

        vuelos.add(new Vuelo(1, origen, destino, fecha, "09:00", "Aerolínea Demo", "2h", 15000, "A tiempo"));
        vuelos.add(new Vuelo(2, origen, destino, fecha, "16:30", "Aerolínea Prueba", "1h 45m", 18000, "A tiempo"));

        return vuelos;
    }
}
