/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.ControladorUsuarios;
import Modelo.Pasajero;
import Modelo.SesionUsuario;

import javax.swing.*;
/**
 *
 * @author julic
 */
public class InicioSesionFrame extends JFrame {

    public InicioSesionFrame() {
        setTitle("Iniciar sesión");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel correoLabel = new JLabel("Correo:");
        correoLabel.setBounds(50, 30, 100, 25);
        add(correoLabel);

        JTextField correoField = new JTextField();
        correoField.setBounds(150, 30, 180, 25);
        add(correoField);

        JLabel contraseñaLabel = new JLabel("Contraseña:");
        contraseñaLabel.setBounds(50, 70, 100, 25);
        add(contraseñaLabel);

        JPasswordField contraseñaField = new JPasswordField();
        contraseñaField.setBounds(150, 70, 180, 25);
        add(contraseñaField);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setBounds(130, 120, 130, 30);
        add(loginButton);

        loginButton.addActionListener(e -> {
            String correo = correoField.getText().trim();
            String contraseña = new String(contraseñaField.getPassword());

            if (ControladorUsuarios.verificarLogin(correo, contraseña)) {
                Pasajero usuario = ControladorUsuarios.obtenerUsuarioPorCorreo(correo);
                if (usuario != null) {
                    SesionUsuario.iniciarSesion(usuario);
                    JOptionPane.showMessageDialog(this, "✅ Sesión iniciada correctamente");
                    new SeleccionarVueloFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Error al cargar usuario");
                }
            } else {
                JOptionPane.showMessageDialog(this, "❌ Correo o contraseña incorrectos");
            }
        });
    }
}

