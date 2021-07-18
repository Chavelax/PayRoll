package com.utci.app4p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtEmailLogin, txtPasswordLogin;     //creo objetos

    BaseDatos bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmailLogin = (EditText) findViewById(R.id.txtEmailLogin);
        txtPasswordLogin = (EditText) findViewById(R.id.txtPasswordLogin);
        bdd=new BaseDatos(getApplicationContext());
    }

    public void abrirPantallaRegistro(View vista){ //metodo para abrir ventana de registro
        Intent pantallaRegistro= new Intent(getApplicationContext(),RegistroActivity.class); //creando in intnt para invocar a registro activicty
        startActivity(pantallaRegistro); //iniciamos la pantalla de Registro

    }
    public void iniciarSesion(View vista){
        //logica de negocio para capturar los valores ingresados en el campo email y password y consultarlos en la BDD
        //capturar valores ingresados por el usuario
        String email=txtEmailLogin.getText().toString();
        String password = txtPasswordLogin.getText().toString();
        //siempre q hacemos consultas usamos el objeto cursor
        //COnsultar el usuario en la base de datos
        Cursor usuarioEncontrado = bdd.obtenerUsuarioPorEmailPassword(email,password);
        if(usuarioEncontrado != null){
            //Para el caso de que el email y contraseña ingresados sean correctos
            //Obteniendo el valor del email almacenadoe en la BDD
            String emailBdd = usuarioEncontrado.getString(3).toString();
            //Obteniendo el valor del email almacenadoe en la BDD
            String nombreBdd=usuarioEncontrado.getString(2).toString();
            //mostramos la bienvenida

            // Guardar en un SHARED PREFERENCES
            SharedPreferences preferencias = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("estado_usu", "activo");
            // Confirmar cambios del share prerences
            editor.commit();
            Toast.makeText(getApplicationContext(), "Bienvenido "+nombreBdd, Toast.LENGTH_LONG).show();
            //cerrando el formulario de inicio de seseion
            finish();
            //creando un objeto para manejar la ventana/antivyty del menu
            Intent ventanaMenu = new Intent(getApplicationContext(),MenuActivity.class);
            //abrir el activity del menu de opciones
            startActivity(ventanaMenu);

        }else{
            //para el caso de que el usuarioEncontrado sea nulo se muestra un mensaje informativo al usuario
            Toast.makeText(getApplicationContext(), "Email o contraseña incorrectos", Toast.LENGTH_LONG).show();
            txtPasswordLogin.setText(""); //limpiamos el campo de la contraseña
        }
    }

}