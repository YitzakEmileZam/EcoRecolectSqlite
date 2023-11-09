package com.example.proyecto_ecorecolect_aedii.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOAdministrador;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOServicios;
import com.example.proyecto_ecorecolect_aedii.Entidades.Administrador;
import com.example.proyecto_ecorecolect_aedii.MenuSecundario.ActFacturaMS;
import com.example.proyecto_ecorecolect_aedii.MenuSecundario.ActMenuSecundario;
import com.example.proyecto_ecorecolect_aedii.MenuSecundario.ActRegistrarServicioMS;
import com.example.proyecto_ecorecolect_aedii.R;

public class ActCrudServicios extends AppCompatActivity {

    EditText edtServ, edtImg, edtPrec, edtDesc;
    DAOAdministrador daoAdministrador = new DAOAdministrador(this);
    DAOServicios daoServicios = new DAOServicios(this);
    int idAdmin=0;
    Administrador administradorActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_act_crud_servicios);
        daoAdministrador.openBD();
        daoServicios.openBD();

        Bundle bundle = getIntent().getExtras();
        idAdmin = bundle.getInt("idAdmin");
        AsignarReferencia();

    }



    public void AsignarReferencia(){
        edtServ= findViewById(R.id.edtNombCrud);
        edtImg= findViewById(R.id.edtImagenCrud);
        edtPrec= findViewById(R.id.edtPrecioCrud);
        edtDesc=findViewById(R.id.edtDescCrud);

    }
    public void regresarInicioMenu(View view) {
        Intent intent = new Intent(this, ActPrincipalAdministrador.class);
        intent.putExtra("idAdmin",idAdmin);
        startActivity(intent);
    }
}