/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 *
 * @author julic
 */

public class GestorReservas {

    public static double calcularTotal(Vuelo vuelo, int cantidad) {
        return vuelo.getPrecio() * cantidad;
    }

    public static boolean vueloConEstadoValido(Vuelo vuelo) {
        String estado = vuelo.getEstado();
        return estado != null && (estado.equalsIgnoreCase("A tiempo") || estado.equalsIgnoreCase("Disponible"));
    }

    public static String generarResumen(Vuelo vuelo, int cantidad) {
        return "✈️ " + cantidad + " pasajero(s) → " + vuelo.getOrigen() + " → " + vuelo.getDestino()
               + " (" + vuelo.getFecha() + " " + vuelo.getHora() + ") | $" + calcularTotal(vuelo, cantidad);
    }
}


