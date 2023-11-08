package com.example.proyecto_ecorecolect_aedii.BDSQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.proyecto_ecorecolect_aedii.Entidades.Administrador;

import java.util.ArrayList;

public class DAOAdministrador {

    ConexionBDSQLite conexionBD;
    Context context;
    ArrayList<Administrador> listaADM;
    SQLiteDatabase database;

    public DAOAdministrador(Context context) {
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

    public boolean updateAdministrador(Administrador admin) {
        try {
            // Verificar si el correo electrónico ya existe para otro usuario
            String query = "SELECT COUNT(*) FROM ADMINISTRADOR WHERE DNI_ADMIN = ? AND ID_ADMIN != ?";
            String[] selectionArgs = { admin.getDni(), String.valueOf(admin.getId()) };
            Cursor cursor = database.rawQuery(query, selectionArgs);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            // Si el dni ya existe para otro administrador, devolver false
            if (count > 0) {
                return false;
            }

            // Si el dni no existe para otro administrador, actualizar el registro y devolver true
            String whereClause = "ID_ADMIN = ?";
            String[] whereArgs = { String.valueOf(admin.getId()) };
            ContentValues values = new ContentValues();
            values.put("IMAGEN_ADM", admin.getIdFoto());
            values.put("NOMBRE_ADMIN", admin.getNombres());
            values.put("APELLIDO_ADMIN", admin.getApellidos());
            values.put("DNI_ADMIN", admin.getDni());
            values.put("EMAIL_ADMIN", admin.getEmail());
            values.put("CEL_ADMIN", admin.getCel());
            values.put("GENERO_ADMIN", admin.getGenero());
            values.put("CONTRA_ADMIN", admin.getPassword());
            values.put("RECONTRA_ADMIN", admin.getRepassword());
            values.put("NIVEL_ACCESO_ADMIN", admin.getNivelAcceso());
            values.put("SALARIO_ADMIN", admin.getSalario());

            int result = database.update("ADMINISTRADOR", values, whereClause, whereArgs);

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

    public boolean insertAdministrador(Administrador admin) {
        try {
            // Verificar si el DNI ya existe
            String dniQuery = "SELECT COUNT(*) FROM ADMINISTRADOR WHERE DNI_ADMIN = ?";
            String[] dniSelectionArgs = { admin.getDni() };
            Cursor dniCursor = database.rawQuery(dniQuery, dniSelectionArgs);
            dniCursor.moveToFirst();
            int countDni = dniCursor.getInt(0);
            dniCursor.close();

            // Verificar si el ID ya existe
            String idQuery = "SELECT COUNT(*) FROM ADMINISTRADOR WHERE ID_ADMIN = ?";
            String[] idSelectionArgs = { String.valueOf(admin.getId()) };
            Cursor idCursor = database.rawQuery(idQuery, idSelectionArgs);
            idCursor.moveToFirst();
            int countId = idCursor.getInt(0);
            idCursor.close();

            // Si el DNI o el ID ya existen, devolver false
            if (countDni > 0 || countId > 0) {
                return false;
            }

            // Insertar el registro y devolver true
            ContentValues values = new ContentValues();
            values.put("ID_ADMIN", admin.getId());
            values.put("IMAGEN_ADM", admin.getIdFoto());
            values.put("NOMBRE_ADMIN", admin.getNombres());
            values.put("APELLIDO_ADMIN", admin.getApellidos());
            values.put("DNI_ADMIN", admin.getDni());
            values.put("EMAIL_ADMIN", admin.getEmail());
            values.put("CEL_ADMIN", admin.getCel());
            values.put("GENERO_ADMIN", admin.getGenero());
            values.put("CONTRA_ADMIN", admin.getPassword());
            values.put("RECONTRA_ADMIN", admin.getRepassword());
            values.put("NIVEL_ACCESO_ADMIN", admin.getNivelAcceso());
            values.put("SALARIO_ADMIN", admin.getSalario());

            long result = database.insert("ADMINISTRADOR", null, values);

            return result != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Administrador> selecAdministradors() {
        ArrayList<Administrador> listaAdmin = new ArrayList<>();
        try {
            String query = "SELECT ID_ADMIN, IMAGEN_ADM, NOMBRE_ADMIN, APELLIDO_ADMIN, DNI_ADMIN, EMAIL_ADMIN, CEL_ADMIN, " +
                    "GENERO_ADMIN, CONTRA_ADMIN, RECONTRA_ADMIN, NIVEL_ACCESO_ADMIN, SALARIO_ADMIN FROM ADMINISTRADOR";
            Cursor cursor = database.rawQuery(query, null);

            while (cursor.moveToNext()) {
                Administrador administrador = new Administrador();
                administrador.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID_ADMIN")));  // 1
                administrador.setIdFoto(cursor.getInt(cursor.getColumnIndexOrThrow("IMAGEN_ADM")));  // 2
                administrador.setNombres(cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_ADMIN")));  // 3
                administrador.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow("APELLIDO_ADMIN")));  // 4
                administrador.setDni(cursor.getString(cursor.getColumnIndexOrThrow("DNI_ADMIN")));  // 5
                administrador.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL_ADMIN")));  // 6
                administrador.setCel(cursor.getString(cursor.getColumnIndexOrThrow("CEL_ADMIN")));  // 7
                administrador.setGenero(cursor.getString(cursor.getColumnIndexOrThrow("GENERO_ADMIN")));  // 8
                administrador.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("CONTRA_ADMIN")));  // 9
                administrador.setRepassword(cursor.getString(cursor.getColumnIndexOrThrow("RECONTRA_ADMIN")));  // 10
                administrador.setNivelAcceso(cursor.getString(cursor.getColumnIndexOrThrow("NIVEL_ACCESO_ADMIN")));  // 11
                administrador.setSalario(cursor.getDouble(cursor.getColumnIndexOrThrow("SALARIO_ADMIN")));  // 12
                listaAdmin.add(administrador);
            }

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this.context, "mala conexion", Toast.LENGTH_SHORT).show();
        }
        return listaAdmin;
    }

    public ArrayList<Administrador> selecAdministradorsPorGenero(String genero) {
        ArrayList<Administrador> listaAdmin = new ArrayList<>();
        try {
            String query = "SELECT ID_ADMIN, IMAGEN_ADM, NOMBRE_ADMIN, APELLIDO_ADMIN, DNI_ADMIN, EMAIL_ADMIN, CEL_ADMIN, " +
                    "GENERO_ADMIN, CONTRA_ADMIN, RECONTRA_ADMIN, NIVEL_ACCESO_ADMIN, SALARIO_ADMIN " +
                    "FROM ADMINISTRADOR WHERE GENERO_ADMIN = ?";
            Cursor cursor = database.rawQuery(query, new String[]{genero});

            while (cursor.moveToNext()) {
                Administrador administrador = new Administrador();
                administrador.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID_ADMIN")));  // 1
                administrador.setIdFoto(cursor.getInt(cursor.getColumnIndexOrThrow("IMAGEN_ADM")));  // 2
                administrador.setNombres(cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_ADMIN")));  // 3
                administrador.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow("APELLIDO_ADMIN")));  // 4
                administrador.setDni(cursor.getString(cursor.getColumnIndexOrThrow("DNI_ADMIN")));  // 5
                administrador.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL_ADMIN")));  // 6
                administrador.setCel(cursor.getString(cursor.getColumnIndexOrThrow("CEL_ADMIN")));  // 7
                administrador.setGenero(cursor.getString(cursor.getColumnIndexOrThrow("GENERO_ADMIN")));  // 8
                administrador.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("CONTRA_ADMIN")));  // 9
                administrador.setRepassword(cursor.getString(cursor.getColumnIndexOrThrow("RECONTRA_ADMIN")));  // 10
                administrador.setNivelAcceso(cursor.getString(cursor.getColumnIndexOrThrow("NIVEL_ACCESO_ADMIN")));  // 11
                administrador.setSalario(cursor.getDouble(cursor.getColumnIndexOrThrow("SALARIO_ADMIN")));  // 12
                listaAdmin.add(administrador);
            }

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this.context, "mala conexion", Toast.LENGTH_SHORT).show();
        }
        return listaAdmin;
    }

    public Administrador getAdministrador(String nombres, String contra) {
        listaADM = selecAdministradors();
        for(Administrador us:listaADM){
            if(us.getNombres().equals(nombres) && us.getPassword().equals(contra)){
                return us;
            }
        }
        return null;
    }

    public Administrador getAdministradorById(int id) {
        listaADM = selecAdministradors();
        for(Administrador us:listaADM){
            if(us.getId()==id){
                return us;
            }
        }
        return null;
    }

    public int loginAdministrador(String nombres, String password) {
        try {
            String query = "SELECT COUNT(*) FROM ADMINISTRADOR WHERE NOMBRE_ADMIN = ? AND CONTRA_ADMIN = ?";
            Cursor cursor = database.rawQuery(query, new String[]{nombres, password});

            int columnIndexCount = cursor.getColumnIndexOrThrow("COUNT(*)");

            int count = 0;
            if (cursor.moveToNext()) {
                count = cursor.getInt(columnIndexCount);
            }

            cursor.close();

            if (count > 0) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            Toast.makeText(this.context, "mala conexion", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    public boolean deleteAdministrador(int idAdmin) {
        try {
            // Verificar si el administrador existe en la base de datos
            String query = "SELECT COUNT(*) FROM ADMINISTRADOR WHERE ID_ADMIN = ?";
            Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(idAdmin)});

            int columnIndexCount = cursor.getColumnIndexOrThrow("COUNT(*)");

            int count = 0;
            if (cursor.moveToNext()) {
                count = cursor.getInt(columnIndexCount);
            }

            cursor.close();

            // Si el administrador no existe en la base de datos, devolver false
            if (count == 0) {
                return false;
            }

            // Si el administrador existe en la base de datos, eliminar el registro y devolver true
            String deleteQuery = "DELETE FROM ADMINISTRADOR WHERE ID_ADMIN = ?";
            database.execSQL(deleteQuery, new String[]{String.valueOf(idAdmin)});

            Toast.makeText(this.context, "REGISTRO ELIMINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            Toast.makeText(this.context, "mala conexion", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
