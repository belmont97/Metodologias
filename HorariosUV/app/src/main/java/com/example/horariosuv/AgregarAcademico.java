package com.example.horariosuv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AgregarAcademico extends AppCompatActivity {

    SQLiteDatabase database;
    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    private Button agregar;
    private EditText numPersonal;
    private EditText nombre;
    private EditText apellidop;
    private EditText apellidom;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_academico);

        agregar = (Button) findViewById(R.id.idGuardarAcademico);
        numPersonal = (EditText) findViewById(R.id.idEditarPersonal);
        nombre = (EditText) findViewById(R.id.idNombre);
        apellidop = (EditText) findViewById(R.id.idApellidoPaterno);
        apellidom = (EditText) findViewById(R.id.idApellidoMaterno);
        alerta = new AlertDialog.Builder(this);
        alerta2 = new AlertDialog.Builder(this);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    alerta.setMessage("¿Los datos ingresados son correctos?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(numPersonal.length() == 0 || nombre.length() == 0 || apellidop.length() == 0 || apellidom.length() == 0){

                                        alerta2.setMessage("Hay campos sin rellenar");
                                        AlertDialog alert = alerta2.create();
                                        alert.setTitle("¡Aviso!");
                                        alert.show();
                                        dialogInterface.cancel();
                                    }else{
                                        try {
                                            database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                            String idPersonal, idNombre, idApellidoP, idApellidoM;
                                            idPersonal = numPersonal.getText().toString();
                                            idNombre = nombre.getText().toString();
                                            idApellidoP = apellidop.getText().toString();
                                            idApellidoM = apellidom.getText().toString();

                                            int idPersonalEntero = Integer.parseInt(idPersonal);

                                            database.beginTransaction();

                                            try {
                                                database.execSQL("insert into Academico values(" + idPersonalEntero +", '" + idNombre +"', '" + idApellidoP+"', '" + idApellidoM+"');");

                                                database.setTransactionSuccessful();
                                                alerta2.setMessage("Académico agregado con éxito");
                                                AlertDialog alert = alerta2.create();
                                                alert.setTitle("¡Aviso!");
                                                alert.show();
                                                numPersonal.setText("");
                                                nombre.setText("");
                                                apellidop.setText("");
                                                apellidom.setText("");

                                            }catch (Exception e){

                                                alerta2.setMessage("Número de personal ocupado, favor de utilizar otro");
                                                AlertDialog alert = alerta2.create();
                                                alert.setTitle("¡Error!");
                                                alert.show();
                                            }finally {
                                                database.endTransaction();
                                            }

                                        }catch (Exception e){

                                            alerta2.setMessage("Error");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Alerta!");
                                            alert.show();
                                        }
                                    }

                                    dialogInterface.cancel();
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