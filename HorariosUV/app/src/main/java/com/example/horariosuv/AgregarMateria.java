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
import android.widget.ImageButton;

public class AgregarMateria extends AppCompatActivity {

    SQLiteDatabase database;
    private EditText idNRC;
    private EditText idCarrera;
    private EditText idEE;
    private EditText idBloque;
    private EditText idSección;
    private EditText idPersonal;
    String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    private Button agregar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_materia);

        agregar = (Button) findViewById(R.id.btAgregarMateria);

        idNRC = (EditText) findViewById(R.id.editIdNRC);
        idCarrera = (EditText) findViewById(R.id.editIdCarrera);
        idEE = (EditText) findViewById(R.id.editIdEE);
        idBloque = (EditText) findViewById(R.id.editIdBloque);
        idSección = (EditText) findViewById(R.id.editIdSeccion);
        idPersonal = (EditText) findViewById(R.id.editIdPersonal);

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
                                if(idNRC.length() == 0 || idCarrera.length() == 0 || idEE.length() == 0 || idBloque.length() == 0|| idSección.length() == 0){

                                    alerta2.setMessage("¡Hay campos vacíos sin rellenar!");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        String carrera, ee;

                                        carrera = idCarrera.getText().toString();
                                        ee = idEE.getText().toString();

                                        int nrc, bloque, seccion, idpersonal;

                                        nrc = Integer.parseInt(idNRC.getText().toString());
                                        bloque = Integer.parseInt(idBloque.getText().toString());
                                        seccion = Integer.parseInt(idSección.getText().toString());
                                        idpersonal = Integer.parseInt(idPersonal.getText().toString());

                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("insert into Materias values("+ nrc +", '"+ carrera +"', '"+ ee +"', "+bloque+", " +seccion +","+ idpersonal + ");");

                                            database.setTransactionSuccessful();

                                            alerta2.setMessage("¡Materia agregada con éxito!");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Aviso!");
                                            alert.show();

                                            idNRC.setText("");
                                            idCarrera.setText("");
                                            idEE.setText("");
                                            idBloque.setText("");
                                            idSección.setText("");
                                            idPersonal.setText("");
                                        }catch (Exception e){

                                            alerta2.setMessage("¡La materia ingresada ya existe!");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Error!");
                                            alert.show();
                                        }finally {
                                            database.endTransaction();
                                        }
                                    }catch (Exception e){

                                        alerta2.setMessage("");
                                        AlertDialog alert = alerta2.create();
                                        alert.setTitle("¡Error!");
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