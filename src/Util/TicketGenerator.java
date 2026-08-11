/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Modelo.Pasajero;
import Modelo.Vuelo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.awt.Desktop;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketGenerator {

    public static void generar(Vuelo vuelo, Pasajero pasajero, String asiento, String metodoPago, double totalPagado) {
        try {
            File carpeta = new File("tickets_generados");
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

            String nombreArchivo = "tickets_generados/Ticket_" + pasajero.getNombre().replaceAll(" ", "_") + ".pdf";
            Document doc = new Document(PageSize.A4); // Vertical
            PdfWriter.getInstance(doc, new FileOutputStream(nombreArchivo));
            doc.open();

            // Estilos
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD);
            Font campoFont = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
            Font codigoFont = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

            // Fondo gris claro
            Rectangle fondo = new Rectangle(PageSize.A4);
            fondo.setBackgroundColor(new BaseColor(245, 245, 245));
            doc.setPageSize(fondo);
            doc.newPage();

            // Encabezado
            Paragraph titulo = new Paragraph("✈️ Aerolineas Mendoza – Boarding Pass", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph(" ")); // Espacio

            // Código de reserva aleatorio
            String codigoReserva = generarCodigo(pasajero);
            Paragraph codigo = new Paragraph("🔢 Código de Reserva: " + codigoReserva, codigoFont);
            codigo.setAlignment(Element.ALIGN_CENTER);
            doc.add(codigo);

            doc.add(new Paragraph(" "));
            doc.add(new LineSeparator());
            doc.add(new Paragraph(" "));

            // Datos del pasajero y vuelo
            doc.add(new Paragraph("👤 Pasajero: " + pasajero.getNombre(), campoFont));
            doc.add(new Paragraph("🪪 DNI: " + pasajero.getDni(), campoFont));
            if (pasajero.getPasaporte() != null && !pasajero.getPasaporte().isEmpty()) {
                doc.add(new Paragraph("🛂 Pasaporte: " + pasajero.getPasaporte(), campoFont));
            }
            doc.add(new Paragraph("✈️ Ruta: " + vuelo.getOrigen() + " → " + vuelo.getDestino(), campoFont));
            doc.add(new Paragraph("📅 Fecha: " + vuelo.getFecha() + "   🕒 Hora: " + vuelo.getHora(), campoFont));
            doc.add(new Paragraph("💺 Asiento asignado: " + asiento, campoFont));
            doc.add(new Paragraph("💳 Método de pago: " + metodoPago, campoFont));
            doc.add(new Paragraph("💰 Total pagado: $" + totalPagado, campoFont));

            doc.add(new Paragraph(" "));
            doc.add(new LineSeparator());
            doc.add(new Paragraph(" "));

            Paragraph mensaje = new Paragraph("📎 Presentate con este ticket al embarque 30 minutos antes de la partida.", campoFont);
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            doc.close();

            // Abrir automáticamente
            File file = new File(nombreArchivo);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generarCodigo(Pasajero p) {
        String iniciales = "AM"; // Aerolineas Mendoza
        String nombre = p.getNombre().replaceAll(" ", "").toUpperCase();
        int hash = Math.abs(nombre.hashCode()) % 10000;
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMdd"));
        return iniciales + "-" + fecha + "-" + hash;
    }
}



