
package Vista;

import Controlador.ControladorUsuarios;

import javax.swing.*;
import java.awt.*;

public class RecuperarClaveFrame extends JFrame {

    public RecuperarClaveFrame() {
        setTitle("Recuperar Contraseña");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        FondoPanel fondo = new FondoPanel("/Vista/imagenes/ticket_fondo.jpg");
        setContentPane(fondo);
        fondo.setLayout(null);

        JLabel correoLabel = new JLabel("Correo registrado:");
        correoLabel.setBounds(40, 40, 120, 25);
        correoLabel.setForeground(Color.WHITE);
        fondo.add(correoLabel);

        JTextField correoField = new JTextField();
        correoField.setBounds(170, 40, 180, 25);
        fondo.add(correoField);

        JButton recuperarBtn = new JButton("Recuperar");
        recuperarBtn.setBounds(130, 90, 120, 30);
        fondo.add(recuperarBtn);

        JButton volverBtn = new JButton("Volver");
        volverBtn.setBounds(130, 130, 120, 30);
        fondo.add(volverBtn);

        // Acción para recuperar contraseña
        recuperarBtn.addActionListener(e -> {
            String correo = correoField.getText().trim();
            if (correo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresá un correo.");
                return;
            }

            if (ControladorUsuarios.existeCorreo(correo)) {
                JOptionPane.showMessageDialog(this, "📧 Mensaje enviado a su correo electrónico");
            } else {
                JOptionPane.showMessageDialog(this, "❌ El correo no está registrado.");
            }
        });

        // Acción para volver
        volverBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        // Imagen decorativa (manteniendo la anterior)
        ImageIcon icon = new ImageIcon(getClass().getResource("/Vista/imagenes/recuperar.png"));
        JLabel imagen = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        imagen.setBounds(310, 140, 60, 60);
        fondo.add(imagen);
    }

    // Clase interna para fondo con imagen y oscurecimiento
    private static class FondoPanel extends JPanel {
        private Image imagen;

        public FondoPanel(String ruta) {
            imagen = new ImageIcon(getClass().getResource(ruta)).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 100)); // Oscurecimiento leve
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }
}

