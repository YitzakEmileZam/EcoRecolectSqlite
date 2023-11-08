package com.example.proyecto_ecorecolect_aedii.BDSQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.proyecto_ecorecolect_aedii.Entidades.Servicios;

import java.sql.Date;
import java.util.ArrayList;

public class DAOServicios {

    ConexionBDSQLite conexionBD;
    Context context;
    ArrayList<Servicios> ServiciosArrayList;
    SQLiteDatabase database;

    public DAOServicios(Context context) {
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

    public boolean updateServicios(Servicios servicios) {
        try {
            // Verificar si ya existe un servicio con el mismo NOMBRE Y ID
            String query = "SELECT COUNT(*) FROM SERVICIOS WHERE NOMBRE_SERVICIOS = ? AND ID_SERVICIOS <> ?";
            String[] selectionArgs = {servicios.getNombreServicio(), String.valueOf(servicios.getId())};
            Cursor cursor = database.rawQuery(query, selectionArgs);
            cursor.moveToFirst();
            int countDNI = cursor.getInt(0);
            cursor.close();

            if (countDNI > 0) {
                // Ya existe UN SERVICIO
                return false;
            } else {
                // Actualizar registro existente
                String updateQuery = "UPDATE SERVICIOS SET " +
                        "ID_ADMINISTRADOR = ?, NOMBRE_SERVICIOS = ?, IMAGEN_SERVICIOS = ?, " +
                        "DESCRIPCION = ?, PRECIO_UNI = ?, FECHA_REGISTRO_SERVICIOS = ?, FECHA_ACTUALIZACION_SERVICIOS = ? WHERE ID_SERVICIOS = ?";
                String[] updateArgs = {
                        String.valueOf(servicios.getIdAdministrador()),
                        servicios.getNombreServicio(),
                        servicios.getImagen(),
                        servicios.getDescripcion(),
                        String.valueOf(servicios.getPrecio()),

                        String.valueOf(servicios.getFecha_registro().getTime()),
                        String.valueOf(servicios.getFecha_actualizacion().getTime()),
                        String.valueOf(servicios.getId())
                };
                database.execSQL(updateQuery, updateArgs);
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión servicios", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean insertServicios(Servicios servicios) {
        try {
            // Verificar si ya existe un registro con el mismo titulo o nombre del SERVICIO
            String verificarQuery = "SELECT COUNT(*) FROM SERVICIOS WHERE NOMBRE_SERVICIOS = ?";
            String[] verificarArgs = {servicios.getNombreServicio()};
            Cursor cursor = database.rawQuery(verificarQuery, verificarArgs);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            if (count > 0) {
                // Ya existe un registro con el mismo titulo o nombre del servicio
                return false;
            }

            // Insertar nuevo registro
            String insertQuery = "INSERT INTO SERVICIOS " +
                    "(ID_ADMINISTRADOR, NOMBRE_SERVICIOS, IMAGEN_SERVICIOS, " +
                    "DESCRIPCION, PRECIO_UNI, FECHA_REGISTRO_SERVICIOS, FECHA_ACTUALIZACION_SERVICIOS) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            Object[] insertArgs = {
                    servicios.getIdAdministrador(),
                    servicios.getNombreServicio(),
                    servicios.getImagen(),
                    servicios.getDescripcion(),
                    servicios.getPrecio(),

                    servicios.getFecha_registro().getTime(),
                    servicios.getFecha_actualizacion().getTime()
            };
            database.execSQL(insertQuery, insertArgs);
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public ArrayList<Servicios> selectServicios() {
        ArrayList<Servicios> listaServicios = new ArrayList<>();
        try {
            Cursor cursor = database.query("SERVICIOS", null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                Servicios servicios = new Servicios();
                servicios.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID_SERVICIOS")));  // 1
                servicios.setIdAdministrador(cursor.getInt(cursor.getColumnIndexOrThrow("ID_ADMINISTRADOR")));  // 2
                servicios.setNombreServicio(cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_SERVICIOS")));  // 3
                servicios.setImagen(cursor.getString(cursor.getColumnIndexOrThrow("IMAGEN_SERVICIOS")));  // 4
                servicios.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPCION")));  // 5
                servicios.setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow("PRECIO_UNI")));  // 6

                servicios.setFecha_registro(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_REGISTRO_SERVICIOS"))));  // 10
                servicios.setFecha_actualizacion(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_ACTUALIZACION_SERVICIOS"))));  // 11
                listaServicios.add(servicios);
            }

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this.context, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
        return listaServicios;
    }

    public ArrayList<Servicios> selectServiciosPARAJSONSINPROBLEMAS() {
        ArrayList<Servicios> listaServicios = new ArrayList<>();
        try {
            Cursor cursor = database.query("SERVICIOS", null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                Servicios servicios = new Servicios();
                servicios.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID_SERVICIOS")));  // 1
                servicios.setIdAdministrador(cursor.getInt(cursor.getColumnIndexOrThrow("ID_ADMINISTRADOR")));  // 2
                servicios.setNombreServicio(cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_SERVICIOS")));  // 3
                servicios.setImagen(cursor.getString(cursor.getColumnIndexOrThrow("IMAGEN_SERVICIOS")));  // 4
                servicios.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPCION")));  // 5
                servicios.setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow("PRECIO_UNI")));  // 6
                listaServicios.add(servicios);
            }

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this.context, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
        return listaServicios;
    }

    public ArrayList<Servicios> selectServiciosPorPrecio(double precio) {
        ArrayList<Servicios> listaServicios = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM SERVICIOS WHERE PRECIO_UNI = ?";
            String[] selectionArgs = {String.valueOf(precio)};
            Cursor cursor = database.rawQuery(selectQuery, selectionArgs);

            while (cursor.moveToNext()) {
                Servicios servicios = new Servicios();
                servicios.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID_SERVICIOS")));  // 1
                servicios.setIdAdministrador(cursor.getInt(cursor.getColumnIndexOrThrow("ID_ADMINISTRADOR")));  // 2
                servicios.setNombreServicio(cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_SERVICIOS")));  // 3
                servicios.setImagen(cursor.getString(cursor.getColumnIndexOrThrow("IMAGEN_SERVICIOS")));  // 4
                servicios.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPCION")));  // 5
                servicios.setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow("PRECIO_UNI")));  // 6


                long fechaRegistroMillis = cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_REGISTRO_SERVICIOS"));
                Date fechaRegistro = new Date(fechaRegistroMillis);
                servicios.setFecha_registro(fechaRegistro);

                long fechaActualizacionMillis = cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_ACTUALIZACION_SERVICIOS"));
                Date fechaActualizacion = new Date(fechaActualizacionMillis);
                servicios.setFecha_actualizacion(fechaActualizacion);
                listaServicios.add(servicios);
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
        return listaServicios;
    }

    public ArrayList<Servicios> selectServiciosPORELRANGODEPRECIOS(double minFee, double maxFee) {
        ArrayList<Servicios> listaServicios = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM SERVICIOS WHERE PRECIO_UNI BETWEEN ? AND ?";
            String[] selectionArgs = {String.valueOf(minFee), String.valueOf(maxFee)};
            Cursor cursor = database.rawQuery(selectQuery, selectionArgs);

            while (cursor.moveToNext()) {
                Servicios servicios = new Servicios();
                servicios.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID_SERVICIOS")));  // 1
                servicios.setIdAdministrador(cursor.getInt(cursor.getColumnIndexOrThrow("ID_ADMINISTRADOR")));  // 2
                servicios.setNombreServicio(cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_SERVICIOS")));  // 3
                servicios.setImagen(cursor.getString(cursor.getColumnIndexOrThrow("IMAGEN_SERVICIOS")));  // 4
                servicios.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPCION")));  // 5
                servicios.setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow("PRECIO_UNI")));  // 6

                servicios.setFecha_registro(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_REGISTRO_SERVICIOS"))));  // 10
                servicios.setFecha_actualizacion(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_ACTUALIZACION_SERVICIOS"))));  // 11
                listaServicios.add(servicios);
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
        return listaServicios;
    }


    public ArrayList<Servicios> selectServiciosPorIdAdministrador(int idAdministrador) {
        ArrayList<Servicios> listaServicios = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM SERVICIOS WHERE ID_ADMINISTRADOR = ?";
            String[] selectionArgs = {String.valueOf(idAdministrador)};
            Cursor cursor = database.rawQuery(selectQuery, selectionArgs);

            while (cursor.moveToNext()) {
                Servicios servicios = new Servicios();
                servicios.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID_SERVICIOS")));  // 1
                servicios.setIdAdministrador(cursor.getInt(cursor.getColumnIndexOrThrow("ID_ADMINISTRADOR")));  // 2
                servicios.setNombreServicio(cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_SERVICIOS")));  // 3
                servicios.setImagen(cursor.getString(cursor.getColumnIndexOrThrow("IMAGEN_SERVICIOS")));  // 4
                servicios.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPCION")));  // 5
                servicios.setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow("PRECIO_UNI")));  // 6

                servicios.setFecha_registro(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_REGISTRO_SERVICIOS"))));  // 10
                servicios.setFecha_actualizacion(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("FECHA_ACTUALIZACION_SERVICIOS"))));  // 11
                listaServicios.add(servicios);
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
        return listaServicios;
    }

    public int cantidadServicio() {
        int cantidad = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM SERVICIOS", null);
            if (cursor.moveToFirst()) {
                cantidad = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
        return cantidad;
    }

    public Servicios getServicio(String titulo, String imagen) {
        ServiciosArrayList=selectServicios();
        for(Servicios us:ServiciosArrayList){
            if(us.getNombreServicio().equals(titulo) && us.getImagen().equals(imagen)){
                return us;
            }
        }
        return null;
    }

    public Servicios getServicioById(int idServicio) {
        ServiciosArrayList=selectServicios();
        for(Servicios us:ServiciosArrayList){
            if(us.getId()==idServicio){
                return us;
            }
        }
        return null;
    }

    public boolean deleteServicio(int idServicio) {
        try {
            String whereClause = "ID_SERVICIOS = ?";
            String[] whereArgs = {String.valueOf(idServicio)};
            int resultado = database.delete("SERVICIOS", whereClause, whereArgs);

            if (resultado > 0) {
                Toast.makeText(context, "SERVICIO ELIMINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "NO SE PUDO ELIMINAR EL SERVICIO", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean deleteServicioPorAdministrador(int idAdministrador){
        try {
            String whereClause = "ID_ADMINISTRADOR = ?";
            String[] whereArgs = {String.valueOf(idAdministrador)};
            int resultado = database.delete("SERVICIOS", whereClause, whereArgs);

            if (resultado > 0) {
                Toast.makeText(context, "SERVICIO ELIMINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "NO SE PUDO ELIMINAR EL SERVICIO", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            return false;
        }
    }



}
