package com.example.proyecto_ecorecolect_aedii.Desarrolladores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyecto_ecorecolect_aedii.Entidades.Desarrolladores;
import com.example.proyecto_ecorecolect_aedii.R;

public class FragmentDetalleDesarrollador extends Fragment {

    TextView txtNombre, txtCelular, txtCorreo, txtProfesion;
    ImageView imgFoto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detalle_desarrollador,container,false);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtNombre = view.findViewById(R.id.txtNombre);
        txtCelular = view.findViewById(R.id.txtCelular);
        txtCorreo = view.findViewById(R.id.txtCorreo);
        txtProfesion = view.findViewById(R.id.txtProfesion);
        imgFoto = view.findViewById(R.id.imgFoto);
    }

    public void mostrarDatos(Desarrolladores desarrollador){
        txtNombre.setText(desarrollador.getNombreDesarrollador()+" " +desarrollador.getApellidoPat_Des()+" "+desarrollador.getApellidoMat_Des()+" ");
        txtCelular.setText(desarrollador.getCelular_Des());
        txtCorreo.setText(desarrollador.getEmail_Des());
        txtProfesion.setText(desarrollador.getProfesion_Des());
        imgFoto.setImageResource(desarrollador.getImagenDesarrollador());
    }
}
