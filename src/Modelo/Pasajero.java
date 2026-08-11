/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author julic
 */
public class Pasajero {
    private int id;
    private String nombre;
    private String fechaNacimiento;
    private String dni;
    private String pasaporte;

    // Constructor completo
    public Pasajero(int id, String nombre, String fechaNacimiento, String dni, String pasaporte) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.dni = dni;
        this.pasaporte = pasaporte;
    }

    // Constructor sin ID
    public Pasajero(String nombre, String fechaNacimiento, String dni, String pasaporte) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.dni = dni;
        this.pasaporte = pasaporte;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getPasaporte() { return pasaporte; }
    public void setPasaporte(String pasaporte) { this.pasaporte = pasaporte; }
}
