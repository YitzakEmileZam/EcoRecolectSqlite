package com.example.proyecto_ecorecolect_aedii.BDSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.proyecto_ecorecolect_aedii.Entidades.Desarrolladores;

import java.util.ArrayList;

public class DAODesarrolladores {

    ConexionBDSQLite conexionBD;
    Context context;
    ArrayList<Desarrolladores> listaDesarrollador;
    SQLiteDatabase database;

    public DAODesarrolladores(Context context) {
        this.context = context;
        conexionBD = new ConexionBDSQLite(this.context);
    }

    public void openBD(){
        database=conexionBD.getWritableDatabase();
    }

    public void close(){
        conexionBD.close();
        database.close();
    }

    public SQLiteDatabase data(){
        return database;
    }

    public boolean insertDesarrollador(Desarrolladores desarrollador) {
        try {
            // Verificar si el DNI ya existe
            String queryDni = "SELECT COUNT(*) FROM DESARROLLADOR WHERE DNI_DESARROLLADOR = ?";
            String[] selectionArgsDni = { desarrollador.getDni_Des() };
            Cursor cursorDni = database.rawQuery(queryDni, selectionArgsDni);
            cursorDni.moveToFirst();
            int countDni = cursorDni.getInt(0);
            cursorDni.close();

            // Si el DNI ya existe, devolver false
            if (countDni > 0) {
                return false;
            }

            // Insertar el registro y devolver true
            ContentValues values = new ContentValues();
            values.put("DNI_DESARROLLADOR", desarrollador.getDni_Des());
            values.put("IMAGEN_DESARROLLADOR", desarrollador.getImagenDesarrollador());
            values.put("NOMBRE_DESARROLLADOR", desarrollador.getNombreDesarrollador());
            values.put("APELLIDO_PAT_DESARROLLADOR", desarrollador.getApellidoPat_Des());
            values.put("APELLIDO_MAT_DESARROLLADOR", desarrollador.getApellidoMat_Des());
            values.put("EMAIL_DESARROLLADOR", desarrollador.getEmail_Des());
            values.put("CEL_DESARROLLADOR", desarrollador.getCelular_Des());
            values.put("GENERO_DESARROLLADOR", desarrollador.getGenero_Des());
            values.put("PROFESION_DESARROLLADOR", desarrollador.getProfesion_Des());

            long result = database.insert("DESARROLLADOR", null, values);

            return result != -1;
        } catch (Exception e) {
            // Manejar la excepción
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDesarrollador(Desarrolladores desarrollador) {
        try {
            // Verificar si el correo electrónico ya existe para otro usuario
            String query = "SELECT COUNT(*) FROM DESARROLLADOR WHERE DNI_ADMIN = ?";
            String[] selectionArgs = { desarrollador.getDni_Des() };
            Cursor cursor = database.rawQuery(query, selectionArgs);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            // Si el DNI ya existe para otro desarrollador, devolver false
            if (count > 0) {
                return false;
            }

            // Si el DNI no existe para otro usuario, actualizar el registro y devolver true
            ContentValues values = new ContentValues();
            values.put("IMAGEN_DESARROLLADOR", desarrollador.getImagenDesarrollador());
            values.put("NOMBRE_DESARROLLADOR", desarrollador.getNombreDesarrollador());
            values.put("APELLIDO_PAT_DESARROLLADOR", desarrollador.getApellidoPat_Des());
            values.put("APELLIDO_MAT_DESARROLLADOR", desarrollador.getApellidoMat_Des());
            values.put("EMAIL_DESARROLLADOR", desarrollador.getEmail_Des());
            values.put("CEL_DESARROLLADOR", desarrollador.getCelular_Des());
            values.put("GENERO_DESARROLLADOR", desarrollador.getGenero_Des());
            values.put("PROFESION_DESARROLLADOR", desarrollador.getProfesion_Des());

            String whereClause = "DNI_DESARROLLADOR = ?";
            String[] whereArgs = { desarrollador.getDni_Des() };

            int result = database.update("DESARROLLADOR", values, whereClause, whereArgs);

            if (result > 0) {
                Toast.makeText(context, "REGISTRO ACTUALIZADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "NO SE PUDO ACTUALIZAR EL REGISTRO", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public ArrayList<Desarrolladores> selectDesarrolladores() {
        ArrayList<Desarrolladores> listaDesarrollador = new ArrayList<>();
        listaDesarrollador.clear();
        try {
            String[] columns = {
                    "DNI_DESARROLLADOR",
                    "IMAGEN_DESARROLLADOR",
                    "NOMBRE_DESARROLLADOR",
                    "APELLIDO_PAT_DESARROLLADOR",
                    "APELLIDO_MAT_DESARROLLADOR",
                    "EMAIL_DESARROLLADOR",
                    "CEL_DESARROLLADOR",
                    "GENERO_DESARROLLADOR",
                    "PROFESION_DESARROLLADOR"
            };
            Cursor cursor = database.query("DESARROLLADOR", columns, null, null, null, null, null);

            int columnIndexDni = cursor.getColumnIndex("DNI_DESARROLLADOR");
            int columnIndexImagen = cursor.getColumnIndex("IMAGEN_DESARROLLADOR");
            int columnIndexNombre = cursor.getColumnIndex("NOMBRE_DESARROLLADOR");
            int columnIndexApellidoPat = cursor.getColumnIndex("APELLIDO_PAT_DESARROLLADOR");
            int columnIndexApellidoMat = cursor.getColumnIndex("APELLIDO_MAT_DESARROLLADOR");
            int columnIndexEmail = cursor.getColumnIndex("EMAIL_DESARROLLADOR");
            int columnIndexCelular = cursor.getColumnIndex("CEL_DESARROLLADOR");
            int columnIndexGenero = cursor.getColumnIndex("GENERO_DESARROLLADOR");
            int columnIndexProfesion = cursor.getColumnIndex("PROFESION_DESARROLLADOR");

            while (cursor.moveToNext()) {
                Desarrolladores desarrollador = new Desarrolladores();
                desarrollador.setDni_Des(cursor.getString(cursor.getColumnIndexOrThrow("DNI_DESARROLLADOR")));  // 1
                desarrollador.setImagenDesarrollador(cursor.getInt(cursor.getColumnIndexOrThrow("IMAGEN_DESARROLLADOR")));   // 2
                desarrollador.setNombreDesarrollador(cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_DESARROLLADOR")));  // 3
                desarrollador.setApellidoPat_Des(cursor.getString(cursor.getColumnIndexOrThrow("APELLIDO_PAT_DESARROLLADOR")));  // 4
                desarrollador.setApellidoMat_Des(cursor.getString(cursor.getColumnIndexOrThrow("APELLIDO_MAT_DESARROLLADOR")));  // 5
                desarrollador.setEmail_Des(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL_DESARROLLADOR")));   // 6
                desarrollador.setCelular_Des(cursor.getString(cursor.getColumnIndexOrThrow("CEL_DESARROLLADOR")));        // 7
                desarrollador.setGenero_Des(cursor.getString(cursor.getColumnIndexOrThrow("GENERO_DESARROLLADOR")));      // 8
                desarrollador.setProfesion_Des(cursor.getString(cursor.getColumnIndexOrThrow("PROFESION_DESARROLLADOR")));    // 9
                listaDesarrollador.add(desarrollador);
            }

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
        return listaDesarrollador;
    }

    public Desarrolladores getDesarrolladorById(String dniDesarrollador) {
        listaDesarrollador = selectDesarrolladores();
        for(Desarrolladores us:listaDesarrollador){
            if(us.getDni_Des().equals(dniDesarrollador)){
                return us;
            }
        }
        return null;
    }

    public boolean deleteDesarrollador(String dniDesarrollador) {
        try {
            // Verificar si el desarrollador existe en la base de datos
            String query = "SELECT COUNT(*) FROM DESARROLLADOR WHERE DNI_DESARROLLADOR = ?";
            String[] selectionArgs = { String.valueOf(dniDesarrollador) };
            Cursor cursor = database.rawQuery(query, selectionArgs);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            // Si el desarrollador no existe en la base de datos, devolver false
            if (count == 0) {
                return false;
            }

            // Si el desarrollador existe en la base de datos, eliminar el registro y devolver true
            String whereClause = "DNI_DESARROLLADOR = ?";
            String[] whereArgs = { String.valueOf(dniDesarrollador) };
            int result = database.delete("DESARROLLADOR", whereClause, whereArgs);

            if (result > 0) {
                Toast.makeText(context, "REGISTRO ELIMINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "NO SE PUDO ELIMINAR EL REGISTRO", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
