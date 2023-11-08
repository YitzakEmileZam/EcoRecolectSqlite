package com.example.proyecto_ecorecolect_aedii.FragmentsMuestra;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.proyecto_ecorecolect_aedii.Actividades.ActPrincipalAdministrador;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOAdministrador;
import com.example.proyecto_ecorecolect_aedii.Entidades.Administrador;

import com.example.proyecto_ecorecolect_aedii.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilAdministradorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilAdministradorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ActPrincipalAdministrador activity;

    int idAdmin=0;

    DAOAdministrador daoAdministrador;

    Administrador objAdministrador;

    private ImageView imageViewAdminEmpleado;
    private TextView txtNombreAdminEmpleado, txtNombresss1, txtApellidosss1, txtDni1, txtEmail11, txtCel11,
            txtGenero11, txtContra11;

    // textviews escondidos
    private TextView txtNivelAccesoAdmin11, txtSalario11, txtCargo11, txtfecha_Contratacion11;
    FragmentManager fragmentManager;
    // Linearlayouts escondidos
    private LinearLayout LinearLayoutNivelAccesoAdmin1, LinearLayoutSalario1, LinearLayoutCargo1, LinearLayoutfecha_Contratacion1;
    public PerfilAdministradorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilAdministradorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilAdministradorFragment newInstance(String param1, String param2) {
        PerfilAdministradorFragment fragment = new PerfilAdministradorFragment();
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
        daoAdministrador = new DAOAdministrador(getContext());

        daoAdministrador.openBD();

        Bundle bundle = getArguments();

        idAdmin=bundle.getInt("idAdmin");
        objAdministrador = daoAdministrador.getAdministradorById(idAdmin);

        // Llamar al método toastCorrecto() en la actividad
        activity = (ActPrincipalAdministrador) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_perfil_administrador, container, false);
        AsignarReferencias(rootView);
        corroborarAcceso(rootView);
        mostrarInformacion();
        return rootView;
    }

    public void AsignarReferencias(View rootView){
        // IMAGENES
        imageViewAdminEmpleado = (ImageView) rootView.findViewById(R.id.imageViewAdminEmpleado);

        // LINEAR LAYOUTS ESCONDIDOS
        LinearLayoutNivelAccesoAdmin1 = (LinearLayout) rootView.findViewById(R.id.LinearLayoutNivelAccesoAdmin1);
        LinearLayoutSalario1 = (LinearLayout) rootView.findViewById(R.id.LinearLayoutSalario1);
        LinearLayoutCargo1 = (LinearLayout) rootView.findViewById(R.id.LinearLayoutCargo1);
        LinearLayoutfecha_Contratacion1 = (LinearLayout) rootView.findViewById(R.id.LinearLayoutfecha_Contratacion1);

        // Textviewwss
        txtNombreAdminEmpleado = (TextView) rootView.findViewById(R.id.txtNombreAdminEmpleado);
        txtNombresss1 = (TextView) rootView.findViewById(R.id.txtNombresss1);
        txtApellidosss1 = (TextView) rootView.findViewById(R.id.txtApellidosss1);
        txtDni1 = (TextView) rootView.findViewById(R.id.txtDni1);
        txtEmail11 = (TextView) rootView.findViewById(R.id.txtEmail11);
        txtCel11 = (TextView) rootView.findViewById(R.id.txtCel11);
        txtGenero11 = (TextView) rootView.findViewById(R.id.txtGenero11);
        txtContra11 = (TextView) rootView.findViewById(R.id.txtContra11);

        // Textviewwss escondidos
        txtNivelAccesoAdmin11 = (TextView) rootView.findViewById(R.id.txtNivelAccesoAdmin11);
        txtSalario11 = (TextView) rootView.findViewById(R.id.txtSalario11);
        txtCargo11 = (TextView) rootView.findViewById(R.id.txtCargo11);
        txtfecha_Contratacion11 = (TextView) rootView.findViewById(R.id.txtfecha_Contratacion11);

        fragmentManager = getActivity().getSupportFragmentManager();
    }

    public void corroborarAcceso(View rootView){
        if( idAdmin > 0 ){
            LinearLayoutNivelAccesoAdmin1.setVisibility(rootView.VISIBLE);
            LinearLayoutSalario1.setVisibility(rootView.VISIBLE);
            LinearLayoutCargo1.setVisibility(rootView.GONE);
            LinearLayoutfecha_Contratacion1.setVisibility(rootView.GONE);
        }
    }

    public void mostrarInformacion(){
        if( idAdmin > 0 ){
            // Obtén el identificador de recurso guardado en la base de datos
            int resourceId = objAdministrador.getIdFoto();

            // Obtén el objeto Drawable correspondiente al identificador de recurso
            Drawable drawable = ContextCompat.getDrawable(getContext(), resourceId);
            imageViewAdminEmpleado.setImageDrawable(drawable);

            txtNombreAdminEmpleado.setText(" ADMINISTRADOR");
            txtNombresss1.setText(""+objAdministrador.getNombres());
            txtApellidosss1.setText(""+objAdministrador.getApellidos());
            txtDni1.setText(""+objAdministrador.getDni());
            txtEmail11.setText(""+objAdministrador.getEmail());
            txtCel11.setText(""+objAdministrador.getCel());
            txtGenero11.setText(""+objAdministrador.getGenero());
            txtContra11.setText(""+objAdministrador.getPassword());

            txtNivelAccesoAdmin11.setText(""+objAdministrador.getNivelAcceso());
            txtSalario11.setText("S/. "+objAdministrador.getSalario()+" soles");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Mi Perfil");
    }
}