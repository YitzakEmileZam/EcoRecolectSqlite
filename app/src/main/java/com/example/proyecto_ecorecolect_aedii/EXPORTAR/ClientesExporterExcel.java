package com.example.proyecto_ecorecolect_aedii.EXPORTAR;

import android.content.Context;

import com.example.proyecto_ecorecolect_aedii.Entidades.Cliente;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ClientesExporterExcel {
    private ArrayList<Cliente> listaClientes;
    private Workbook libro;

    public ClientesExporterExcel(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
        libro = new XSSFWorkbook();
    }

    private void escribirCabeceraDeTabla() {
        Sheet hoja = libro.createSheet("Clientes");
        Row fila = hoja.createRow(0);

        CellStyle estilo = libro.createCellStyle();
        Font fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeightInPoints((short) 16);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("ID");
        celda.setCellStyle(estilo);

        celda = fila.createCell(1);
        celda.setCellValue("Nombre");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Apellido");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Email");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("Fecha");
        celda.setCellStyle(estilo);

        celda = fila.createCell(5);
        celda.setCellValue("Telefono");
        celda.setCellStyle(estilo);

        celda = fila.createCell(6);
        celda.setCellValue("Sexo");
        celda.setCellStyle(estilo);

        celda = fila.createCell(7);
        celda.setCellValue("Dni");
        celda.setCellStyle(estilo);
    }

    private void escribirDatosDeLaTabla() {
        Sheet hoja = libro.getSheet("Clientes");

        CellStyle estilo = libro.createCellStyle();
        Font fuente = libro.createFont();
        fuente.setFontHeightInPoints((short) 14);
        estilo.setFont(fuente);

        int numeroFilas = 1;

        for (Cliente cliente : listaClientes) {
            Row fila = hoja.createRow(numeroFilas++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(cliente.getId());
            //hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(cliente.getNombres());
            //hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(cliente.getApellidos());
            //hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(cliente.getEmail());
            //hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(cliente.getFecha_registro().toString());
            //hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(cliente.getCel());
            //hoja.autoSizeColumn(5);
            celda.setCellStyle(estilo);

            celda = fila.createCell(6);
            celda.setCellValue(cliente.getGenero());
            //hoja.autoSizeColumn(6);
            celda.setCellStyle(estilo);

            celda = fila.createCell(7);
            celda.setCellValue(cliente.getDni());
            //hoja.autoSizeColumn(7);
            celda.setCellStyle(estilo);
        }

        // Ajustar el ancho de las columnas manualmente
        for (int i = 0; i < 8; i++) {
            hoja.setColumnWidth(i, 15 * 256); // Ancho de columna en unidades de 1/256 de carácter
        }
    }

    /* AL EXPORTAR EN EXCEL SE GUARDARA EN
    DEVICE FILE EXPLORER --> storage --> self
     --> primary --> Android --> data
     --> com.usmp.fia.proyecto.personal
     --> files --> nombre del archivo excel en este caso es
     --> clientes.xlsx
    */
    public void exportar(Context context, String fileName) throws IOException {
        escribirCabeceraDeTabla();
        escribirDatosDeLaTabla();

        // Guardar el archivo en el almacenamiento externo
        File file = new File(context.getExternalFilesDir(null), fileName);
        FileOutputStream outputStream = new FileOutputStream(file);
        libro.write(outputStream);
        libro.close();
        outputStream.flush();
        outputStream.close();
    }
}
