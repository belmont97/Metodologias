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

public class AgregarHorario extends AppCompatActivity {

    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    SQLiteDatabase database;
    private EditText idNRC, idSalon, idLunes, idMartes, idMiercoles, idJueves, idViernes;
    private Button agregar;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_horario);

        idNRC = (EditText) findViewById(R.id.idNRC);
        idSalon = (EditText) findViewById(R.id.idSalon);
        idLunes = (EditText) findViewById(R.id.idLunes);
        idMartes = (EditText) findViewById(R.id.idMartes);
        idMiercoles = (EditText) findViewById(R.id.idMiercoles);
        idJueves = (EditText) findViewById(R.id.idJueves);
        idViernes = (EditText) findViewById(R.id.idViernes);

        agregar = (Button) findViewById(R.id.idagregarHorario);

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
                                if(idNRC.length() == 0 || idSalon.length() == 0){
                                    alerta2.setMessage("Campos vacíos");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion,null, SQLiteDatabase.OPEN_READWRITE);

                                        int idNRCEntero = Integer.parseInt(idNRC.getText().toString());

                                        String salonText, Lunes, Martes, Miercoles, Jueves, Viernes;

                                        salonText = idSalon.getText().toString();
                                        Lunes = idLunes.getText().toString();
                                        Martes = idMartes.getText().toString();
                                        Miercoles = idMiercoles.getText().toString();
                                        Jueves = idJueves.getText().toString();
                                        Viernes = idViernes.getText().toString();

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES,MIERCOLES,JUEVES,VIERNES) values" +
                                                    "("+ idNRCEntero+",'"+ salonText +"','" + Lunes+"','" + Martes+ "','" + Miercoles+"','" + Jueves+"', '"+ Viernes+"');");

                                            database.setTransactionSuccessful();
                                            alerta2.setMessage("¡Horario agregado con éxito!");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Aviso!");
                                            alert.show();

                                            idNRC.setText("");
                                            idSalon.setText("");
                                            idLunes.setText("");
                                            idMartes.setText("");
                                            idMiercoles.setText("");
                                            idJueves.setText("");
                                            idViernes.setText("");
                                        }catch (Exception e){
                                            alerta2.setMessage("¡El horario ya existe!");
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