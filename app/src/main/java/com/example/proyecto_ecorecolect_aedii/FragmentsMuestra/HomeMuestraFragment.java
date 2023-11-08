package com.example.proyecto_ecorecolect_aedii.FragmentsMuestra;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.proyecto_ecorecolect_aedii.Adaptadores.ServiciosAdapter;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOServicios;
import com.example.proyecto_ecorecolect_aedii.Entidades.Servicios;
import com.example.proyecto_ecorecolect_aedii.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;


import java.sql.Date;
import java.util.Calendar;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeMuestraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeMuestraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeMuestraFragment() {
        // Required empty public constructor
    }

    private ImageSlider imageSlider;

    DAOServicios daoServicios;

    ArrayList<Servicios> ListaServicios;

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewListaServicios;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeMuestraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeMuestraFragment newInstance(String param1, String param2) {
        HomeMuestraFragment fragment = new HomeMuestraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_muestra, container, false);
        daoServicios = new DAOServicios(getContext());
        daoServicios.openBD();
        ListaServicios=daoServicios.selectServiciosPARAJSONSINPROBLEMAS();

        Carrusel(rootView);
        recyclerViewServicios(rootView);
        return rootView;
    }

    private void Carrusel(View rootView){
        imageSlider = rootView.findViewById(R.id.imageSlider);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.carrusel_1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.carrusel_2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.carrusel_3, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }

    private void recyclerViewServicios(View rootView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewListaServicios = rootView.findViewById(R.id.view4);
        recyclerViewListaServicios.setLayoutManager(linearLayoutManager);

        adapter = new ServiciosAdapter(ListaServicios, getContext());
        recyclerViewListaServicios.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Principal");
    }


}