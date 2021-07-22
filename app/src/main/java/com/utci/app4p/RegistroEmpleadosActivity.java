package com.utci.app4p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroEmpleadosActivity extends AppCompatActivity {
    EditText txtCedulaE,txtApellidoE,txtNombreE, txtNumeroE ,txtDireccionE, txtSueldoE ,txtCargoE, txthextraE,txthsupleE,txtstotalE;
    BaseDatos Bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empleados);
        txtCedulaE=(EditText) findViewById(R.id.txtCedulaE);
        txtApellidoE=(EditText) findViewById(R.id.txtApellidoE);
        txtNombreE=(EditText) findViewById(R.id.txtNombreE);
        txtNumeroE=(EditText) findViewById(R.id.txtNumeroE);
        txtDireccionE=(EditText) findViewById(R.id.txtDireccionE);
        txtSueldoE=(EditText) findViewById(R.id.txtSueldoE);
        txtCargoE=(EditText)  findViewById(R.id.txtCargoE);
        txthextraE=(EditText)  findViewById(R.id.txthextraE);
        txthsupleE=(EditText)  findViewById(R.id.txthsupleE);
        txtstotalE=(EditText)  findViewById(R.id.txtstotalE);
        Bdd=new BaseDatos(getApplicationContext());
    }
    public void guardarEmpleado(View vista){
        //capturados los valores ingresados por el usuario en el formulario de Registro de Clientes
        String cedula=  txtCedulaE.getText().toString();
        String apellido= txtApellidoE.getText().toString();
        String nombre= txtNombreE.getText().toString();
        String telefono=  txtNumeroE.getText().toString();
        String direccion= txtDireccionE.getText().toString();
        String sueldo=txtSueldoE.getText().toString();
        String cargo= txtCargoE.getText().toString();
        String hextra= txthextraE.getText().toString();
        String hsuple= txthsupleE.getText().toString();
        String stotal= txtstotalE.getText().toString();




        if (!cedula.equals("") && !apellido.equals("") && !nombre.equals("") && !telefono.equals("")  && !direccion.equals("") && !sueldo.equals("") && !cargo.equals("")){

            if (validacionCedula(cedula)!= false){
                Bdd.AgregarEmpleado(cedula,apellido,nombre,telefono,direccion,sueldo,cargo, hextra,hsuple,stotal);

                Toast.makeText(getApplicationContext(),"Empleado Registrdo Exitosamente",
                        Toast.LENGTH_LONG).show();
            }else{ Toast.makeText(getApplicationContext(),"Ingrese una cedula de identidad valida",
                    Toast.LENGTH_LONG).show();}


        }else{
            Toast.makeText(getApplicationContext(),"Para guardar complete todos los campos del formulario",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void abrirEditarEmpleados(View vista){ //metodo para abrir ventana de registro
        Intent pantallaRegistroE= new Intent(getApplicationContext(),EditarEmpleadoActivity.class); // invocar registro Empleados
        startActivity(pantallaRegistroE); //iniciamos la pantalla de Registro
    }

    //metodo par aconsultar los clientes existenetes en sqlite y presentarlos en una lista

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