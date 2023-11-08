package com.example.proyecto_ecorecolect_aedii.Desarrolladores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAODesarrolladores;
import com.example.proyecto_ecorecolect_aedii.Entidades.Desarrolladores;
import com.example.proyecto_ecorecolect_aedii.Entidades.Servicios;
import com.example.proyecto_ecorecolect_aedii.R;

import java.sql.Date;
import java.util.ArrayList;

public class FragmentListaDesarrollador extends Fragment {
    ArrayList<Desarrolladores> listaDesarrolladores;
    ListView lstDesarrollador;
    DAODesarrolladores daoDesarrollador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lista_desarrollador,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        daoDesarrollador = new DAODesarrolladores(getContext());
        daoDesarrollador.openBD();
        lstDesarrollador = getView().findViewById(R.id.lstDesarrollador);
        listaDesarrolladores = daoDesarrollador.selectDesarrolladores();

        ArrayList<String> nombres = new ArrayList<>();
        for(Desarrolladores p:listaDesarrolladores){
            nombres.add(p.getNombreDesarrollador());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,nombres);
        lstDesarrollador.setAdapter(adapter);


        lstDesarrollador.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((IDesarrollador)getActivity()).seleccionarDesarrollador(listaDesarrolladores.get(position));
            }
        });
    }
}
