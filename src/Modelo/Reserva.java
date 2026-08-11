/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author julic
 */
public class Reserva {

    private int id;
    private int vueloId;
    private int pasajeroId;
    private String asiento;
    private String estado;
    private String metodoPago;
    private double totalPagado;

    public Reserva(int id, int vueloId, int pasajeroId, String asiento, String estado, String metodoPago, double totalPagado) {
        this.id = id;
        this.vueloId = vueloId;
        this.pasajeroId = pasajeroId;
        this.asiento = asiento;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.totalPagado = totalPagado;
    }

    public int getId() { return id; }
    public int getVueloId() { return vueloId; } // ✅ Este es el que necesitás
    public int getPasajeroId() { return pasajeroId; }
    public String getAsiento() { return asiento; }
    public String getEstado() { return estado; }
    public String getMetodoPago() { return metodoPago; }
    public double getTotalPagado() { return totalPagado; }

    public void setAsiento(String asiento) { this.asiento = asiento; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public void setTotalPagado(double totalPagado) { this.totalPagado = totalPagado; }

    public int getIdVuelo() {
    return vueloId;
    }

}

