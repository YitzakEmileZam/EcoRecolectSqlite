package com.example.proyecto_ecorecolect_aedii.EXPORTAR;

import android.os.Environment;

import com.example.proyecto_ecorecolect_aedii.Entidades.Servicios;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ServiciosExporterPDF {
    private ArrayList<Servicios> listaServicios;

    public ServiciosExporterPDF(ArrayList<Servicios> listaServicios) {
        this.listaServicios = listaServicios;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(BaseColor.BLUE);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(BaseColor.WHITE);

        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Nombre", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Descripcion", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Imagen", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Precio", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fec. Reg.", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fec. Actualiz.", fuente));
        tabla.addCell(celda);

    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for (Servicios servicios : listaServicios) {
            tabla.addCell(String.valueOf(servicios.getId()));
            tabla.addCell(servicios.getNombreServicio());
            tabla.addCell(servicios.getDescripcion());
            tabla.addCell(servicios.getImagen());
            tabla.addCell(String.valueOf(servicios.getPrecio()));
            tabla.addCell(servicios.getFecha_registro().toString());
            tabla.addCell(servicios.getFecha_actualizacion().toString());

        }
    }

     /* AL EXPORTAR EN PDF SE GUARDARA EN
    DEVICE FILE EXPLORER --> storage --> emulated
     --> 0 --> nombre del archivo PDF en este caso es
     --> exportServicios.pdf
    */

    public void exportar() throws DocumentException, IOException {
        String pdfFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "export.pdf";
        File pdfFile = new File(pdfFilePath);
        FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);

        Document documento = new Document();
        PdfWriter.getInstance(documento, fileOutputStream);

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(BaseColor.BLUE); // el color del titulo de lista de clientes que aparece en el pdf
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de Servicios", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(7); // Cambia el número de columnas según tus necesidades
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] { 1f, 2.8f, 2.8f, 3f, 2.2f, 2.8f, 2.8f }); // Cambia los pesos de las columnas según tus necesidades
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }
}
