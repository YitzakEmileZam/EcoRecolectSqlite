package com.example.proyecto_ecorecolect_aedii.MenuSecundario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_ecorecolect_aedii.Entidades.ServiciosMS;
import com.example.proyecto_ecorecolect_aedii.R;

import java.util.ArrayList;

public class ActRegistrarServicioMS extends AppCompatActivity {
    //Servicio
    ArrayList<ServiciosMS> listaServiciosEc;
    EditText edtDescri,edtPrec;
    Spinner spServ;


    String tipoServicio[]={"Recolección","Reutilización","Eliminación","Control de plagas","Limpieza pública"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registrar_servicio);
        asignarReferencia();
        recuperacionDatos();
    }

    public void asignarReferencia() {

        edtDescri = findViewById(R.id.edtDescri);
        edtPrec = findViewById(R.id.edtPrec);
        spServ = findViewById(R.id.spServ);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tipoServicio);
        spServ.setAdapter(adapter);

    }

    public void recuperacionDatos() {
        Bundle bundle=getIntent().getExtras();
        if(bundle==null){
            listaServiciosEc=new ArrayList<>();
        }else{
            listaServiciosEc=(ArrayList<ServiciosMS>)bundle.getSerializable("data");
        }
    }

    public void registrarInventario(View view){
        String tipoServicio,descrip,prec;
        int idfoto = 0;

        descrip=edtDescri.getText().toString();
        prec=edtPrec.getText().toString();
        tipoServicio=spServ.getSelectedItem().toString();

        switch (tipoServicio){
            case "Recolección":idfoto= R.drawable.servicio_basura;break;
            case "Reutilización":idfoto= R.drawable.servicio_recoleccion_residuos;break;
            case "Eliminación":idfoto= R.drawable.destruccion;break;
            case "Control de plagas":idfoto= R.drawable.dinero;break;
            case "Limpieza pública":idfoto= R.drawable.fgjdgfj;break;

        }
        listaServiciosEc.add(new ServiciosMS(idfoto,tipoServicio,descrip,prec));
        limpiarBarra();
    }

    public void limpiarBarra(){
        edtDescri.setText("");
        edtPrec.setText("");
        spServ.setSelection(0);
    }

    public void regresarMenu(View view){
        Intent intent=new Intent(this, ActMenuSecundario.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",listaServiciosEc);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void listaInventario(View view){
        Intent intent=new Intent(this, ActListarServicioMS.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",listaServiciosEc);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
