package com.utci.app4p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


        public void abrirRegistroEmpleado(View vista){ //metodo para abrir ventana de registro
        Intent pantallaRegistroE= new Intent(getApplicationContext(),RegistroActivity.class); // invocar registro Empleados
        startActivity(pantallaRegistroE); //iniciamos la pantalla de Registro
    }

    public void cerrarPantallaMenu(View vista) {
        finish();
    }
}