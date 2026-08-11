/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Pasajero;
import Modelo.SesionUsuario;
import Modelo.Vuelo;
import Modelo.VueloDAO;
import Util.ContextoReservas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DatosPasajeroFrame extends JFrame {

    private Vuelo vuelo;
    private int cantidadPasajeros;
    private boolean esInternacional;

    private ArrayList<JTextField> nombreFields = new ArrayList<>();
    private ArrayList<JTextField> fechaFields = new ArrayList<>();
    private ArrayList<JTextField> dniFields = new ArrayList<>();
    private ArrayList<JTextField> pasaporteFields = new ArrayList<>();

    public DatosPasajeroFrame(Vuelo vueloSeleccionado, int cantidad) {
        this.vuelo = vueloSeleccionado;
        this.cantidadPasajeros = cantidad;
        this.esInternacional = !esArgentino(vuelo.getOrigen()) || !esArgentino(vuelo.getDestino());

        if (cantidadPasajeros == 0) {
            JOptionPane.showMessageDialog(this, "Debe haber al menos un pasajero.");
            dispose();
            return;
        }

        int altoVentana = 220 + cantidadPasajeros * (esInternacional ? 130 : 100) + 60;

        setTitle("Datos de Pasajeros");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel de fondo gris
        JPanel panelFondo = new JPanel();
        panelFondo.setBackground(new Color(60, 60, 60)); // gris oscuro
        panelFondo.setLayout(null);
        panelFondo.setPreferredSize(new Dimension(800, altoVentana));
        setContentPane(panelFondo);

        // Panel superior semitransparente
        JPanel capa = new JPanel();
        capa.setBackground(new Color(0, 0, 0, 140));
        capa.setBounds(0, 0, 800, altoVentana);
        capa.setLayout(null);
        panelFondo.add(capa);

        JLabel vueloLabel = new JLabel("✈️ " + vuelo.getOrigen() + " → " + vuelo.getDestino()
                + " | " + vuelo.getFecha() + " " + vuelo.getHora());
        vueloLabel.setBounds(20, 10, 760, 25);
        vueloLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        vueloLabel.setForeground(Color.WHITE);
        capa.add(vueloLabel);

        for (int i = 0; i < cantidadPasajeros; i++) {
            int y = 50 + i * (esInternacional ? 130 : 100);

            JLabel nombreLabel = new JLabel("Nombre completo " + (i + 1) + ":");
            nombreLabel.setBounds(20, y, 180, 25);
            nombreLabel.setForeground(Color.WHITE);
            capa.add(nombreLabel);

            JTextField nombreField = new JTextField();
            nombreField.setBounds(210, y, 550, 25);
            capa.add(nombreField);
            nombreFields.add(nombreField);

            JLabel fechaLabel = new JLabel("Fecha nac. (YYYY-MM-DD):");
            fechaLabel.setBounds(20, y + 30, 180, 25);
            fechaLabel.setForeground(Color.WHITE);
            capa.add(fechaLabel);

            JTextField fechaField = new JTextField();
            fechaField.setBounds(210, y + 30, 550, 25);
            capa.add(fechaField);
            fechaFields.add(fechaField);

            JLabel dniLabel = new JLabel("DNI:");
            dniLabel.setBounds(20, y + 60, 180, 25);
            dniLabel.setForeground(Color.WHITE);
            capa.add(dniLabel);

            JTextField dniField = new JTextField();
            dniField.setBounds(210, y + 60, 550, 25);
            capa.add(dniField);
            dniFields.add(dniField);

            if (esInternacional) {
                JLabel passLabel = new JLabel("Pasaporte:");
                passLabel.setBounds(20, y + 90, 180, 25);
                passLabel.setForeground(Color.WHITE);
                capa.add(passLabel);

                JTextField passField = new JTextField();
                passField.setBounds(210, y + 90, 550, 25);
                capa.add(passField);
                pasaporteFields.add(passField);
            } else {
                pasaporteFields.add(new JTextField()); // placeholder
            }
        }

        JButton continuarBtn = new JButton("✅ Continuar al pago");
        continuarBtn.setBounds(310, 90 + cantidadPasajeros * (esInternacional ? 130 : 100), 180, 35);
        continuarBtn.setBackground(new Color(0, 153, 51));
        continuarBtn.setForeground(Color.WHITE);
        continuarBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        capa.add(continuarBtn);

        continuarBtn.addActionListener(e -> {
            ArrayList<Pasajero> pasajeros = new ArrayList<>();
            VueloDAO dao = new VueloDAO();

            Pasajero usuarioActual = SesionUsuario.getUsuarioActual();
            if (usuarioActual == null) {
                JOptionPane.showMessageDialog(this, "❌ No hay usuario en sesión. Volvé a iniciar sesión.");
                return;
            }

            for (int i = 0; i < cantidadPasajeros; i++) {
                String nombre = nombreFields.get(i).getText().trim();
                String fecha = fechaFields.get(i).getText().trim();
                String dni = dniFields.get(i).getText().trim();
                String pass = esInternacional ? pasaporteFields.get(i).getText().trim() : null;

                if (nombre.isEmpty() || fecha.isEmpty() || dni.isEmpty() || (esInternacional && pass.isEmpty())) {
                    JOptionPane.showMessageDialog(this, "Completá todos los campos del pasajero " + (i + 1));
                    return;
                }

                if (!dni.matches("\\d{7,9}")) {
                    JOptionPane.showMessageDialog(this, "El DNI debe tener entre 7 y 9 dígitos. Pasajero " + (i + 1));
                    return;
                }

                Pasajero p;
                if (i == 0) {
                    p = usuarioActual;
                    p.setNombre(nombre);
                    p.setFechaNacimiento(fecha);
                    p.setDni(dni);
                    if (esInternacional) p.setPasaporte(pass);
                } else {
                    p = new Pasajero(nombre, fecha, dni, pass);
                    int pasajeroId = dao.guardarPasajero(p);
                    if (pasajeroId == -1) {
                        JOptionPane.showMessageDialog(this, "❌ No se pudo guardar el pasajero " + (i + 1));
                        return;
                    }
                    p.setId(pasajeroId);
                }
                pasajeros.add(p);
            }

            ContextoReservas.setPasajeros(pasajeros);
            new PagoFrame(vuelo, pasajeros).setVisible(true);
            dispose();
        });

        pack();
        setLocationRelativeTo(null);
    }

    private boolean esArgentino(String ciudad) {
        String[] ciudadesArg = {"Buenos Aires", "Mendoza", "Córdoba", "Salta", "Rosario", "Bariloche"};
        for (String c : ciudadesArg) {
            if (ciudad.equalsIgnoreCase(c)) return true;
        }
        return false;
    }
}








