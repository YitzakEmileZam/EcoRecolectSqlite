package com.example.proyecto_ecorecolect_aedii.Actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOCliente;
import com.example.proyecto_ecorecolect_aedii.Desarrolladores.ActPrincipalDesarrolladores;
import com.example.proyecto_ecorecolect_aedii.Entidades.Cliente;

import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.HomeClienteFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.HomeMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.MisionMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.NosotrosMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.PerfilClienteFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.VisionMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.Outlook_Email.EmailTask;
import com.example.proyecto_ecorecolect_aedii.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActPrincipalClientes extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, OnClienteDataChangeListener {

    DrawerLayout drawer_layout_cliente;
    BottomNavigationView bottom_navigation_cliente;
    FragmentManager fragmentManager;
    Toolbar toolbar_cliente;
    NavigationView navigation_drawer_cliente;

    DAOCliente daoCliente=new DAOCliente(this);
    Cliente clienteActual;
    int idCliente=0;
    TextView textViewMiNombreNavigationView ;
    TextView textViewMiCorreoNavigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);
        daoCliente.openBD();

        Bundle bundle=getIntent().getExtras();
        idCliente=bundle.getInt("idCliente");
        clienteActual=daoCliente.getClienteById(idCliente);
        AsignarReferencias();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.nav_principal_cliente){
            openFragment(new HomeClienteFragment(), idCliente);

        }else if(itemId == R.id.nav_nosotros_cliente) {
            openFragment1(new NosotrosMuestraFragment());

        }else if(itemId == R.id.nav_cerrar_sesion_cliente) {
            //Dialogo para cerrar cuenta
            AlertDialog.Builder c = new AlertDialog.Builder(this);
            c.setMessage("Estas seguro de cerrar tu cuenta???").setTitle("CONFIRMACION");
            c.setCancelable(false);
            c.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent a = new Intent(ActPrincipalClientes.this, ActIniciarSesion.class);

                    startActivity(a);
                    toastCorrecto("Excelente, Haz Cerrado exitosamente la sesion");
                    finish();
                }
            });

            c.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            c.show();
        }
        drawer_layout_cliente.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer_layout_cliente.isDrawerOpen(GravityCompat.START)){
            drawer_layout_cliente.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void openFragment1(Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container_cliente,fragment);
        transaction.commit();
    }

    private void openFragment(Fragment fragment, int idCliente) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("idCliente", idCliente);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container_cliente, fragment);
        transaction.commit();
    }

    /* ESTE METODO ES PARA MANDAR UN MENSAJE CORRECTO COLOR AZUL TIPO TOAST */
    public void toastCorrecto(String msg) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast1);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    /* ESTE METODO ES PARA MANDAR UN MENSAJE INCORRECTO COLOR ROJO TIPO TOAST */
    public void toastIncorrecto(String msg) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_error, (ViewGroup) findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast2);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    /* ESTE METODO ES PARA MANDAR UN MENSAJE DE ALERTA COLOR ROJO SU IMAGEN */
    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    private void AsignarReferencias(){
        toolbar_cliente = findViewById(R.id.toolbar_cliente);

        setSupportActionBar(toolbar_cliente);

        drawer_layout_cliente = findViewById(R.id.drawer_layout_cliente);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout_cliente , toolbar_cliente,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer_layout_cliente.addDrawerListener(toggle);
        toggle.syncState();

        navigation_drawer_cliente = findViewById(R.id.navigation_drawer_cliente);
        navigation_drawer_cliente.setNavigationItemSelectedListener(this);


        View headerView = navigation_drawer_cliente.getHeaderView(0);
        textViewMiNombreNavigationView = headerView.findViewById(R.id.textViewMiNombreNavigationView);
        textViewMiCorreoNavigationView = headerView.findViewById(R.id.textViewMiCorreoNavigationView);

        textViewMiNombreNavigationView.setText(clienteActual.getNombres()+" "+clienteActual.getApellidos());
        textViewMiCorreoNavigationView.setText(clienteActual.getEmail()+"");

        // hasta aca es el codigo

        bottom_navigation_cliente = findViewById(R.id.bottom_navigation_cliente);
        bottom_navigation_cliente.setBackground(null);

        bottom_navigation_cliente.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.bottom_principal_cliente){
                    openFragment(new HomeClienteFragment(), idCliente);
                    return true;
                }else if(itemId == R.id.bottom_perfil_cliente) {
                    openFragment(new PerfilClienteFragment(), idCliente);
                    return true;
                }
                return false;
            }
        });

        fragmentManager = getSupportFragmentManager();
        openFragment(new HomeClienteFragment(), idCliente);
    }

    @Override
    public void onClienteDataChanged(Cliente clienteAct) {
        // Actualiza los datos del cliente en el NavigationView
        //clienteActual=clienteAct;
        textViewMiNombreNavigationView.setText(clienteAct.getNombres()+" "+clienteAct.getApellidos());
        textViewMiCorreoNavigationView.setText(clienteAct.getEmail()+"");
    }
}