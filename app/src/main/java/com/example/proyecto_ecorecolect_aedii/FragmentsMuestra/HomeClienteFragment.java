package com.example.proyecto_ecorecolect_aedii.FragmentsMuestra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.proyecto_ecorecolect_aedii.Actividades.ActPrincipalClientes;
import com.example.proyecto_ecorecolect_aedii.Adaptadores.ServiciosAdapter;
import com.example.proyecto_ecorecolect_aedii.Adaptadores.ServiciosClienteAdapter;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOCliente;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOServicios;
import com.example.proyecto_ecorecolect_aedii.Entidades.Cliente;
import com.example.proyecto_ecorecolect_aedii.Entidades.Servicios;
import com.example.proyecto_ecorecolect_aedii.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeClienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeClienteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeClienteFragment() {
        // Required empty public constructor
    }

    private ImageSlider imageSlider;

    DAOServicios daoServicios;
    DAOCliente daoCliente;
    ArrayList<Servicios> ListaServicios;

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewListaServicios;



    int idCliente=0;
    Cliente clienteActual;
    TextView textViewMensajeBienvenida;
    ImageView imageViewMiFoto;

    ActPrincipalClientes activity;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeMuestraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeClienteFragment newInstance(String param1, String param2) {
        HomeClienteFragment fragment = new HomeClienteFragment();
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
        daoServicios = new DAOServicios(getContext());
        daoCliente = new DAOCliente(getContext());
        View rootView = inflater.inflate(R.layout.fragment_home_cliente, container, false);

        daoServicios.openBD();
        daoCliente.openBD();

        Bundle bundle = getArguments();
        idCliente=bundle.getInt("idCliente");
        clienteActual = daoCliente.getClienteById(idCliente);
        activity = (ActPrincipalClientes) getActivity();
        ListaServicios=daoServicios.selectServiciosPARAJSONSINPROBLEMAS();

        AsignarReferencias(rootView);
        Carrusel(rootView);
        recyclerViewServicios(rootView);
        return rootView;
    }

    public void AsignarReferencias(View rootView){
        textViewMensajeBienvenida= (TextView) rootView.findViewById(R.id.textViewMensajeBienvenida1);

        imageViewMiFoto= (ImageView) rootView.findViewById(R.id.imageViewMiFoto1);
        textViewMensajeBienvenida.setText("Bienvenido "+clienteActual.getNombres()+" "+clienteActual.getApellidos()+ ", ahora si podras adquirir nuestros servicios");
        imageViewMiFoto.setImageResource(clienteActual.getIdFoto());
    }

    private void Carrusel(View rootView){
        imageSlider = rootView.findViewById(R.id.imageSlider1);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.carrusel_1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.carrusel_2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.carrusel_3, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }

    private void recyclerViewServicios(View rootView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewListaServicios = rootView.findViewById(R.id.view41);
        recyclerViewListaServicios.setLayoutManager(linearLayoutManager);

        adapter = new ServiciosClienteAdapter(ListaServicios, getContext(), clienteActual, idCliente );
        recyclerViewListaServicios.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Principal " + clienteActual.getNombres()+ " " );
    }



}