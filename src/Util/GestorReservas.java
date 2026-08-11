/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.time.*;
import java.time.temporal.ChronoUnit;
/**
 *
 * @author julic
 */
public class GestorReservas {

    public static boolean puedeCancelar(String fechaVuelo) {
        LocalDate vuelo = LocalDate.parse(fechaVuelo); // formato: yyyy-MM-dd
        LocalDate hoy = LocalDate.now();
        return ChronoUnit.HOURS.between(hoy.atStartOfDay(), vuelo.atStartOfDay()) > 48;
    }

    public static String cancelarReserva(String codigoReserva) {
        return "🛑 Reserva " + codigoReserva + " cancelada exitosamente (modo simulación)";
    }

    public static String cambiarReserva(String codigoReserva, String nuevoDestino) {
        return "🔄 Reserva " + codigoReserva + " modificada a destino " + nuevoDestino + " (modo simulación)";
    }
}
