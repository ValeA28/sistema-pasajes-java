/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author julic
 */
public class FondoPanel extends JPanel {
    private Image imagenFondo;
    private Image imagenDecorativa;

    public FondoPanel(String fondoPrincipal, String decorativo) {
        if (fondoPrincipal != null) {
            imagenFondo = new ImageIcon(getClass().getResource("/Vista/imagenes/" + fondoPrincipal)).getImage();
        }
        if (decorativo != null) {
            imagenDecorativa = new ImageIcon(getClass().getResource("/Vista/imagenes/" + decorativo)).getImage()
                .getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
        if (imagenDecorativa != null) {
            int x = getWidth() - 70;
            int y = getHeight() - 70;
            g.drawImage(imagenDecorativa, x, y, this);
        }
    }
}
