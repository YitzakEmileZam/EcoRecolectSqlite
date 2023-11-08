package com.example.proyecto_ecorecolect_aedii.FragmentsMuestra;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyecto_ecorecolect_aedii.Actividades.ActPrincipalPruebas;
import com.example.proyecto_ecorecolect_aedii.Entidades.Servicios;
import com.example.proyecto_ecorecolect_aedii.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleServicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleServicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetalleServicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleServicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleServicioFragment newInstance(String param1, String param2) {
        DetalleServicioFragment fragment = new DetalleServicioFragment();
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
    private Servicios object;

    ActPrincipalPruebas activity;


    private TextView addToRegisterBtn;
    private TextView tituloTxt, precioTxt, descripcionTxt, totalPrecioTxt;
    private ImageView ImagenServicio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();

        object = (Servicios)bundle.getSerializable("object");

        activity = (ActPrincipalPruebas) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_detalle_servicio, container, false);


        iniView(rootView);
        getBundle();
        return rootView;
    }

    /* ESTE METODO SIRVE PARA PODER HACER LOS CALCULOS CORRESPONDIENTES EN EL DETALLE CARRITO */
    private void getBundle() {
        int drawableResourceId = this.getResources().getIdentifier(object.getImagen(),"drawable", this.getContext().getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(ImagenServicio);

        tituloTxt.setText(object.getNombreServicio());
        precioTxt.setText("S/"+object.getPrecio());
        descripcionTxt.setText(object.getDescripcion());

        totalPrecioTxt.setText("S/"+Math.round(object.getPrecio()*100.0)/100.0);


        addToRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //successMessage("Adquiriste un buen plan, felicidades!!!");
                warningMessage("Porfavor inicie sesión con su cuenta de EcoRecolect!!!");
            }
        });
    }

    /* ESTE METODO ES PARA DECLARAR LAS VARIABLES QUE VAMOS A USAR CON SU RESPECTIVO ID */
    private void iniView(View rootView) {
        addToRegisterBtn = rootView.findViewById(R.id.addToRegisterBtn);
        tituloTxt = rootView.findViewById(R.id.tituloTxt);
        precioTxt = rootView.findViewById(R.id.precioTxt);
        descripcionTxt = rootView.findViewById(R.id.descripcionTxt);


        totalPrecioTxt = rootView.findViewById(R.id.totalPrecioTxt);

        ImagenServicio = rootView.findViewById(R.id.ImagenServicio);
    }

    /* ESTE METODO ES UN CUADRO DE DIALOGO INFORMANDO QUE HAS ADQUIRIDO UN BUEN SERVICIO  */
    public void successMessage(String message) {
        new SweetAlertDialog(getContext(),
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buena Eleccion!")
                .setContentText(message).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Detalle de un Servicio ");
    }

    /* ESTE METODO ES PARA MANDAR UN MENSAJE DE ALERTA COLOR AMARILLO SU IMAGEN */
    public void warningMessage(String message) {
        new SweetAlertDialog(getContext(),
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }
}