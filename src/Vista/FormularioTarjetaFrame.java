/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Vista;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class FormularioTarjetaFrame extends JFrame {

    public FormularioTarjetaFrame(Consumer<TarjetaDatos> callback) {
        setTitle("💳 Datos de Tarjeta");
        setSize(440, 390);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        FondoPanel fondo = new FondoPanel("/Vista/imagenes/ticket_fondo.jpg");
        setContentPane(fondo);
        fondo.setLayout(null);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        JLabel iconTarjeta = new JLabel("• Forma de pago: Tarjeta");
        iconTarjeta.setFont(new Font("Segoe UI", Font.BOLD, 15));
        iconTarjeta.setBounds(30, 15, 300, 25);
        iconTarjeta.setForeground(Color.WHITE);
        fondo.add(iconTarjeta);

        JLabel titularLbl = new JLabel("• Titular*:");
        titularLbl.setBounds(30, 50, 100, 25);
        titularLbl.setFont(labelFont);
        titularLbl.setForeground(Color.WHITE);
        JTextField titularField = new JTextField();
        titularField.setFont(fieldFont);
        titularField.setBounds(150, 50, 230, 25);

        JLabel numeroLbl = new JLabel("• Nº Tarjeta*:");
        numeroLbl.setBounds(30, 90, 100, 25);
        numeroLbl.setFont(labelFont);
        numeroLbl.setForeground(Color.WHITE);
        JTextField numeroField = new JTextField();
        numeroField.setFont(fieldFont);
        numeroField.setBounds(150, 90, 230, 25);

        JLabel vencimientoLbl = new JLabel("• Vencimiento (MM/AA)*:");
        vencimientoLbl.setBounds(30, 130, 180, 25);
        vencimientoLbl.setFont(labelFont);
        vencimientoLbl.setForeground(Color.WHITE);
        JTextField vencimientoField = new JTextField();
        vencimientoField.setFont(fieldFont);
        vencimientoField.setBounds(190, 130, 80, 25);

        JLabel cvvLbl = new JLabel("• CVV*:");
        cvvLbl.setBounds(30, 170, 100, 25);
        cvvLbl.setFont(labelFont);
        cvvLbl.setForeground(Color.WHITE);
        JTextField cvvField = new JTextField();
        cvvField.setFont(fieldFont);
        cvvField.setBounds(150, 170, 80, 25);

        JLabel logosLbl = new JLabel("Aceptamos: VISA · MasterCard · AmEx · CABAL · Naranja");
        logosLbl.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        logosLbl.setForeground(new Color(220, 220, 220));
        logosLbl.setBounds(30, 210, 360, 20);
        fondo.add(logosLbl);

        ImageIcon icono = new ImageIcon("src/img/logos_tarjetas.png");
        JLabel iconoLabel = new JLabel();
        iconoLabel.setIcon(icono);
        iconoLabel.setBounds(30, 230, 300, 40);
        fondo.add(iconoLabel);

        JButton confirmar = new JButton("Confirmar pago");
        confirmar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        confirmar.setBounds(125, 290, 170, 35);
        confirmar.setBackground(new Color(0, 120, 215));
        confirmar.setForeground(Color.WHITE);
        confirmar.setFocusPainted(false);
        confirmar.addActionListener(e -> {
            String titular = titularField.getText().trim();
            String numero = numeroField.getText().trim();
            String vencimiento = vencimientoField.getText().trim();
            String cvv = cvvField.getText().trim();

            if (titular.isEmpty() || numero.isEmpty() || vencimiento.isEmpty() || cvv.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Completá todos los campos.");
                return;
            }

            callback.accept(new TarjetaDatos(titular, numero, vencimiento, cvv));
            dispose();
        });

        fondo.add(titularLbl); fondo.add(titularField);
        fondo.add(numeroLbl); fondo.add(numeroField);
        fondo.add(vencimientoLbl); fondo.add(vencimientoField);
        fondo.add(cvvLbl); fondo.add(cvvField);
        fondo.add(confirmar);
    }

    public static class TarjetaDatos {
        public final String titular, numero, vencimiento, cvv;

        public TarjetaDatos(String titular, String numero, String vencimiento, String cvv) {
            this.titular = titular;
            this.numero = numero;
            this.vencimiento = vencimiento;
            this.cvv = cvv;
        }
    }

    // FondoPanel con oscurecimiento más fuerte
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
                g2d.setColor(new Color(0, 0, 0, 160)); // más oscuro (antes 100)
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }
}




