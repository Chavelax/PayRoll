package com.utci.app4p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {
    EditText txtApellidoRegistro, txtNombreRegistro, txtEmailRegistro, txtPasswordRegistro, txtPasswordConfirmada, txtCedulaRegistro; // definiendo objetos para capturar datos de la lista
    BaseDatos miBdd;// creando un objeto para acceder a los procesos de la BDD SQlite

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtApellidoRegistro = (EditText) findViewById(R.id.txtApellidoRegistro);
        txtNombreRegistro = (EditText) findViewById(R.id.txtNombreRegistro);
        txtEmailRegistro = (EditText) findViewById(R.id.txtEmailRegistro);
        txtPasswordRegistro = (EditText) findViewById(R.id.txtPasswordRegistro);
        txtPasswordConfirmada = (EditText) findViewById(R.id.txtPasswordConfirmada);
        txtCedulaRegistro = (EditText) findViewById(R.id.txtCedulaRegistro);
        miBdd = new BaseDatos(getApplicationContext()); //instanciar /construir la base de datos en el objeto mi bdd

    }

    public void cerrarPantallaRegistro(View vista) {
        finish();//cerrando la pantalla de registro
    }

    public void registrarUsuario(View vista){
        //capturando los valores ingresados por el usuario en variables JAva de tipo String
        String apellido=txtApellidoRegistro.getText().toString();
        String nombre=txtNombreRegistro.getText().toString();
        String email=txtEmailRegistro.getText().toString();
        String password=txtPasswordRegistro.getText().toString();
        String passwordConfirmada=txtPasswordConfirmada.getText().toString();
        String cedula=txtCedulaRegistro.getText().toString();

        //validacion que las contraseñas insertadas sean iguales

        if (!cedula.equals("") && !apellido.equals("") && !nombre.equals("") && !email.equals("")  && !password.equals("")&& !passwordConfirmada.equals("")){
            if (validacionCedula(cedula)!= false){
                if(password.equals(passwordConfirmada)){
                    miBdd.agregarUsuarios(apellido, nombre, email, password, cedula);
                    Toast.makeText(getApplicationContext(),"Usuario Registrado Exitosamente",
                            Toast.LENGTH_LONG).show();
                }else{ Toast.makeText(getApplicationContext(),"Los passwords deben ser iguales",
                        Toast.LENGTH_LONG).show();
                }
            }else{ Toast.makeText(getApplicationContext(),"Ingrese una cedula de identidad valida",
                    Toast.LENGTH_LONG).show();}

        }else{
            Toast.makeText(getApplicationContext(),"Para guardar complete todos los campos del formulario",
                    Toast.LENGTH_LONG).show();
        }

    }
    private boolean validacionCedula(String document) {
        byte sum = 0;
        try {
            if (document.trim().length() != 10)
                return false;
            String[] data = document.split("");
            byte verifier = Byte.parseByte(data[0] + data[1]);
            if (verifier < 1 || verifier > 24)
                return false;
            byte[] digits = new byte[data.length];
            for (byte i = 0; i < digits.length; i++)
                digits[i] = Byte.parseByte(data[i]);
            if (digits[2] > 6)
                return false;
            for (byte i = 0; i < digits.length - 1; i++) {
                if (i % 2 == 0) {
                    verifier = (byte) (digits[i] * 2);
                    if (verifier > 9)
                        verifier = (byte) (verifier - 9);
                } else
                    verifier = (byte) (digits[i] * 1);
                sum = (byte) (sum + verifier);
            }
            if ((sum - (sum % 10) + 10 - sum) == digits[9])
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}