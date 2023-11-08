package com.example.proyecto_ecorecolect_aedii.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_ecorecolect_aedii.BDSQLite.DAOAdministrador;
import com.example.proyecto_ecorecolect_aedii.Entidades.Administrador;
import com.example.proyecto_ecorecolect_aedii.R;
import com.google.android.material.textfield.TextInputEditText;

public class ActIniciarSesionAdmin extends AppCompatActivity  {

    /*   NOTA IMPORTANTE
     * EN EL DEVICE MANAGER PONER AL COSTADO DE CADA EMULADOR
     * WITH DATA PARA LIMPIAR TODO EL ESPACIO UTILIZADO S ES QUE ESTA LLENO
     * ESE EMULADOR QUE ESTAS USANDO PARA NO TENER QUE DESCARGAR UN
     * EMULADOR NUEVO POR LAS PURAS
     * */
    TextInputEditText edtNombreAdmin, edtContraAdmin;

    // base de datos de los administradores
    DAOAdministrador daoAdministrador = new DAOAdministrador(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_iniciar_ses_admin);
        daoAdministrador.openBD();

        edtContraAdmin = (TextInputEditText) findViewById(R.id.edtContra);
        edtNombreAdmin =(TextInputEditText) findViewById(R.id.edtID);
    }

    /* ESTE METODO ES PARA INICIAR SESION COMO ADMINISTRADOR DE ACUERDO A SI ESTA O NO REGISTRADO INTERNAMENTE  */
    public void iniciarSesionAdmin(View view){
        String dniIngresado = edtNombreAdmin.getText().toString();
        String contraIngresado = edtContraAdmin.getText().toString();


        if(dniIngresado.equals("") && contraIngresado.equals("")){
            //Toast.makeText(this, "ERROR Campos vacios", Toast.LENGTH_LONG).show();
            toastIncorrecto("ERROR Campos vacios, vuelva a verificar los campos");
        }else if( daoAdministrador.loginAdministrador(dniIngresado,contraIngresado)==1 ){
            Administrador ax=daoAdministrador.getAdministrador(dniIngresado,contraIngresado);

            Intent i2 = new Intent(ActIniciarSesionAdmin.this, ActPrincipalAdministrador.class);
            i2.putExtra("idAdmin",ax.getId());

            startActivity(i2);
            toastCorrecto("Excelente, Datos Correctos Haz Iniciado Sesión, BIENVENIDO "+ax.getNombres()+" " + ax.getApellidos());
            finish();
        }else{
            //Toast.makeText(this, "Dni y/o Password incorrectos", Toast.LENGTH_LONG).show();
            toastIncorrecto("Credenciales Inválidas,"+" Dni y/o Password incorrectos");
        }
    }

    /* ESTE METODO SIRVE PARA PASAR DE UNA ACTIVIDAD A OTRA  */
    public void IrALaPaginaPrincipalPruebas(View view) {
        Intent intent = new Intent(this, ActPrincipalPruebas.class);
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