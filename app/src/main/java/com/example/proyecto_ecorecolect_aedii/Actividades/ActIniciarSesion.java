package com.example.proyecto_ecorecolect_aedii.Actividades;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOCliente;
import com.example.proyecto_ecorecolect_aedii.Entidades.Cliente;
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
import com.google.android.material.textfield.TextInputEditText;

public class ActIniciarSesion extends AppCompatActivity  {
    /*   NOTA IMPORTANTE
     * EN EL DEVICE MANAGER PONER AL COSTADO DE CADA EMULADOR
     * WITH DATA PARA LIMPIAR TODO EL ESPACIO UTILIZADO S ES QUE ESTA LLENO
     * ESE EMULADOR QUE ESTAS USANDO PARA NO TENER QUE DESCARGAR UN
     * EMULADOR NUEVO POR LAS PURAS
     * */

    TextInputEditText edtContraseCliente, edtdniCliente;
    DAOCliente daoCliente = new DAOCliente(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_iniciar_ses_cliente);
        daoCliente.openBD();
        edtContraseCliente = (TextInputEditText) findViewById(R.id.edtContra);
        edtdniCliente =(TextInputEditText) findViewById(R.id.edtID);
    }


    /* ESTE METODO SIRVE PARA VALIDAR SI EL DNI Y LA CONTRASEÑA SON VALIDAS, SI SON VALIDOS ENTONCES INGRESAREMOS
     *  COMO ADMINISTRADOR */
    public void validarInformacionDelClienteIniciarSesion(View view){
        String dniIngresado = edtdniCliente.getText().toString();
        String contraIngresado = edtContraseCliente.getText().toString();

        if(dniIngresado.equals("")&&contraIngresado.equals("")){

            toastIncorrecto("ERROR Campos vacios");
        }else if(daoCliente.loginCliente(dniIngresado,contraIngresado)==1){
            Cliente ux=daoCliente.getCliente(dniIngresado,contraIngresado);

            Intent i2 = new Intent(ActIniciarSesion.this, ActPrincipalClientes.class);
            i2.putExtra("idCliente",ux.getId());

            String recipient = ux.getEmail();
            String subject = "INICIO DE SESION EN ECORECOLECT";
            String body = "¡Hola! "+ux.getNombres() +  " " +ux.getApellidos() +"\n\nEste es un mensaje para " +
                    "informarte que haz iniciado sesion exitosamente en la app de ECORECOLECT.";

            new EmailTask(recipient, subject, body, this).execute();

            startActivity(i2);
            toastCorrecto("Excelente, Datos Correctos Haz Iniciado Sesión "+ux.getNombres()+" " + ux.getApellidos());
            finish();
        }else{

            toastIncorrecto("Credenciales Inválidas,"+" Dni y/o Password incorrectos");
        }
    }

    /* ESTE METODO SIRVE PARA PASAR DE UNA ACTIVIDAD A OTRA  */
    public void registrarmeOnClick(View view) {
        Intent intent = new Intent(this, ActRegistrarCliente.class);
        startActivity(intent);
    }

    /* ESTE METODO SIRVE PARA PASAR DE UNA ACTIVIDAD A OTRA  */
    public void irAloginAdministradores(View view){
        Intent intent = new Intent(this, ActIniciarSesionAdmin.class);
        startActivity(intent);
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
}