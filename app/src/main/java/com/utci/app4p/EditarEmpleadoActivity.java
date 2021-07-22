package com.utci.app4p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditarEmpleadoActivity extends AppCompatActivity {
    ListView IstClientes;
    ArrayList<String> listaClientes= new ArrayList<>();
    Cursor clienteObtenidos;
    BaseDatos Bdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empleado);
        IstClientes=(ListView) findViewById(R.id.IstClientes);
        Bdd=new BaseDatos(getApplicationContext()); //Instanciando el objeto para llamar a los metodos de la Base de Datos
        consultarDatos(); //llamando al metodo para cargar los datos de clientes ene l list view
        IstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //programacion de acciones cuando se da clic de un elemento de la lista cliente
                clienteObtenidos.moveToPosition(position);
                String idCliente=clienteObtenidos.getString( 0);
                String cedulaCliente=clienteObtenidos.getString( 1);
                String apellidoCliente=clienteObtenidos.getString( 2);
                String nombreCliente=clienteObtenidos.getString( 3);
                String telefonoCliente=clienteObtenidos.getString( 4);
                String direccionCliente=clienteObtenidos.getString( 5);
                String sueldo=clienteObtenidos.getString( 6);
                String cargo=clienteObtenidos.getString( 7);
                String hextra=clienteObtenidos.getString( 8);
                String hsuple=clienteObtenidos.getString( 9);
                String stotal=clienteObtenidos.getString( 10);


                //Toast.makeText(getApplicationContext(),idCliente+"-"+cedulaCliente+"-"+apellidoCliente+"-"+
                //nombreCliente+"-"+telefonoCliente+"-"+direccionCliente,Toast.LENGTH_LONG).show();
                Intent ventanaEditarCliente= new Intent(getApplicationContext(),CrudEmpleado.class);
                ventanaEditarCliente.putExtra("id",idCliente);
                ventanaEditarCliente.putExtra("cedula",cedulaCliente);
                ventanaEditarCliente.putExtra("apellido",apellidoCliente);
                ventanaEditarCliente.putExtra("nombre",nombreCliente);
                ventanaEditarCliente.putExtra("telefono",telefonoCliente);
                ventanaEditarCliente.putExtra("direccion",direccionCliente);
                ventanaEditarCliente.putExtra("sueldo",sueldo);
                ventanaEditarCliente.putExtra("cargo",cargo);
                ventanaEditarCliente.putExtra("hextra",hextra);
                ventanaEditarCliente.putExtra("hsuple",hsuple);
                ventanaEditarCliente.putExtra("stotal",stotal);
                startActivity(ventanaEditarCliente);
                finish();
            }
        });

    }




    //metodo par aconsultar los clientes existenetes en sqlite y presentarlos en una lista
    public void consultarDatos(){
        listaClientes.clear();
        clienteObtenidos=Bdd.obtenerEmpleado();//consultando clientes y guardandolos en un cursor
        if (clienteObtenidos!=null){
            do {
                String id=clienteObtenidos.getString(0).toString();
                String apellido=clienteObtenidos.getString(2).toString();
                String nombre=clienteObtenidos.getString(3).toString();
                listaClientes.add(id+": "+apellido+" "+nombre);
                ArrayAdapter<String> adaptadorClientes= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listaClientes);
                IstClientes.setAdapter(adaptadorClientes);
            }while (clienteObtenidos.moveToNext());
        }else {
            Toast.makeText(getApplicationContext(),"No Existen clientes registrados",
                    Toast.LENGTH_LONG).show();
        }

    }
    public void abrirRegistroEmpleado(View vista){ //metodo para abrir ventana de registro
        Intent pantallaRegistroE= new Intent(getApplicationContext(),RegistroEmpleadosActivity.class); // invocar registro Empleados
        startActivity(pantallaRegistroE); //iniciamos la pantalla de Registro
    }

}