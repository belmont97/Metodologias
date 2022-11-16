package com.example.aula;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ResgistroAcademico extends AppCompatActivity {

    SQLiteDatabase database;
    private AlertDialog.Builder alerta;
    private EditText numPersonal;
    private EditText nombre;
    private EditText apellidop;
    private EditText apellidom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgistro_academico);
        alerta = new AlertDialog.Builder(this);
        try {
            openDatabase();

        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void openDatabase(){
        try{
            database = SQLiteDatabase.openDatabase("data/data/com.example.aula/UniversidadVeracruzana",null,SQLiteDatabase.CREATE_IF_NECESSARY);
            Toast.makeText(this, "DATABASE Open", Toast.LENGTH_SHORT).show();
        }catch(SQLiteException e){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }

    public void btCancelar(View view){
        Intent cance = new Intent(this,Administrador.class);
        startActivity(cance);
    }

    public void registrarAcademico(View view){
        numPersonal = (EditText) findViewById(R.id.editNumPersonal);
        nombre = (EditText) findViewById(R.id.editNombre);
        apellidop = (EditText) findViewById(R.id.editApellidoP);
        apellidom = (EditText) findViewById(R.id.editApellidoM);

        int idPersonal = Integer.parseInt(numPersonal.getText().toString());

        String nombree = nombre.getText().toString();
        String apellip = apellidop.getText().toString();
        String apellim = apellidom.getText().toString();


        database.beginTransaction();
        try {
            database.execSQL("insert into Academico(NUMPERSONAL,NOMBRE,APELLIDOPATERNO,APELLIDOMATERNO) " +
                    "values("+ idPersonal +",'"+ nombree+"','"+ apellip+"', '" + apellim+"');");

            database.setTransactionSuccessful();

            Toast.makeText(this,"Resgistrado exitosamente",Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }finally {
            database.endTransaction();
        }

        database.close();
        finish();
    }
}