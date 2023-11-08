package com.example.proyecto_ecorecolect_aedii.BDSQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.proyecto_ecorecolect_aedii.Entidades.Cliente;

import java.sql.Date;
import java.util.ArrayList;

public class DAOCliente {

    ConexionBDSQLite conexionBD;
    Context context;
    ArrayList<Cliente> clienteArrayList;
    SQLiteDatabase database;

    public DAOCliente(Context context) {
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

    public boolean updateCliente(Cliente cliente) {
        try {
            // Verificar si ya existe un cliente con el mismo DNI
            String[] whereArgs = { cliente.getDni(), String.valueOf(cliente.getId()) };
            String queryDNI = "SELECT COUNT(*) FROM CLIENTES WHERE DNI_CLIENTE = ? AND ID_CLIENTE <> ?";
            Cursor cursor = database.rawQuery(queryDNI, whereArgs);
            cursor.moveToFirst();
            int countDNI = cursor.getInt(0);
            cursor.close();

            if (countDNI > 0) {
                // Ya existe un cliente con el mismo DNI
                return false;
            } else {
                // Actualizar registro existente
                ContentValues values = new ContentValues();
                values.put("NOMBRE_CLIENTE", cliente.getNombres());
                values.put("APELLIDO_CLIENTE", cliente.getApellidos());
                values.put("DNI_CLIENTE", cliente.getDni());
                values.put("EMAIL_CLIENTE", cliente.getEmail());
                values.put("CEL_CLIENTE", cliente.getCel());
                values.put("GENERO_CLIENTE", cliente.getGenero());
                values.put("CONTRA_CLIENTE", cliente.getPassword());
                values.put("RECONTRA_CLIENTE", cliente.getRepassword());

                values.put("IMAGEN_CLIENTE", cliente.getIdFoto());
                values.put("FECHA_REGISTRO_CLIENTE", cliente.getFecha_registro().getTime());
                values.put("FECHA_ACTUALIZACION_CLIENTE", cliente.getFecha_actualizacion().getTime());

                String whereClause = "ID_CLIENTE = ?";
                String[] whereArgsUpdate = { String.valueOf(cliente.getId()) };
                int rows = database.update("CLIENTES", values, whereClause, whereArgsUpdate);

                return rows > 0;
            }
        } catch (Exception e) {
            Toast.makeText(this.context, "Error de conexión", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean insertCliente(Cliente cliente) {
        try {
            // Verificar si ya existe un registro con el mismo DNI
            String[] whereArgs = { cliente.getDni() };
            String queryVerificar = "SELECT COUNT(*) FROM CLIENTES WHERE DNI_CLIENTE = ?";
            Cursor cursor = database.rawQuery(queryVerificar, whereArgs);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            if (count > 0) {
                // Ya existe un registro con el mismo DNI
                return false;
            }

            // Insertar nuevo registro
            String insertQuery = "INSERT INTO CLIENTES " +
                    "(NOMBRE_CLIENTE, APELLIDO_CLIENTE, DNI_CLIENTE, EMAIL_CLIENTE, CEL_CLIENTE, GENERO_CLIENTE, " +
                    "CONTRA_CLIENTE, RECONTRA_CLIENTE, IMAGEN_CLIENTE, FECHA_REGISTRO_CLIENTE, FECHA_ACTUALIZACION_CLIENTE ) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] insertArgs = {
                    cliente.getNombres(),
                    cliente.getApellidos(),
                    cliente.getDni(),
                    cliente.getEmail(),
                    cliente.getCel(),
                    cliente.getGenero(),
                    cliente.getPassword(),
                    cliente.getRepassword(),

                    cliente.getIdFoto(),
                    cliente.getFecha_registro().getTime(), // Almacena el tiempo en milisegundos
                    cliente.getFecha_actualizacion().getTime()
            };
            database.execSQL(insertQuery, insertArgs);
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public ArrayList<Cliente> selectClientes() {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM CLIENTES", null);
            while (cursor.moveToNext()) {
                Cliente cliente = new Cliente();
                cliente.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID_CLIENTE")));
                cliente.setNombres(cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_CLIENTE")));
                cliente.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow("APELLIDO_CLIENTE")));
                cliente.setDni(cursor.getString(cursor.getColumnIndexOrThrow("DNI_CLIENTE")));
                cliente.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL_CLIENTE")));
                cliente.setCel(cursor.getString(cursor.getColumnIndexOrThrow("CEL_CLIENTE")));
                cliente.setGenero(cursor.getString(cursor.getColumnIndexOrThrow("GENERO_CLIENTE")));
                cliente.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("CONTRA_CLIENTE")));
                cliente.setRepassword(cursor.getString(cursor.getColumnIndexOrThrow("RECONTRA_CLIENTE")));

                cliente.setIdFoto(cursor.getInt(cursor.getColumnIndexOrThrow("IMAGEN_CLIENTE")));
                cliente.setFecha_registro(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_REGISTRO_CLIENTE"))));
                cliente.setFecha_actualizacion(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_ACTUALIZACION_CLIENTE"))));
                listaClientes.add(cliente);
            }

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
        }

        return listaClientes;
    }

    public ArrayList<Cliente> selecUsuariosPorGenero(String generoCliente) {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try {
            String[] columns = {
                    "ID_CLIENTE",
                    "NOMBRE_CLIENTE",
                    "APELLIDO_CLIENTE",
                    "DNI_CLIENTE",
                    "EMAIL_CLIENTE",
                    "CEL_CLIENTE",
                    "GENERO_CLIENTE",
                    "CONTRA_CLIENTE",
                    "RECONTRA_CLIENTE",
                    "IMAGEN_CLIENTE",
                    "FECHA_REGISTRO_CLIENTE",
                    "FECHA_ACTUALIZACION_CLIENTE"
            };

            String selection = "GENERO_CLIENTE = ?";
            String[] selectionArgs = { generoCliente };

            Cursor cursor = database.query("CLIENTES", columns, selection, selectionArgs, null, null, null);

            while (cursor.moveToNext()) {
                Cliente cliente = new Cliente();
                cliente.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID_CLIENTE")));
                cliente.setNombres(cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_CLIENTE")));
                cliente.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow("APELLIDO_CLIENTE")));
                cliente.setDni(cursor.getString(cursor.getColumnIndexOrThrow("DNI_CLIENTE")));
                cliente.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL_CLIENTE")));
                cliente.setCel(cursor.getString(cursor.getColumnIndexOrThrow("CEL_CLIENTE")));
                cliente.setGenero(cursor.getString(cursor.getColumnIndexOrThrow("GENERO_CLIENTE")));
                cliente.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("CONTRA_CLIENTE")));
                cliente.setRepassword(cursor.getString(cursor.getColumnIndexOrThrow("RECONTRA_CLIENTE")));

                cliente.setIdFoto(cursor.getInt(cursor.getColumnIndexOrThrow("IMAGEN_CLIENTE")));
                cliente.setFecha_registro(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_REGISTRO_CLIENTE"))));
                cliente.setFecha_actualizacion(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_ACTUALIZACION_CLIENTE"))));
                listaClientes.add(cliente);
            }

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
        }

        return listaClientes;
    }

    public int CantidadUsuarios() {
        int count = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM CLIENTES", null);
            count = cursor.getCount();
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
        return count;
    }

    public int loginCliente(String dni, String contra) {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) FROM CLIENTES WHERE DNI_CLIENTE = ? AND CONTRA_CLIENTE = ?";
            String[] selectionArgs = { dni, contra };
            Cursor cursor = database.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
        }

        return count > 0 ? 1 : 0;
    }

    public Cliente getCliente(String dni, String contra) {
        clienteArrayList = selectClientes();
        for(Cliente us: clienteArrayList){
            if(us.getDni().equals(dni) && us.getPassword().equals(contra)){
                return us;
            }
        }
        return null;
    }

    public Cliente getClienteById(int idCliente) {
        clienteArrayList = selectClientes();
        for(Cliente us: clienteArrayList){
            if(us.getId()==idCliente){
                return us;
            }
        }
        return null;
    }

    public boolean deleteCliente(int idCliente) {
        try {
            String whereClause = "ID_CLIENTE = ?";
            String[] whereArgs = { String.valueOf(idCliente) };
            int resultado = database.delete("CLIENTES", whereClause, whereArgs);

            if (resultado > 0) {
                Toast.makeText(context, "CLIENTE ELIMINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "NO SE PUDO ELIMINAR EL CLIENTE", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
