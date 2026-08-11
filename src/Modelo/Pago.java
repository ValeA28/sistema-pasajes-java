/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author julic
 */
public class Pago {
    private String tarjeta;
    private double monto;

    public Pago(String tarjeta, double monto) {
        this.tarjeta = tarjeta;
        this.monto = monto;
    }

    public boolean procesar() {
        return tarjeta.length() == 16 && tarjeta.matches("\\d+");
    }
}
