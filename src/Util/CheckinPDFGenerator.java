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
import java.util.Random;
/**
 *
 * @author julic
 */
public class CheckinPDFGenerator {

    public static void generar(Vuelo vuelo, Pasajero pasajero, String asiento, String metodoPago, double totalPagado) {
        try {
            File carpeta = new File("tickets_generados");
            if (!carpeta.exists()) carpeta.mkdir();

            String archivo = "tickets_generados/Checkin_" + pasajero.getNombre().replaceAll(" ", "_") + ".pdf";
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, new FileOutputStream(archivo));
            doc.open();

            // 🖌️ Estilos
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD);
            Font campoFont = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
            Font codigoFont = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

            // 🎨 Fondo
            Rectangle fondo = new Rectangle(PageSize.A4);
            fondo.setBackgroundColor(new BaseColor(240, 248, 255));
            doc.setPageSize(fondo);
            doc.newPage();

            // ✈️ Título
            Paragraph titulo = new Paragraph("🧍‍♀️ Aerolineas Mendoza – Check-in Confirmado", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph(" "));
            doc.add(new LineSeparator());

            // 🆔 Código de Reserva
            String codigo = generarCodigo(pasajero);
            Paragraph codigoR = new Paragraph("Código de Reserva: " + codigo, codigoFont);
            codigoR.setAlignment(Element.ALIGN_CENTER);
            doc.add(codigoR);
            doc.add(new Paragraph(" "));
            doc.add(new LineSeparator());
            doc.add(new Paragraph(" "));

            // 📋 Datos
            doc.add(new Paragraph("👤 Pasajero: " + pasajero.getNombre(), campoFont));
            doc.add(new Paragraph("✈️ Ruta: " + vuelo.getOrigen() + " → " + vuelo.getDestino(), campoFont));
            doc.add(new Paragraph("📅 Fecha: " + vuelo.getFecha() + "   🕒 Hora: " + vuelo.getHora(), campoFont));
            doc.add(new Paragraph("💺 Asiento: " + asiento + "   🚪 Puerta: " + generarGate(), campoFont));
            doc.add(new Paragraph("🔢 Grupo de embarque: " + generarGrupo(), campoFont));
            doc.add(new Paragraph("💳 Método de pago: " + metodoPago, campoFont));
            doc.add(new Paragraph("💰 Total abonado: $" + totalPagado, campoFont));

            doc.add(new Paragraph(" "));
            doc.add(new LineSeparator());
            doc.add(new Paragraph(" "));

            Paragraph mensaje = new Paragraph("✅ Check-in realizado correctamente. Presentate 30 minutos antes en tu puerta de embarque.", campoFont);
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            doc.close();

            File file = new File(archivo);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generarCodigo(Pasajero p) {
        String base = "CHK-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMdd"));
        int hash = Math.abs(p.getNombre().hashCode()) % 10000;
        return base + "-" + hash;
    }

    private static String generarGate() {
        int gate = new Random().nextInt(10) + 1;
        return "Puerta " + gate;
    }

    private static String generarGrupo() {
        String[] grupos = {"Grupo A", "Grupo B", "Grupo C", "Grupo Prioritario"};
        return grupos[new Random().nextInt(grupos.length)];
    }
}

