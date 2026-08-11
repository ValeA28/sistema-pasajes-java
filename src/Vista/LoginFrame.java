/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Vista;

import Controlador.ControladorUsuarios;
import Modelo.Pasajero;
import Modelo.SesionUsuario;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Sistema de Pasajes");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        FondoPanel fondo = new FondoPanel("fondo.jpg", null);
        setContentPane(fondo);
        fondo.setLayout(null);

        JLabel correoLabel = new JLabel("Correo electrónico:");
        correoLabel.setBounds(50, 30, 150, 25);
        correoLabel.setForeground(Color.WHITE);
        fondo.add(correoLabel);

        JTextField correoField = new JTextField();
        correoField.setBounds(180, 30, 150, 25);
        fondo.add(correoField);

        JLabel contraseñaLabel = new JLabel("Contraseña:");
        contraseñaLabel.setBounds(50, 70, 150, 25);
        contraseñaLabel.setForeground(Color.WHITE);
        fondo.add(contraseñaLabel);

        JPasswordField contraseñaField = new JPasswordField();
        contraseñaField.setBounds(180, 70, 150, 25);
        fondo.add(contraseñaField);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setBounds(50, 120, 130, 30);
        fondo.add(loginButton);

        JButton registerButton = new JButton("Registrarse");
        registerButton.setBounds(200, 120, 130, 30);
        fondo.add(registerButton);

        JButton forgotButton = new JButton("¿Olvidaste tu contraseña?");
        forgotButton.setBounds(100, 170, 200, 30);
        fondo.add(forgotButton);

        loginButton.addActionListener(e -> {
            String correo = correoField.getText();
            String clave = new String(contraseñaField.getPassword());

            if (ControladorUsuarios.verificarLogin(correo, clave)) {
                Pasajero p = ControladorUsuarios.obtenerUsuarioPorCorreo(correo);
                SesionUsuario.iniciarSesion(p);

                // Colores armónicos y sobrios
                UIManager.put("Panel.background", new Color(195, 215, 245)); // Azul más oscuro
                UIManager.put("OptionPane.messageForeground", new Color(25, 25, 25));
                UIManager.put("Button.background", new Color(180, 200, 230));
                UIManager.put("Button.foreground", Color.BLACK);

                JOptionPane.showMessageDialog(this, "¡Inicio de sesión exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);

                new MenuPrincipalFrame(p).setVisible(true);
                dispose();
            } else {
                UIManager.put("Panel.background", new Color(255, 225, 225)); // fondo rosado claro
                UIManager.put("OptionPane.messageForeground", new Color(120, 0, 0));
                UIManager.put("Button.background", new Color(250, 190, 190));
                UIManager.put("Button.foreground", Color.BLACK);

                JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            new RegistroFrame().setVisible(true);
            dispose();
        });

        forgotButton.addActionListener(e -> {
            new RecuperarClaveFrame().setVisible(true);
            dispose();
        });
    }
}



