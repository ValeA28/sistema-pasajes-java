/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Pasajero;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author julic
 */
public class MenuPrincipalFrame extends JFrame {

    public MenuPrincipalFrame(Pasajero pasajero) {
        setTitle("🏠 Menú Principal");
        setSize(420, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Fondo con imagen
        JPanel fondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon(getClass().getResource("/Vista/imagenes/fondo.jpg"));
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        fondo.setLayout(null);
        setContentPane(fondo);

        int anchoBoton = 240;
        int xCentro = (420 - anchoBoton) / 2;

        JLabel saludo = new JLabel("👋 Bienvenida/o, " + pasajero.getNombre());
        saludo.setBounds(xCentro - 10, 30, 300, 25);
        saludo.setFont(new Font("SansSerif", Font.BOLD, 16));
        saludo.setForeground(Color.WHITE);
        fondo.add(saludo);

        // Botones con celeste más claro
        Color celesteClaro = new Color(102, 178, 255);

        JButton buscarBtn = crearBoton("🔍 Buscar vuelos", xCentro, 80, anchoBoton, celesteClaro);
        fondo.add(buscarBtn);

        JButton historialBtn = crearBoton("📄 Ver historial", xCentro, 140, anchoBoton, celesteClaro);
        fondo.add(historialBtn);

        JButton gestionarBtn = crearBoton("✏ Cancelar o Cambiar Reserva", xCentro, 200, anchoBoton, celesteClaro);
        gestionarBtn.setFont(new Font("SansSerif", Font.PLAIN, 12)); // texto largo
        fondo.add(gestionarBtn);

        JButton checkinBtn = crearBoton("🛄 Check-in en Línea", xCentro, 270, anchoBoton, celesteClaro);
        fondo.add(checkinBtn);

        // Botón Volver con color rojo original
        JButton volverBtn = new JButton("🔙 Volver");
        volverBtn.setBounds(xCentro, 330, anchoBoton, 40);
        volverBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        volverBtn.setBackground(new Color(220, 53, 69));
        volverBtn.setForeground(Color.WHITE);
        volverBtn.setFocusPainted(false);
        fondo.add(volverBtn);

        // Acciones
        buscarBtn.addActionListener(e -> {
            dispose();
            new SeleccionarVueloFrame().setVisible(true);
        });

        historialBtn.addActionListener(e -> {
            dispose();
            new HistorialFrame(pasajero).setVisible(true);
        });

        gestionarBtn.addActionListener(e -> {
            new GestionReservaFrame().setVisible(true);
        });

        checkinBtn.addActionListener(e -> {
            new CheckinFrame(pasajero).setVisible(true);
        });

        volverBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private JButton crearBoton(String texto, int x, int y, int ancho, Color colorFondo) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, ancho, 40);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}



