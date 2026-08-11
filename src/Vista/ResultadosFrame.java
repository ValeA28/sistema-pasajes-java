/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Vuelo;
import Util.ContextoReservas;
import Vista.DatosPasajeroFrame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/**
 *
 * @author julic
 */
public class ResultadosFrame extends JFrame {

    private JTable tabla;
    private ArrayList<Vuelo> resultados;
    private int cantidadPasajeros;

    public ResultadosFrame(ArrayList<Vuelo> vuelos, int cantidadPasajeros) {
        this.resultados = vuelos;
        this.cantidadPasajeros = cantidadPasajeros;

        setTitle("✈️ Resultados de búsqueda");
        setSize(720, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Origen", "Destino", "Fecha", "Hora", "Aerolínea", "Duración", "Precio", "Estado"};
        Object[][] datos = new Object[resultados.size()][columnas.length];

        for (int i = 0; i < resultados.size(); i++) {
            Vuelo v = resultados.get(i);
            datos[i][0] = v.getId();
            datos[i][1] = v.getOrigen();
            datos[i][2] = v.getDestino();
            datos[i][3] = v.getFecha();
            datos[i][4] = v.getHora();
            datos[i][5] = v.getAerolinea();
            datos[i][6] = v.getDuracion();
            datos[i][7] = "$" + v.getPrecio();
            datos[i][8] = v.getEstado();
        }

        tabla = new JTable(datos, columnas);
        tabla.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        JButton seleccionarBtn = new JButton("Seleccionar vuelo ✈️ y continuar");
        add(seleccionarBtn, BorderLayout.SOUTH);

        seleccionarBtn.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccioná un vuelo primero.");
                return;
            }

            Vuelo vueloSeleccionado = resultados.get(fila);
            ContextoReservas.setVueloSeleccionado(vueloSeleccionado);

            new DatosPasajeroFrame(vueloSeleccionado, cantidadPasajeros).setVisible(true);
            dispose();
        });
    }
}

