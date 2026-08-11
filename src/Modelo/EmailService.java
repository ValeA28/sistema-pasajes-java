/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
/**
 *
 * @author julic
 */
/**
 * Simulación del servicio de envío de correos.
 * No envía correos reales, solo muestra mensajes por consola.
 */
public class EmailService {
    public static void enviarCorreo(String destino, String asunto, String cuerpo) {
        System.out.println("📧 Simulando envío de correo...");
        System.out.println("Para: " + destino);
        System.out.println("Asunto: " + asunto);
        System.out.println("Contenido:\n" + cuerpo);
        System.out.println("✅ Simulación completada.");
    }
}
