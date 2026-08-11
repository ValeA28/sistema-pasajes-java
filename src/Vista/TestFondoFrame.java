/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import javax.swing.*;
/**
 *
 * @author julic
 */
public class TestFondoFrame extends JFrame {

    public TestFondoFrame() {
        setTitle("Prueba de Imagen Decorativa");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Imagen decorativa en esquina, sin fondo principal
        setContentPane(new FondoPanel(null, "login.png"));

        setVisible(true);
    }

    public static void main(String[] args) {
        new TestFondoFrame();
    }
}



