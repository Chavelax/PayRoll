package com.utci.app4p;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {
    private static final String nombreBdd="bdd_rol"; //define el nombre de la base de datos
    private static final int versionBdd=1; //definiendo la version de la base de datos
    private static final String tablaUsuario="create table usuario(id_usu integer primary key autoincrement, " +
            "apellido_usu text, nombre_usu text, email_usu text, password_usu text, ci_usu text)";
    private static final String tablaEmpleado="create table empleados(id_emple integer primary key autoincrement," +
            "cedula_emple text, apellido_emple text, nombre_emple text, telefono_emple text, direccion_emple text, sueldo_emple text, cargo_emple text, hextra_emple text, hsuple_emple text, stotal_emple text)";//definiendo la estructura de la tabla usuarios
//definiendo la estructura de la tabla usuarios

    public BaseDatos (Context contexto){
        super(contexto,nombreBdd,null,versionBdd);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(tablaUsuario);
        db.execSQL(tablaEmpleado);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");//elimincacion de la version anterior de la tabla usuarios se puee usar otro comando Dll como alter table
        db.execSQL(tablaUsuario);
        db.execSQL("DROP TABLE IF EXISTS empleados"); //Eliminacion de la version anterior de la tabla cliente
        db.execSQL(tablaEmpleado);//Ejecucion del codigo para crear la tabla cliente con su nueva estructura

    }
    public boolean agregarUsuarios(String apellido, String nombre, String email, String password, String cedula){
        SQLiteDatabase miBdd=getWritableDatabase(); //llamando a la base de datos en el objeto mi Ddd
        if(miBdd != null){ //validando que la base de datos exista(q no sea nula)
            miBdd.execSQL("insert into usuario(apellido_usu, nombre_usu, email_usu, password_usu, ci_usu) " +
                    "values('"+apellido+"','"+nombre+"','"+email+"','"+password+"','"+cedula+"')");    //ejecutando la sentencia de insercion de SQL
            miBdd.close(); //cerrando la conexion a la base de datos.
            return true; // valor de retorno si se inserto exitosamente.
        }
        return false; //retorno cuando no existe la BDD
    }
    public Cursor obtenerUsuarioPorEmailPassword(String email, String password){
        SQLiteDatabase miBdd=getWritableDatabase(); // llamado a la base de datos
        //crear un cursor donde inserto la consulta sql y almaceno los resultados en el objeto usuario
        Cursor usuario = miBdd.rawQuery("select * from usuario where " +
                "email_usu='"+email+"' and password_usu = '"+password+"';", null);

        //validar si existe o no la consulta
        if(usuario.moveToFirst()){ //metodo movetofirst nueve al primer elemento encontrado si hay el usuario
            return usuario; //retornamos los datos encontrados
        }else{
            //no se encuentra informacion de usuaio -> no existe o porque el email y password son incorrectos
            return null; //devuelvo null si no hay
        }
    }
    public boolean AgregarEmpleado(String cedula, String apellido, String nombre, String telefono, String direccion, String sueldo, String cargo, String hextra, String hsuple, String stotal){
        SQLiteDatabase miBdd=getWritableDatabase();
        if (miBdd!=null){
            miBdd.execSQL("insert into empleados(cedula_emple, apellido_emple, nombre_emple,telefono_emple, direccion_emple, sueldo_emple,cargo_emple,hextra_emple,hsuple_emple,stotal_emple) " +
                    "values  ('"+cedula+"','"+apellido+"','"+nombre+"','"+telefono+"','"+direccion+"','"+sueldo+"','"+cargo+"','"+hextra+"','"+hsuple+"','"+stotal+"');");
            miBdd.close();
            return true;
        }
        return false;
    }

    public Cursor obtenerEmpleado(){
        SQLiteDatabase miBdd= getWritableDatabase();//objeto para manejar la base de datos
        //consultando los clientes en la base de datos y guardandolos en un cursor
        Cursor empleados=miBdd.rawQuery("select * from empleados; ", null);

        if (empleados.moveToFirst()){
            miBdd.close();
            return empleados; //retornar el cursor que contiene el listado de cliente
        }else {
            return null;
        }
    }

    //Metodo para actualizar un registro de cliente
    public boolean actualizarEmpleado(String cedula, String apellido, String nombre, String telefono, String direccion, String sueldo, String cargo, String hextra, String hsuple, String stotal, String id){
        SQLiteDatabase miBdd=getWritableDatabase();
        if (miBdd!=null){//validando que la base de datos en realidad existe
            //Proceso de actualizacion
            miBdd.execSQL("update empleados set cedula_emple='"+cedula+"', " +
                    "apellido_emple='"+apellido+"', nombre_emple='"+nombre+"', " +
                    "telefono_emple='"+telefono+"',direccion_emple='"+direccion+"',sueldo_emple='"+sueldo+"',cargo_emple='"+cargo+"',hextra_emple='"+hextra+"',hsuple_emple='"+hsuple+"',stotal_emple='"+stotal+"' where id_emple="+id);
            miBdd.close();
            return true;
        }
        return false;
    }


    //metodo para eliminar un registro de clientes
    public boolean eliminarEmpleado(String id){
        SQLiteDatabase miBdd=getWritableDatabase();
        if (miBdd!=null){
            miBdd.execSQL("delete from empleados where id_emple="+id);
            miBdd.close();
            return true;
        }
        return false;
    }


}
