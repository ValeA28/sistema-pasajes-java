/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Vuelo;
import Modelo.VueloDAO;
import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 *
 * @author julic
 */
public class EstadoVueloAdminFrame extends JFrame {

    private JTable tabla;
    private List<Vuelo> vuelos; // <- corregido: ahora es List en lugar de ArrayList
    private JComboBox<String> estadoCombo;

    public EstadoVueloAdminFrame() {
        setTitle("✈️ Administrar Estado de Vuelos");
        setSize(720, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        VueloDAO dao = new VueloDAO();
        vuelos = dao.obtenerVuelos(); // ya no da error

        String[] columnas = {"ID", "Origen", "Destino", "Fecha", "Hora", "Estado actual"};
        Object[][] datos = new Object[vuelos.size()][columnas.length];

        for (int i = 0; i < vuelos.size(); i++) {
            Vuelo v = vuelos.get(i);
            datos[i][0] = v.getId();
            datos[i][1] = v.getOrigen();
            datos[i][2] = v.getDestino();
            datos[i][3] = v.getFecha();
            datos[i][4] = v.getHora();
            datos[i][5] = v.getEstado();
        }

        tabla = new JTable(datos, columnas);
        tabla.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        JPanel abajo = new JPanel(null);
        abajo.setPreferredSize(new Dimension(720, 80));

        JLabel estadoLabel = new JLabel("Nuevo estado:");
        estadoLabel.setBounds(30, 20, 100, 25);
        abajo.add(estadoLabel);

        estadoCombo = new JComboBox<>(new String[]{"A tiempo", "Retrasado", "Cancelado"});
        estadoCombo.setBounds(140, 20, 140, 25);
        abajo.add(estadoCombo);

        JButton cambiarBtn = new JButton("Guardar cambio");
        cambiarBtn.setBounds(320, 20, 160, 30);
        abajo.add(cambiarBtn);

        add(abajo, BorderLayout.SOUTH);

        cambiarBtn.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccioná un vuelo primero.");
                return;
            }

            int vueloId = vuelos.get(fila).getId();
            String nuevoEstado = (String) estadoCombo.getSelectedItem();

            dao.actualizarEstadoVuelo(vueloId, nuevoEstado);
            JOptionPane.showMessageDialog(this, "✅ Estado actualizado a \"" + nuevoEstado + "\"");
            dispose(); // Cerramos la ventana tras guardar
        });
    }
}


