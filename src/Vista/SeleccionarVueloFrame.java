/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Vuelo;
import Modelo.VueloDAO;
import Modelo.SesionUsuario;
import Modelo.Pasajero;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class SeleccionarVueloFrame extends JFrame {
    private JTable tabla;
    private JTextField cantidadPasajerosField;

    public SeleccionarVueloFrame() {
        setTitle("Seleccionar Vuelo");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        FondoPanel fondo = new FondoPanel("/Vista/imagenes/ticket_fondo.jpg");
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);

        // Título con fondo oscuro
        JPanel tituloPanel = new JPanel();
        tituloPanel.setBackground(new Color(0, 0, 0, 180));
        tituloPanel.setPreferredSize(new Dimension(800, 40));

        JLabel titulo = new JLabel("Vuelos Disponibles", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        tituloPanel.add(titulo);
        fondo.add(tituloPanel, BorderLayout.NORTH);

        tabla = new JTable();
        tabla.setOpaque(false);
        tabla.setBackground(new Color(255, 255, 255, 200));
        tabla.setForeground(Color.BLACK);
        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setBackground(new Color(0, 0, 0, 180));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        fondo.add(scrollPane, BorderLayout.CENTER);

        JPanel abajoPanel = new JPanel(new FlowLayout());
        abajoPanel.setOpaque(false);

        JLabel cantidadLabel = new JLabel("Cantidad de pasajeros:");
        cantidadLabel.setForeground(Color.WHITE);
        cantidadPasajerosField = new JTextField(3);

        JButton seleccionarBtn = new JButton("Seleccionar vuelo");
        seleccionarBtn.addActionListener(e -> seleccionarVuelo());

        JButton volverBtn = new JButton("⬅️ Volver");
        volverBtn.addActionListener(e -> {
            dispose();
            Pasajero pasajero = SesionUsuario.getUsuarioActual();
            if (pasajero != null) {
                new MenuPrincipalFrame(pasajero).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo volver: sesión no iniciada.");
            }
        });

        abajoPanel.add(cantidadLabel);
        abajoPanel.add(cantidadPasajerosField);
        abajoPanel.add(seleccionarBtn);
        abajoPanel.add(volverBtn);
        fondo.add(abajoPanel, BorderLayout.SOUTH);

        cargarVuelos();
    }

    private void cargarVuelos() {
        VueloDAO dao = new VueloDAO();
        List<Vuelo> lista = dao.obtenerVuelos();

        String[] columnas = {"Origen", "Destino", "Fecha", "Hora", "Aerolínea", "Duración", "Precio"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Vuelo v : lista) {
            modelo.addRow(new Object[]{
                    v.getOrigen(), v.getDestino(), v.getFecha(), v.getHora(),
                    v.getAerolinea(), v.getDuracion(), "$" + v.getPrecio()
            });
        }

        tabla.setModel(modelo);
    }

    private void seleccionarVuelo() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un vuelo de la tabla.");
            return;
        }

        String cantidadStr = cantidadPasajerosField.getText().trim();
        if (!cantidadStr.matches("\\d+") || Integer.parseInt(cantidadStr) < 1) {
            JOptionPane.showMessageDialog(this, "Ingresá una cantidad válida de pasajeros.");
            return;
        }

        int cantidad = Integer.parseInt(cantidadStr);

        VueloDAO dao = new VueloDAO();
        Vuelo vueloSeleccionado = dao.obtenerVuelos().get(fila);

        new DatosPasajeroFrame(vueloSeleccionado, cantidad).setVisible(true);
        dispose();
    }

    // Fondo con imagen atenuada
    static class FondoPanel extends JPanel {
        private final Image imagen;

        public FondoPanel(String ruta) {
            Image original = null;
            try {
                original = ImageIO.read(getClass().getResource(ruta));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (original != null) {
                BufferedImage buffered = new BufferedImage(
                        original.getWidth(null),
                        original.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2 = buffered.createGraphics();
                g2.drawImage(original, 0, 0, null);
                g2.setColor(new Color(0, 0, 0, 100)); // oscurecer
                g2.fillRect(0, 0, buffered.getWidth(), buffered.getHeight());
                g2.dispose();
                this.imagen = buffered;
            } else {
                this.imagen = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
