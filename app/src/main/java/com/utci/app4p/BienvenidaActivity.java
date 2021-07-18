package com.utci.app4p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class BienvenidaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        //Definir un hilo--> simula un cronometro que ejecuta una accion con base a un tiempo predeterminado
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent ventanaLogin=new Intent(getApplicationContext(),MainActivity.class); //Construyendo un objeto de tipo ventana/Intent para poder abrir la ventana de login
                startActivity(ventanaLogin);//Permite entrar al formulario de login
                finish();//Cerrando la ventana de Bienvenida
            }
        },4000);
    }
}