package com.example.proyecto_ecorecolect_aedii.MenuSecundario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_ecorecolect_aedii.Adaptadores.ServiciosAdapterMS;
import com.example.proyecto_ecorecolect_aedii.Entidades.ServiciosMS;
import com.example.proyecto_ecorecolect_aedii.R;

import java.util.ArrayList;

public class ActListarServicioMS extends AppCompatActivity {
    ArrayList<ServiciosMS> listaServiciosEco;
    ListView lstListServ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_listar_servicio);
        asignarReferencia();
        recuperarData();
        listarServicio();
    }

    private void listarServicio() {
        ServiciosAdapterMS adaptador=new ServiciosAdapterMS(this,listaServiciosEco);
        lstListServ.setAdapter(adaptador);
    }

    private void recuperarData() {
        Bundle bundle=getIntent().getExtras();
        if(bundle==null){
            Toast.makeText(this,"falta registra Servicios",Toast.LENGTH_LONG).show();
        }else {
            listaServiciosEco=(ArrayList<ServiciosMS>) bundle.getSerializable("data");
        }
    }

    private void asignarReferencia() {
        lstListServ=findViewById(R.id.lstServicio);
    }


    public void regresarRegistrarInventario(View view){
        Intent intent=new Intent(this,ActRegistrarServicioMS.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",listaServiciosEco);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
