package com.example.proyecto_ecorecolect_aedii.MenuSecundario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_ecorecolect_aedii.Actividades.ActPrincipalPruebas;
import com.example.proyecto_ecorecolect_aedii.R;

public class ActMenuSecundario extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_menu_secundario);

        ImageView imgOPC1 = findViewById(R.id.imgOPC1);
        ImageView imgOPC3 = findViewById(R.id.imgOPC3);



        imgOPC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActMenuSecundario.this, ActRegistrarServicioMS.class);
                startActivity(intent);
            }
        });

        imgOPC3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //debería ser ver factura
                Intent intent = new Intent(ActMenuSecundario.this, ActFacturaMS.class);
                startActivity(intent);
            }
        });

    }

    public void regresarInicioMenu(View view) {
        Intent intent = new Intent(this, ActPrincipalPruebas.class);
        startActivity(intent);
    }
}
