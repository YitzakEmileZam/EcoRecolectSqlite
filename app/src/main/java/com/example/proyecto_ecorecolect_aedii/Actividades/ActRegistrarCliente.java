package com.example.proyecto_ecorecolect_aedii.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import java.sql.Date;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActRegistrarCliente extends AppCompatActivity  {

    DAOCliente daoCliente = new DAOCliente(this);
    TextInputEditText edtNombres, edtApellidos, edtDNI, edtEmail, edtCelular, edtContra, edtReContra;
    Spinner sprGenero;
    String gen[] = {"SELECCIONE UN GENERO","MASCULINO","FEMENINO"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registrar_cliente);
        daoCliente.openBD();
        asignarReferencia();
    }

    /* ESTE METODO SIRVE PARA PASAR DE UNA ACTIVIDAD A OTRA  */
    public void IrAlIniciarSesion(View view) {
        Intent intent = new Intent(this, ActIniciarSesion.class);
        startActivity(intent);
    }


    /* ESTE METODO ES ASIGANR LOS IDS CORRESPONDIENTES A CADA VARIABLE */
    private void asignarReferencia() {
        edtNombres = (TextInputEditText)findViewById(R.id.edtNombres);
        edtApellidos = (TextInputEditText)findViewById(R.id.edtApellidos);
        edtDNI =(TextInputEditText) findViewById(R.id.edtDNI);

        edtEmail = (TextInputEditText)findViewById(R.id.edtEmail);
        edtCelular = (TextInputEditText)findViewById(R.id.edtCelular);
        edtContra =(TextInputEditText) findViewById(R.id.edtContra);
        edtReContra =(TextInputEditText) findViewById(R.id.edtReContra);

        sprGenero=(Spinner) findViewById(R.id.sprGenero);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, gen);
        sprGenero.setAdapter(adapter);
    }


    /* ESTE METODO ES PARA REGISTRAR A LOS USUARIOS DE ECORECOLECT */
    public void registrarClientesEnEcoRecolect(View view){
        String ape, dni, gen, contra, recontra;
        Integer idFoto=0;
        ape = edtApellidos.getText().toString();
        dni = edtDNI.getText().toString();
        gen = sprGenero.getSelectedItem().toString();
        contra = edtContra.getText().toString();
        recontra = edtReContra.getText().toString();

        String nombre = edtNombres.getText().toString();
        String telefono = edtCelular.getText().toString();
        String correo = edtEmail.getText().toString();

        if(nombre.isEmpty() || ape.isEmpty() || dni.isEmpty() || correo.isEmpty() || telefono.isEmpty() ||
                gen.equals("SELECCIONE UN GENERO") || contra.isEmpty() || recontra.isEmpty() ) {
            warningMessage("Se ha producido un error : \nPor favor, complete todos los campos del formulario");
        }else{
            boolean a = esNombreValido(nombre);
            boolean b = esTelefonoValido(telefono);
            boolean c = esCorreoValido(correo);
            boolean d = esDniValido(dni);

            if(a){
                if(b){
                    if(c){
                        if(d){
                            switch (gen){
                                case "MASCULINO": idFoto=R.drawable.img_hombre; break;
                                case "FEMENINO": idFoto=R.drawable.img_mujer; break;
                            }
                            if(contra.equals(recontra)) {
                                /*  PROBANDO ESTA BASE DE DATOS */
                                java.util.Date date = new java.util.Date();
                                Cliente clienteRegistrado = new Cliente();
                                clienteRegistrado.setNombres(nombre);
                                clienteRegistrado.setApellidos(ape);
                                clienteRegistrado.setDni(dni);
                                clienteRegistrado.setEmail(correo);
                                clienteRegistrado.setCel(telefono);
                                clienteRegistrado.setGenero(gen);
                                clienteRegistrado.setPassword(contra);
                                clienteRegistrado.setRepassword(recontra);

                                clienteRegistrado.setIdFoto(idFoto);
                                clienteRegistrado.setFecha_registro(new Date(date.getTime()));
                                clienteRegistrado.setFecha_actualizacion(new Date(date.getTime()));
                                if( daoCliente.insertCliente(clienteRegistrado)){
                                    Intent i3=new Intent(ActRegistrarCliente.this,ActIniciarSesion.class);
                                    startActivity(i3);
                                    toastCorrecto("CLIENTE REGISTRADO EXITOSAMENTE EN ECORECOLECT!!!");
                                    finish();
                                } else {
                                    errorMessage("Ya existe un cliente con ese mismo dni!!!");                                }
                            }else{
                                toastIncorrecto("IMPORTANTE, Las contraseñas deben coincidir");
                            }
                        }else{
                            toastIncorrecto("IMPORTANTE, EL DNI NO ES VALIDO MINIMO 8 CARACTERES");
                        }
                    }else{
                        toastIncorrecto("ERROR, El correo no es valido");
                    }
                }else{
                    toastIncorrecto("IMPORTANTE, EL TELEFONO NO ES VALIDO");
                }
            }else{
                toastIncorrecto("ERROR, El nombre no es valido");
            }
        }
    }

    /* ESTE METODO ES PARA MANDAR UN MENSAJE DE ALERTA COLOR ROJO SU IMAGEN */
    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    /* ESTE METODO ES PARA MANDAR UN MENSAJE DE ALERTA COLOR AMARILLO SU IMAGEN */
    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }

    /* ESTE METODO ES PARA COMPROBAR SI EL NOMBRE ES VALIDO O NO */
    private boolean esNombreValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            edtNombres.setError("Nombre inválido");
            return false;
        } else {
            edtNombres.setError(null);
        }

        return true;
    }

    /* ESTE METODO ES PARA COMPROBAR SI EL DNI ES VALIDO O NO */
    private boolean esDniValido(String dni) {
        if (dni.length() != 8) {
            edtDNI.setError("DNI inválido, debe tener 8 caracteres");
            return false;
        } else {
            edtDNI.setError(null);
        }

        return true;
    }

    /* ESTE METODO ES PARA COMPROBAR SI EL TELEFONO ES VALIDO O NO */
    private boolean esTelefonoValido(String telefono) {
        if (!Patterns.PHONE.matcher(telefono).matches()) {
            edtCelular.setError("Teléfono inválido");
            return false;
        } else {
            edtCelular.setError(null);
        }

        return true;
    }

    /* ESTE METODO ES PARA COMPROBAR SI EL CORREO ES VALIDO O NO */
    private boolean esCorreoValido(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            edtEmail.setError("Correo electrónico inválido");
            return false;
        } else {
            edtEmail.setError(null);
        }

        return true;
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