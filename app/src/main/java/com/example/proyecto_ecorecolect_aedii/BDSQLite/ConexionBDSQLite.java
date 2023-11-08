package com.example.proyecto_ecorecolect_aedii.BDSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionBDSQLite extends SQLiteOpenHelper {
    public ConexionBDSQLite(@Nullable Context context) {
        super(context, Constantes.NOMBREDB_UNIFICADA, null, Constantes.VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DESARROLLADOR " +
                " ( DNI_DESARROLLADOR TEXT NOT NULL," +
                "  IMAGEN_DESARROLLADOR INTEGER  NOT NULL," +
                "  NOMBRE_DESARROLLADOR TEXT NOT NULL," +
                "  APELLIDO_PAT_DESARROLLADOR TEXT NOT NULL," +
                "  APELLIDO_MAT_DESARROLLADOR TEXT NOT NULL," +
                "  EMAIL_DESARROLLADOR TEXT NOT NULL," +
                "  CEL_DESARROLLADOR TEXT NOT NULL," +
                "  GENERO_DESARROLLADOR TEXT NOT NULL," +
                "  PROFESION_DESARROLLADOR TEXT NOT NULL," +
                "  CONSTRAINT PK_DESARROLLADOR PRIMARY KEY (DNI_DESARROLLADOR)" +
                ")");

        db.execSQL("CREATE TABLE ADMINISTRADOR " +
                "( ID_ADMIN INTEGER NOT NULL," +
                "IMAGEN_ADM INTEGER NOT NULL," +
                "NOMBRE_ADMIN TEXT NOT NULL," +
                "APELLIDO_ADMIN TEXT NOT NULL," +
                "DNI_ADMIN TEXT NOT NULL," +
                "EMAIL_ADMIN TEXT NOT NULL," +
                "CEL_ADMIN TEXT NOT NULL," +
                "GENERO_ADMIN TEXT NOT NULL," +
                "CONTRA_ADMIN TEXT NOT NULL," +
                "RECONTRA_ADMIN TEXT NOT NULL," +
                "NIVEL_ACCESO_ADMIN TEXT NOT NULL," +
                "SALARIO_ADMIN REAL NOT NULL," +
                "CONSTRAINT PK_ADMINISTRADOR PRIMARY KEY (ID_ADMIN)" +
                ")");

        db.execSQL("CREATE TABLE CLIENTES " +
                "( ID_CLIENTE INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "IMAGEN_CLIENTE INTEGER NOT NULL," +
                "NOMBRE_CLIENTE TEXT NOT NULL," +
                "APELLIDO_CLIENTE TEXT NOT NULL," +
                "DNI_CLIENTE TEXT NOT NULL," +
                "EMAIL_CLIENTE TEXT NOT NULL," +
                "CEL_CLIENTE TEXT NOT NULL," +
                "GENERO_CLIENTE TEXT NOT NULL," +
                "CONTRA_CLIENTE TEXT NOT NULL," +
                "RECONTRA_CLIENTE TEXT NOT NULL," +
                "FECHA_REGISTRO_CLIENTE DATE NOT NULL," +
                "FECHA_ACTUALIZACION_CLIENTE DATE)");


        db.execSQL("CREATE TABLE SERVICIOS " +
                "( ID_SERVICIOS INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "ID_ADMINISTRADOR INTEGER NOT NULL," +
                "NOMBRE_SERVICIOS TEXT NOT NULL," +
                "IMAGEN_SERVICIOS TEXT NOT NULL," +
                "DESCRIPCION TEXT NOT NULL," +
                "PRECIO_UNI REAL NOT NULL," +
                "FECHA_REGISTRO_SERVICIOS DATE NOT NULL," +
                "FECHA_ACTUALIZACION_SERVICIOS DATE," +
                "CONSTRAINT FK_SERVICIOS_ADMINISTRADOR FOREIGN KEY (ID_ADMINISTRADOR) REFERENCES ADMINISTRADOR (ID_ADMIN)" +
                ")");

        db.execSQL("CREATE TABLE COMPRA " +
                "(ID_COMPRA INTEGER NOT NULL," +
                "ID_CLI INTEGER NOT NULL," +
                "ID_ADMIN INTEGER NOT NULL," +
                "ID_SERVICIO INTEGER NOT NULL," +
                "MONTO REAL NOT NULL," +
                "ESTADO TEXT NOT NULL," +
                "METODO_PAGO TEXT," +
                "FECHA_COMPRA DATE NOT NULL," +
                "FECHA_ACTUALIZACION_COMPRA DATE," +
                "CONSTRAINT PK_COMPRA PRIMARY KEY (ID_COMPRA)," +
                "CONSTRAINT FK_COMPRA_CLIENTES FOREIGN KEY (ID_CLI) REFERENCES CLIENTES (ID_CLIENTE)," +
                "CONSTRAINT FK_COMPRA_ADMINISTRADOR FOREIGN KEY (ID_ADMIN) REFERENCES ADMINISTRADOR (ID_ADMIN)," +
                "CONSTRAINT FK_COMPRA_SERVICIOS FOREIGN KEY (ID_SERVICIO) REFERENCES SERVICIOS (ID_SERVICIOS)" +
                ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

