package com.utci.app4p;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GenerarRolActovity extends AppCompatActivity {
    String id, cedula, apellido, nombre, telefono, direccion, sueldo, cargo;
    EditText txtCedulaE1,txtApellidoE1,txtNombreE1, txtNumeroE1 ,txtDireccionE1, txtSueldoE1 ,txtCargoE1;
    TextView txtEmpleadoEditar;
    BaseDatos Bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_rol_actovity);
        txtEmpleadoEditar = (TextView) findViewById(R.id.txtEmpleadoEditar);
        txtCedulaE1= findViewById(R.id.txtCedulaE1);
        txtApellidoE1=findViewById(R.id.txtApellidoE1);
        txtNombreE1= findViewById(R.id.txtNombreE1);
        txtNumeroE1= findViewById(R.id.txtNumeroE1);
        txtDireccionE1= findViewById(R.id.txtDireccionE1);
        txtSueldoE1= findViewById(R.id.txtSueldoE1);
        txtCargoE1=  findViewById(R.id.txtCargoE1);
        Bundle parametrosExtra = getIntent().getExtras();//capturando los parametros que se han pasado a esta actividad
        if (parametrosExtra != null) {
            try {

                id = parametrosExtra.getString("id");
                cedula = parametrosExtra.getString("cedula");
                apellido = parametrosExtra.getString("apellido");
                nombre = parametrosExtra.getString("nombre");
                telefono = parametrosExtra.getString("telefono");
                direccion = parametrosExtra.getString("direccion");
                sueldo = parametrosExtra.getString("sueldo");
                cargo = parametrosExtra.getString("cargo");
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Error al procesar la solicitud", Toast.LENGTH_SHORT).show();
            }
        }
        txtEmpleadoEditar.setText(id);
        txtCedulaE1.setText(cedula);
        txtApellidoE1.setText(apellido);
        txtNombreE1.setText(nombre);
        txtNumeroE1.setText(telefono);
        txtDireccionE1.setText(direccion);
        txtSueldoE1.setText(sueldo);
        txtCargoE1.setText(cargo);
        Bdd = new BaseDatos(getApplicationContext());
    }
    public void volver(View vista) {
        finish();//cerrar la ventana
        //creando un objeto para manejar la ventana de gestion de clientes
        Intent ventanaGestionClientes = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(ventanaGestionClientes);
    }

    public void eliminarEmpleado(View vista) {
        AlertDialog.Builder estructuraConfirmacion = new AlertDialog.Builder(this).setTitle("CONFIRMACIÓN")
                .setMessage("Esta seguro de Eliminar el cliente seleccionado?")
                .setPositiveButton("Si, Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bdd.eliminarEmpleado(id);//Procesando la eliminacion con base del id del cliente
                        Toast.makeText(getApplicationContext(), "Eliminacion Exitosa", Toast.LENGTH_SHORT).show();
                        volver(null);//invoncando el metodo volver
                    }
                })
                .setNegativeButton("No, Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Eliminacion Cancelada", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(true);
        AlertDialog cuadroDialogo = estructuraConfirmacion.create();
        cuadroDialogo.show();
    }

    public void actualizar(View vista) {

        String cedula = txtCedulaE1.getText().toString();
        String apellido = txtApellidoE1.getText().toString();
        String nombre = txtNombreE1.getText().toString();
        String telefono = txtNumeroE1.getText().toString();
        String direccion = txtDireccionE1.getText().toString();
        String sueldo = txtSueldoE1.getText().toString();
        String cargo = txtCargoE1.getText().toString();

        if (!cedula.equals("") && !apellido.equals("") && !nombre.equals("") && !telefono.equals("") && !direccion.equals("") && !sueldo.equals("")&& !cargo.equals("")) {
            if (validacionCedula(cedula) != false) {
                Bdd.actualizarEmpleado(cedula, apellido, nombre, telefono, direccion, sueldo, cargo, id);//Procesando la actualizacion en la bdd

                Toast.makeText(getApplicationContext(), "Se Actualizaron los datos Correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Ingrese una cedula de identidad valida",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            //Presentando un mensaje cuando un campo este vacio
            Toast.makeText(getApplicationContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
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
