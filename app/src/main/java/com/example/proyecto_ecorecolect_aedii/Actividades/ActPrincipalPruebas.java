package com.example.proyecto_ecorecolect_aedii.Actividades;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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

import com.example.proyecto_ecorecolect_aedii.Desarrolladores.ActPrincipalDesarrolladores;
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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ActPrincipalPruebas extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawer_layout;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsignarReferencias();


        //String recipient = "ecoRecolect@outlook.com";
        String recipient = "codemanhh78@gmail.com";
        String subject = "INICIO DE SESION EN ECORECOLECT";
        String body = "¡Hola! "+ "Yitzak Emile" + " " + "Zamudio Pacheco" +"\n\nEste es un mensaje para " +
                "informarte que haz iniciado sesion exitosamente en la app de ECORECOLECT.";

        new EmailTask(recipient, subject, body, this).execute();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.nav_principal){
            openFragment(new HomeMuestraFragment());
        }else if(itemId == R.id.nav_login) {
            Intent intent = new Intent(this, ActIniciarSesion.class);
            startActivity(intent);
            openFragment(new NosotrosMuestraFragment());
        }else if(itemId == R.id.nav_nosotros) {
            openFragment(new NosotrosMuestraFragment());
        }else if(itemId == R.id.nav_desarrolladores) {
            Intent intent1 = new Intent(this, ActPrincipalDesarrolladores.class);
            startActivity(intent1);
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }


    private void AsignarReferencias(){
        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawer_layout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout , toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.bottom_home){
                    openFragment(new HomeMuestraFragment());
                    return true;
                }else if(itemId == R.id.bottom_mision) {
                    openFragment(new MisionMuestraFragment());
                    return true;
                }else if(itemId == R.id.bottom_vision) {
                    openFragment(new VisionMuestraFragment());
                    return true;
                }else if(itemId == R.id.bottom_login) {
                    Intent intent = new Intent(ActPrincipalPruebas.this, ActIniciarSesion.class);
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });

        fragmentManager = getSupportFragmentManager();
        openFragment(new HomeMuestraFragment());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActPrincipalPruebas.this, R.string.EMPRESA, Toast.LENGTH_SHORT).show();
            }
        });
    }
}