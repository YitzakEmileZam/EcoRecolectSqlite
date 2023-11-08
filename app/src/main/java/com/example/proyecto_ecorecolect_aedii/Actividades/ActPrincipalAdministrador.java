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

import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOAdministrador;
import com.example.proyecto_ecorecolect_aedii.Desarrolladores.ActPrincipalDesarrolladores;
import com.example.proyecto_ecorecolect_aedii.Entidades.Administrador;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.HomeAdministradorFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.HomeMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.MisionMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.NosotrosMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.PerfilAdministradorFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.VisionMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.Outlook_Email.EmailTask;
import com.example.proyecto_ecorecolect_aedii.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActPrincipalAdministrador extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawer_layout_admin;
    BottomNavigationView bottom_navigation_admin;
    FragmentManager fragmentManager;
    Toolbar toolbar_admin;
    FloatingActionButton fab_admin;
    int idAdmin=0;
    DAOAdministrador daoAdministrador = new DAOAdministrador(this);

    Administrador administradorActual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrador);
        daoAdministrador.openBD();
        Bundle bundle=getIntent().getExtras();
        idAdmin=bundle.getInt("idAdmin");
        administradorActual = daoAdministrador.getAdministradorById(idAdmin);
        AsignarReferencias();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.nav_home_administrador){
            openFragment(new HomeAdministradorFragment(), idAdmin);
        }else if(itemId == R.id.nav_quienes_ssomos) {
            openFragment1(new NosotrosMuestraFragment());

        }else if(itemId == R.id.nav_cerrar_sesion) {
            //Dialogo para cerrar cuenta
            AlertDialog.Builder c = new AlertDialog.Builder(this);
            c.setMessage("Estas seguro de cerrar tu cuenta???").setTitle("CONFIRMACION");
            c.setCancelable(false);
            c.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent a = new Intent(ActPrincipalAdministrador.this, ActIniciarSesionAdmin.class);
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
        drawer_layout_admin.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer_layout_admin.isDrawerOpen(GravityCompat.START)){
            drawer_layout_admin.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void openFragment1(Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container_admin,fragment);
        transaction.commit();
    }



    private void AsignarReferencias(){
        fab_admin = findViewById(R.id.fab_admin);
        toolbar_admin = findViewById(R.id.toolbar_admin);

        setSupportActionBar(toolbar_admin);

        drawer_layout_admin = findViewById(R.id.drawer_layout_admin);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout_admin , toolbar_admin,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer_layout_admin.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigation_drawer_admin = findViewById(R.id.navigation_drawer_admin);
        navigation_drawer_admin.setNavigationItemSelectedListener(this);

        // con esto pongo mi nombre y correo en el navigation view
        View headerView = navigation_drawer_admin.getHeaderView(0);

        TextView textViewMiRolNavigationViewAdminEmpleado = headerView.findViewById(R.id.textViewMiRolNavigationViewAdminEmpleado);
        TextView textViewMiNombreNavigationViewAdminEmpleado = headerView.findViewById(R.id.textViewMiNombreNavigationViewAdminEmpleado);
        TextView textViewMiCorreoNavigationViewAdminEmpleado = headerView.findViewById(R.id.textViewMiCorreoNavigationViewAdminEmpleado);

        if(administradorActual!=null){
            // Establece los datos en los TextViews
            textViewMiRolNavigationViewAdminEmpleado.setText("Aministrador");
            textViewMiNombreNavigationViewAdminEmpleado.setText(administradorActual.getNombres()+" "+administradorActual.getApellidos());
            textViewMiCorreoNavigationViewAdminEmpleado.setText(administradorActual.getEmail()+"");
        }

        // hasta aca es el codigo

        bottom_navigation_admin = findViewById(R.id.bottom_navigation_admin);
        bottom_navigation_admin.setBackground(null);

        bottom_navigation_admin.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.bottom_home_administrador){
                    openFragment(new HomeAdministradorFragment(), idAdmin);
                    return true;
                }else if(itemId == R.id.bottom_perfil_administrador) {
                    openFragment(new PerfilAdministradorFragment(), idAdmin);
                    return true;
                }
                return false;
            }
        });

        fragmentManager = getSupportFragmentManager();
        openFragment(new HomeAdministradorFragment(), idAdmin);

        fab_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActPrincipalAdministrador.this, R.string.EMPRESA, Toast.LENGTH_SHORT).show();
            }
        });
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

    private void openFragment(Fragment fragment,int idAdmin) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();

        bundle.putInt("idAdmin", idAdmin);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container_admin, fragment);
        transaction.commit();
    }
}