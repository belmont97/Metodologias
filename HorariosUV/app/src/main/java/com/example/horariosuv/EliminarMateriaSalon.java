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

public class EliminarMateriaSalon extends AppCompatActivity {

    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    private EditText idNRC, idSalon;
    SQLiteDatabase database;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    private Button eliminar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_materia_salon);

        eliminar = (Button) findViewById(R.id.btEliminar);

        idNRC = (EditText) findViewById(R.id.idEliminarNRC);
        idSalon = (EditText) findViewById(R.id.idSalonEliminar);

        alerta = new AlertDialog.Builder(this);
        alerta2 = new AlertDialog.Builder(this);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.setMessage("¿Estas seguro de eliminar la relación?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(idNRC.length() == 0 || idSalon.length()==0){
                                    alerta2.setMessage("Campos vacíos");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int numNRC = Integer.parseInt(idNRC.getText().toString());

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("DELETE FROM MateriaSalon where IDNRC="+numNRC+" and IDSALON='" + idSalon.getText().toString()+"';");

                                            database.setTransactionSuccessful();

                                            alerta2.setMessage("Relación eliminada de manera éxitosa");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Avíso!");
                                            alert.show();
                                            idNRC.setText("");
                                            idSalon.setText("");
                                        }catch (Exception e){
                                            alerta2.setMessage("La relación no existe");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Alerta!");
                                            alert.show();
                                        }finally {
                                            database.endTransaction();
                                        }

                                    }catch (Exception e){

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