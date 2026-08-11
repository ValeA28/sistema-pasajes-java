/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Modelo.Vuelo;
import Modelo.Pasajero;
import java.util.ArrayList;

/**
 *
 * @author julic
 */
public class ContextoReservas {
    private static Vuelo vueloSeleccionado;
    private static ArrayList<Pasajero> pasajeros;

    public static void setVueloSeleccionado(Vuelo v) {
        vueloSeleccionado = v;
    }

    public static Vuelo getVueloSeleccionado() {
        return vueloSeleccionado;
    }

    public static void setPasajeros(ArrayList<Pasajero> p) {
        pasajeros = p;
    }

    public static ArrayList<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public static void limpiar() {
        vueloSeleccionado = null;
        if (pasajeros != null) pasajeros.clear();
    }
}

