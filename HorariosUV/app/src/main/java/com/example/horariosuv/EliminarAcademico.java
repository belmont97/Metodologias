package com.example.horariosuv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EliminarAcademico extends AppCompatActivity {

    SQLiteDatabase database;
    private Button eliminar;
    private EditText idPersonal;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_academico);

        eliminar = (Button) findViewById(R.id.idEliminarAcademico);
        idPersonal = (EditText) findViewById(R.id.idNumeroPersonal);

        alerta = new AlertDialog.Builder(this);
        alerta2 = new AlertDialog.Builder(this);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.setMessage("¿Está seguro de eliminarlo?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               if(idPersonal.length() == 0){

                                   alerta2.setMessage("Campo sin rellenar");
                                   AlertDialog alert = alerta2.create();
                                   alert.setTitle("¡Aviso!");
                                   alert.show();

                               }else{
                                   try {
                                       database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                       int idPersonalEntero = Integer.parseInt(idPersonal.getText().toString());

                                       database.beginTransaction();
                                       try {
                                           database.execSQL("delete from Academico where NUMPERSONAL = " + idPersonalEntero+";");

                                           database.setTransactionSuccessful();

                                           alerta2.setMessage("Académico eliminado con éxito");
                                           AlertDialog alert = alerta2.create();
                                           alert.setTitle("¡Aviso!");
                                           alert.show();
                                           idPersonal.setText("");

                                       }catch (Exception e){
                                           alerta2.setMessage("El número personal ingresado no existe");
                                           AlertDialog alert = alerta2.create();
                                           alert.setTitle("¡Aviso!");
                                           alert.show();

                                       }finally {
                                           database.endTransaction();
                                       }
                                   }catch (Exception e){
                                       alerta2.setMessage("Error");
                                       AlertDialog alert = alerta2.create();
                                       alert.setTitle("¡Aviso!");
                                       alert.show();

                                   }
                               }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = alerta.create();
                alert.setTitle("Alerta");
                alert.show();
            }
        });
    }

    public void botonAtras(View view){
        finish();
    }
}