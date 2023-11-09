package com.example.proyecto_ecorecolect_aedii.FragmentsMuestra;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_ecorecolect_aedii.Actividades.ActCrudServicios;
import com.example.proyecto_ecorecolect_aedii.Actividades.ActPrincipalAdministrador;
import com.example.proyecto_ecorecolect_aedii.Actividades.ActPrincipalClientes;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOAdministrador;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOCliente;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOServicios;
import com.example.proyecto_ecorecolect_aedii.EXPORTAR.ClientesExporterExcel;
import com.example.proyecto_ecorecolect_aedii.EXPORTAR.ServiciosExporterPDF;
import com.example.proyecto_ecorecolect_aedii.Entidades.Administrador;
import com.example.proyecto_ecorecolect_aedii.Entidades.Cliente;
import com.example.proyecto_ecorecolect_aedii.Entidades.Servicios;
import com.example.proyecto_ecorecolect_aedii.R;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeAdministradorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeAdministradorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    Administrador administradorActual;

    DAOServicios daoServicios;
    DAOAdministrador daoAdministrador;
    DAOCliente daoCliente;

    ActPrincipalAdministrador activity;
    private Button btnCrudServicios;

    int idAdmin=0;
    ArrayList<Servicios> ListaServicios;

    ArrayList<Cliente> ListaClientes;
    public HomeAdministradorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeAdministradorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeAdministradorFragment newInstance(String param1, String param2) {
        HomeAdministradorFragment fragment = new HomeAdministradorFragment();
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

    Button btnPDFServicios, btnExcelClientes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        daoServicios = new DAOServicios(getContext());
        daoAdministrador = new DAOAdministrador(getContext());
        daoCliente = new DAOCliente(getContext());

        View rootView= inflater.inflate(R.layout.fragment_home_administrador, container, false);
        daoServicios.openBD();
        daoAdministrador.openBD();
        daoCliente.openBD();
        Bundle bundle = getArguments();
        idAdmin=bundle.getInt("idAdmin");
        administradorActual = daoAdministrador.getAdministradorById(idAdmin);
        activity = (ActPrincipalAdministrador) getActivity();
        ListaServicios=daoServicios.selectServicios();
        ListaClientes=daoCliente.selectClientes();
        AsignarReferencias(rootView);
        ParaPdf();
        ParaExcel();
        mostrarBotones();
        return rootView;

    }

    public void mostrarBotones(){

        btnCRUDSERVICIOS();

    }

    public void btnCRUDSERVICIOS(){
        btnCrudServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActCrudServicios.class);
                intent.putExtra("idAdmin",idAdmin);
                startActivity(intent);
            }
        });
    }
    public void AsignarReferencias(View rootView){
        btnPDFServicios= (Button) rootView.findViewById(R.id.btnPDFServicios);
        btnExcelClientes= (Button) rootView.findViewById(R.id.btnExcelClientes);
        btnCrudServicios= (Button) rootView.findViewById(R.id.btnCrudServicios);
    }

    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportarServiciosPDF();
                exportarClientesExcel();
            } else {
                Toast.makeText(getContext(), "Permiso de escritura en almacenamiento externo denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void ParaPdf(){
        btnPDFServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarPermisosYExportarPDF();
            }
        });
    }

    public void ParaExcel(){
        btnExcelClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarPermisosYExportarExcel();
            }
        });
    }
    private void solicitarPermisosYExportarPDF() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            exportarServiciosPDF();
        }
    }

    private void solicitarPermisosYExportarExcel() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            exportarClientesExcel();
        }
    }

    // ESTE METODO ya funciona y su ruta esta en esta clase ServiciosExporterPDF de donde se esta guardando
    private void exportarServiciosPDF() {
        ServiciosExporterPDF servicioExporterPDF = new ServiciosExporterPDF(ListaServicios);

        try {
            servicioExporterPDF.exportar();
            Toast.makeText(getContext(), "Datos exportados en PDF correctamente", Toast.LENGTH_SHORT).show();
        } catch (DocumentException | IOException e) {
            Toast.makeText(getContext(), "Error al exportar los datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // ESTE METODO ya funciona y su ruta esta en esta clase ClientesExporterExcel de donde se esta guardando
    private void exportarClientesExcel() {
        ClientesExporterExcel clientesExporterExcel = new ClientesExporterExcel(ListaClientes);

        try {
            clientesExporterExcel.exportar(getContext(), "clientes.xlsx");
            Toast.makeText(getContext(), "Archivo de Excel generado exitosamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al generar el archivo de Excel", Toast.LENGTH_SHORT).show();
        }
    }
}