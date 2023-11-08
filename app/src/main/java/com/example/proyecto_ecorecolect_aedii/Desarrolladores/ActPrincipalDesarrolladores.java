package com.example.proyecto_ecorecolect_aedii.Desarrolladores;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyecto_ecorecolect_aedii.Actividades.ActIniciarSesion;
import com.example.proyecto_ecorecolect_aedii.Actividades.ActPrincipalPruebas;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAODesarrolladores;
import com.example.proyecto_ecorecolect_aedii.Entidades.Desarrolladores;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.HomeMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.MisionMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.NosotrosMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.VisionMuestraFragment;
import com.example.proyecto_ecorecolect_aedii.Outlook_Email.EmailTask;
import com.example.proyecto_ecorecolect_aedii.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class ActPrincipalDesarrolladores extends AppCompatActivity implements IDesarrollador {
    FragmentListaDesarrollador fragmentLista;
    FragmentDetalleDesarrollador fragmentDetalle;

    DAODesarrolladores daoDesarrollador = new DAODesarrolladores(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_desarrollador);
        daoDesarrollador.openBD();
        asignarReferencias();
    }

    private void asignarReferencias() {
        fragmentLista = (FragmentListaDesarrollador) getSupportFragmentManager().
                findFragmentById(R.id.fgtLista);
        fragmentDetalle = (FragmentDetalleDesarrollador) getSupportFragmentManager().
                findFragmentById(R.id.fgtDetalle);
    }

    @Override
    public void seleccionarDesarrollador(Desarrolladores desarrollador) {     fragmentDetalle.mostrarDatos(desarrollador);     }

    public void regresarInicioSesion(View view) {
        Intent intent = new Intent(this, ActIniciarSesion.class);
        startActivity(intent);
    }

    public void regresarMenuPrincipal(View view) {
        Intent intent = new Intent(this, ActPrincipalPruebas.class);
        startActivity(intent);
    }
}