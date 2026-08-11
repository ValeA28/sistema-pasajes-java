/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author julic
 */
public class Vuelo {

    private int id;
    private String origen;
    private String destino;
    private String fecha;
    private String hora;
    private String aerolinea;
    private String duracion;
    private double precio;
    private String estado;

    public Vuelo(int id, String origen, String destino, String fecha, String hora,
                 String aerolinea, String duracion, double precio, String estado) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.aerolinea = aerolinea;
        this.duracion = duracion;
        this.precio = precio;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getAerolinea() { return aerolinea; }
    public String getDuracion() { return duracion; }
    public double getPrecio() { return precio; }
    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }

    // Nueva funcionalidad: detección de vuelo internacional
    public boolean isInternacional() {
        // Si el destino no es una ciudad argentina común, se considera internacional
        String[] destinosNacionales = {
            "Buenos Aires", "Mendoza", "Córdoba", "Salta", "Rosario", "Tucumán", "Bariloche", "Neuquén"
        };
        for (String d : destinosNacionales) {
            if (destino.equalsIgnoreCase(d)) {
                return false;
            }
        }
        return true;
    }
}
