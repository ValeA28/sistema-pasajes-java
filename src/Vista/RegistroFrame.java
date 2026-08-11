/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Vista;

import Controlador.ControladorUsuarios;
import Modelo.Pasajero;

import javax.swing.*;
import java.awt.*;

public class RegistroFrame extends JFrame {

    public RegistroFrame() {
        setTitle("Registro de Usuario");
        setSize(420, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel de fondo con imagen
        JPanel fondoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondo = new ImageIcon(getClass().getResource("/Vista/imagenes/fondo.jpg"));
                g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        fondoPanel.setLayout(null);
        setContentPane(fondoPanel);

        JLabel nombreLabel = new JLabel("Nombre Completo:");
        nombreLabel.setBounds(50, 30, 120, 25);
        fondoPanel.add(nombreLabel);

        JTextField nombreField = new JTextField();
        nombreField.setBounds(170, 30, 180, 25);
        fondoPanel.add(nombreField);

        JLabel correoLabel = new JLabel("Correo:");
        correoLabel.setBounds(50, 70, 100, 25);
        fondoPanel.add(correoLabel);

        JTextField correoField = new JTextField();
        correoField.setBounds(170, 70, 180, 25);
        fondoPanel.add(correoField);

        JLabel claveLabel = new JLabel("Contraseña:");
        claveLabel.setBounds(50, 110, 100, 25);
        fondoPanel.add(claveLabel);

        JPasswordField claveField = new JPasswordField();
        claveField.setBounds(170, 110, 180, 25);
        fondoPanel.add(claveField);

        JButton registrarButton = new JButton("Registrarse");
        registrarButton.setBounds(130, 180, 120, 30);
        fondoPanel.add(registrarButton);

        JButton volverButton = new JButton("Volver");
        volverButton.setBounds(130, 220, 120, 30);
        volverButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        fondoPanel.add(volverButton);

        // Imagen decorativa en la esquina inferior derecha
        ImageIcon icono = new ImageIcon(getClass().getResource("/Vista/imagenes/registro.png"));
        Image imagen = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel iconoLabel = new JLabel(new ImageIcon(imagen));
        iconoLabel.setBounds(320, 230, 60, 60);
        fondoPanel.add(iconoLabel);

        registrarButton.addActionListener(e -> {
            String nombre = nombreField.getText().trim();
            String correo = correoField.getText().trim();
            String clave = new String(claveField.getPassword());

            if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completá todos los campos.");
                return;
            }

            if (ControladorUsuarios.existeCorreo(correo)) {
                JOptionPane.showMessageDialog(this, "Este correo ya está registrado.");
                return;
            }

            Pasajero p = new Pasajero(nombre, "", "", "");
            if (ControladorUsuarios.registrarUsuario(p, correo, clave)) {
                JOptionPane.showMessageDialog(this, "¡Registro exitoso!");
                new LoginFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar.");
            }
        });
    }
}


