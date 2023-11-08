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

import com.example.proyecto_ecorecolect_aedii.Actividades.ActPrincipalClientes;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOCliente;
import com.example.proyecto_ecorecolect_aedii.Entidades.Cliente;
import com.example.proyecto_ecorecolect_aedii.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilClienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilClienteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int idCliente=0;
    ActPrincipalClientes activity;
    Cliente clienteActual;
    DAOCliente daoCliente;
    FragmentManager fragmentManager;

    TextView txt_nombre, txt_apellidos, txt_dni, txt_celular, txt_email, txt_genero;
    ImageView img_foto;
    TextView tvTitulo, textView14;


    public PerfilClienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilClienteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilClienteFragment newInstance(String param1, String param2) {
        PerfilClienteFragment fragment = new PerfilClienteFragment();
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
        daoCliente = new DAOCliente(getContext());
        daoCliente.openBD();
        Bundle bundle = getArguments();
        idCliente=bundle.getInt("idCliente");
        clienteActual = daoCliente.getClienteById(idCliente);
        activity = (ActPrincipalClientes) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_perfil_cliente, container, false);
        AsignarReferencias(rootView);
        mostrarInfoUsuario();

        return rootView;
    }

    public void AsignarReferencias(View rootView){
        // IMAGENES
        img_foto = (ImageView) rootView.findViewById(R.id.img_foto);

        // textviews
        txt_nombre = (TextView) rootView.findViewById(R.id.txt_nombre);
        txt_apellidos = (TextView) rootView.findViewById(R.id.txt_apellidos);
        txt_dni = (TextView) rootView.findViewById(R.id.txt_dni);
        txt_celular = (TextView) rootView.findViewById(R.id.txt_celular);
        txt_email = (TextView) rootView.findViewById(R.id.txt_email);
        txt_genero = (TextView) rootView.findViewById(R.id.txt_genero);

        tvTitulo = (TextView) rootView.findViewById(R.id.tvTitulo);
        textView14= (TextView) rootView.findViewById(R.id.textView14);

        fragmentManager = activity.getSupportFragmentManager();
    }

    private void mostrarInfoUsuario() {
        tvTitulo.setText("Mis Datos Personales");
        textView14.setText("Aqui podras visualizar \ntu información \n"+clienteActual.getNombres());
        txt_nombre.setText(clienteActual.getNombres());
        txt_apellidos.setText(clienteActual.getApellidos());
        txt_dni.setText(clienteActual.getDni());
        txt_email.setText(clienteActual.getEmail());
        txt_celular.setText(clienteActual.getCel());
        txt_genero.setText(clienteActual.getGenero());
        img_foto.setImageResource(clienteActual.getIdFoto());
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Mi Perfil "+clienteActual.getNombres());
    }



    private void openFragment(Fragment fragment, int idCliente) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("idCliente", idCliente);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container_cliente, fragment);
        transaction.commit();
    }
}