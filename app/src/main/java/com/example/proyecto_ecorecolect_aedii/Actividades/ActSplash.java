package com.example.proyecto_ecorecolect_aedii.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOAdministrador;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOCliente;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAODesarrolladores;
import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOServicios;
import com.example.proyecto_ecorecolect_aedii.Entidades.Administrador;
import com.example.proyecto_ecorecolect_aedii.Entidades.Cliente;
import com.example.proyecto_ecorecolect_aedii.Entidades.Desarrolladores;
import com.example.proyecto_ecorecolect_aedii.Entidades.Servicios;
import com.example.proyecto_ecorecolect_aedii.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class ActSplash extends AppCompatActivity {
    private final int duration = 4000;

    DAODesarrolladores daoDesarrollador = new DAODesarrolladores(this);

    DAOServicios daoServicios = new DAOServicios(this);

    DAOAdministrador daoAdministrador = new DAOAdministrador(this);

    DAOCliente daoCliente = new DAOCliente(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_splash);

        daoDesarrollador.openBD();
        daoServicios.openBD();
        daoAdministrador.openBD();
        daoCliente.openBD();

        agregarDesarrolladores();
        agregarAdministradores();
        agregarServicios();

        agregarClientes();
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);

        ImageView logo = findViewById(R.id.logo);

        logo.setAnimation(animacion1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActSplash.this, ActPrincipalPruebas.class);
                Pair[] pairs = new Pair[1];
                pairs[0]= new Pair<View, String>(logo,"logo");

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ActSplash.this, pairs);
                    startActivity(intent, options.toBundle());
                }else{
                    startActivity(intent);
                    finish();
                }
            };
        }, duration);
    }



    private void agregarDesarrolladores() {
        daoDesarrollador.insertDesarrollador(new Desarrolladores("99887766", R.drawable.carrusel_1, "Michael", "Gustavo", "Orihuela ", "michaelorihuela00@gmail.com", "965 275 078", "MASCULINO", "Ingeniería de Computación y Sistemas"));
        daoDesarrollador.insertDesarrollador(new Desarrolladores("99887765", R.drawable.logo_empresa_ecorecolect_1, "Renzo Marlon", "Huamán", "Melgar", "renzo_huaman2@usmp.pe", "974 658 992", "MASCULINO", "Ingeniería de Computación y Sistemas"));
        daoDesarrollador.insertDesarrollador(new Desarrolladores("99887764", R.drawable.carrusel_2, "Bryan Arnold", "Valdivia", "Pillaca", "bryan2910arnold@gmail.com", "958 526 643", "MASCULINO", "Ingeniería de Computación y Sistemas"));
        daoDesarrollador.insertDesarrollador(new Desarrolladores("99887763", R.drawable.logo_empresa_ecorecolect_1, "Mathías Marcelo", "Cueto", "Escobar", "mathias_cueto@usmp.pe", "973 597 929", "MASCULINO", "Ingeniería de Computación y Sistemas"));
        daoDesarrollador.insertDesarrollador(new Desarrolladores("99887762", R.drawable.carrusel_3, "Thaine Alexander", "Alarcon", "Segovia", "thaine_alarcon@usmp.pe", "963 481 267", "MASCULINO", "Ingeniería de Computación y Sistemas"));
        daoDesarrollador.insertDesarrollador(new Desarrolladores("99887761", R.drawable.logo_empresa_ecorecolect_1, "Yitzak Emile", "Zamudio", "Pacheco", "yitzak_zamudio@usmp.pe", "926 635 137", "MASCULINO", "Ingeniería de Computación y Sistemas"));
    }


    private void agregarAdministradores(){
        daoAdministrador.insertAdministrador(new Administrador(1,R.drawable.img_hombre, "Yitzak Emile","Zamudio Pacheco","78978978",
                "yitzak@gmail.com", "927572267","MASCULINO","123","123","ALTO",10000.0));

        daoAdministrador.insertAdministrador(new Administrador(2,R.drawable.img_mujer, "Gimena","Usmp","7777",
                "tu@gmail.com", "998877665","FEMENINO","321","321","ALTO",5000.0));

        daoAdministrador.insertAdministrador(new Administrador(3,R.drawable.img_hombre, "Andres","Zamudio","88888888",
                "andres@gmail.com", "912345678","MASCULINO","88888888","88888888","ALTO",8000.0));
    }
    private void agregarServicios() {
        java.util.Date date = new java.util.Date();
        daoServicios.insertServicios(new Servicios(1,"Servicio de eliminación de basura","servicio_basura","Reduciremos significativamente los residuos recolectados durante el proceso",15.0,obtenerFechaEspecifica(2023,05,12),new Date(date.getTime())));
        daoServicios.insertServicios(new Servicios(1,"Servicio de control de plagas","dinero","Reduciremos el crecimiento de animales con enfermedades, insectos, etc.",15.0,obtenerFechaEspecifica(2023,05,12),new Date(date.getTime())));
        daoServicios.insertServicios(new Servicios(1,"Servicio de Recolección de Residuos","servicio_recoleccion_residuos","Podremos recolectar todo tipo de residuos",15.0,obtenerFechaEspecifica(2023,05,12),new Date(date.getTime())));
        daoServicios.insertServicios(new Servicios(1,"Servicio de Reutilización de Residuos","destruccion","Somos capaces de organizar y darle una segunda vida a ciertos desechos",15.0,obtenerFechaEspecifica(2023,05,12),new Date(date.getTime())));
    }

    /* METODO PARA AGREGAR INTERNAMENTE EN LA BASE DE DATOS DE LOS CLIENTES */
    private void agregarClientes(){
        /*  PROBANDO ESTA BASE DE DATOS */

        java.util.Date date = new java.util.Date();

        // 1
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Jesus Alberto","Soria LLantoy","73514145",
                "jesus_soria@usmp.pe", "927572267","MASCULINO","73514145","73514145",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 2
        daoCliente.insertCliente(new Cliente(R.drawable.img_mujer, "Kristell Yazileth","Flores Huamani","71959078",
                "kristell_flores@usmp.pe", "967232735","FEMENINO","71959078","71959078",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 3
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Matias Nicolas","Araujo Vera","74965516",
                "matias_araujo@usmp.pe", "924536111","MASCULINO","74965516","74965516",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 4
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Yasser Miguel Angelo","Avalos Montero","60755349",
                "yasser_avalos@usmp.pe", "941153390","MASCULINO","60755349","60755349",obtenerFechaEspecifica(2022,02,15),obtenerFechaEspecifica(2023,02,16)));
        // 5
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Arturo Tomas","Aliaga Silva","76534375",
                "arturo_aliaga1@usmp.pe", "933138899","MASCULINO","76534375","76534375",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 6
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Leonardo Manuel","Caycho Rivas","70464655",
                "leonardo_caycho@usmp.pe", "949230622","MASCULINO","70464655","70464655",obtenerFechaEspecifica(2022,02,15),obtenerFechaEspecifica(2023,02,16)));
        // 7
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Yitzak Emile","Zamudio Pacheco","70985090",
                "yitzak_zamudio@usmp.pe", "926635137","MASCULINO","70985090","70985090",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 8
        daoCliente.insertCliente(new Cliente(R.drawable.img_mujer, "Sandra Yadhira","Rodriguez Rivera","74965539",
                "sandra_rodriguez5@usmp.pe", "912782380","FEMENINO","74965539","74965539",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 9
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Ruben Osvaldo","Garcia Farje","08167417",
                "rgarcia@usmp.pe", "999999999","MASCULINO","08167417","08167417",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 10
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Leonardo","Buitron Farfan","75150962",
                "leonardo_buitron@usmp.pe", "952125847","MASCULINO","75150962","75150962",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 11
        daoCliente.insertCliente(new Cliente(R.drawable.img_mujer, "Samira","Quiquen Coba","74036015",
                "samira_quiquen@usmp.pe", "956822558","FEMENINO","74036015","74036015",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 12
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Brandon Jair","Viru Camones","72460815",
                "brandon_viru@usmp.pe", "945356543","MASCULINO","72460815","72460815",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 13
        daoCliente.insertCliente(new Cliente(R.drawable.img_mujer, "Andrea Nikole","Alva Chavez","73424336",
                "andrea_alva2@usmp.pe", "922977009","FEMENINO","73424336","73424336",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 14
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Yanpiero Imanol","Cutipa Monroy","71498322",
                "yanpiero_cutipa@usmp.pe", "955015210","MASCULINO","71498322","71498322",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 15
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Oscar Andre","Febres Delgado","70276487",
                "oscar_febres@usmp.pe", "988380603","MASCULINO","70276487","70276487",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 16
        daoCliente.insertCliente(new Cliente(R.drawable.img_mujer, "Alexandra Gianella","Salazar Rios","72735361",
                "alexandra_salazar6@usmp.pe", "912087635","FEMENINO","72735361","72735361",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 17
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Juan Carlos","Morales Sanchez","61759643",
                "juan_morales8@usmp.pe", "981573807","MASCULINO","61759643","61759643",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 18
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Lucas Adrian","Espinoza Laurente","73035832",
                "lucas_espinoza@usmp.pe", "963089053","MASCULINO","73035832","73035832",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 19
        daoCliente.insertCliente(new Cliente(R.drawable.img_mujer, "Katherine Milagros","Hernandez Valencia","72196690",
                "katherine_hernandez6@usmp.pe", "994028662","FEMENINO","72196690","72196690",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 20
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Andres Eduardo","Lamillar LLantoy","73497212",
                "andreslamillar@outlook.com", "943313837","MASCULINO","73497212","73497212",obtenerFechaEspecifica(2022,02,15), obtenerFechaEspecifica(2023,02,16)));
        // 21
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Junior Jesus Alejandro","Tovar Salazar","72765426",
                "junior_tovar@usmp.pe", "986682184","MASCULINO","72765426","72765426",obtenerFechaEspecifica(2022,3,23),new Date(date.getTime())));
        // 22
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Alexander Abraham","Marino Calixto","75905032",
                "alexander_marino@usmp.pe", "948573280","MASCULINO","75905032","75905032",obtenerFechaEspecifica(2023,04,22),new Date(date.getTime())));
        // 23
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Eduardo Andres","LLantoy Lamillar","87654321",
                "andreslamillarllantoy13@gmail.com", "999993515","MASCULINO","87654321","87654321",obtenerFechaEspecifica(2023,05,12),new Date(date.getTime())));
        // 24
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Cesar Pedro","Soria Campomanes","22233222",
                "yisusoria@gmail.com", "999919999","MASCULINO","22233222","22233222",new Date(date.getTime()),new Date(date.getTime())));


        // 25
        daoCliente.insertCliente(new Cliente(R.drawable.img_hombre, "Yitzak 2","Yitzak 2","15915915",
                "codemanhh78@gmail.com", "999919999","MASCULINO","15915915","15915915",new Date(date.getTime()),new Date(date.getTime())));
    }

    public Date obtenerFechaEspecifica(int year_anio, int month_mes, int day_dia) {
        // Crear una instancia de Calendar
        Calendar calendar = Calendar.getInstance();

        // Establecer la fecha específica que deseas
        calendar.set(Calendar.YEAR, year_anio);
        calendar.set(Calendar.MONTH, month_mes - 1); // Restamos 1 al mes, ya que los meses en Calendar comienzan en 0
        calendar.set(Calendar.DAY_OF_MONTH, day_dia);

        // Obtener la fecha del Calendar
        Date fechaEspecifica = new Date(calendar.getTimeInMillis());

        // Devolver la fecha especificada
        return fechaEspecifica;
    }
}