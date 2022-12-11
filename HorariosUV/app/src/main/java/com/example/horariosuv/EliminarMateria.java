package com.example.horariosuv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EliminarMateria extends AppCompatActivity {

    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    SQLiteDatabase database;
    private EditText idNRC;
    private Button eliminar;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_materia);

        idNRC = (EditText) findViewById(R.id.idNRCEliminar);

        eliminar = (Button) findViewById(R.id.btEliminarMateria);

        alerta = new AlertDialog.Builder(this);
        alerta2 = new AlertDialog.Builder(this);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.setMessage("¿El NRC ingresado es correcto?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(idNRC.length() == 0){
                                    alerta2.setMessage("Campo vacío");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();

                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null,SQLiteDatabase.OPEN_READWRITE);

                                        int idNRCENtero = Integer.parseInt(idNRC.getText().toString());

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("DELETE FROM Materias WHERE NRC=" + idNRCENtero+";");

                                            database.setTransactionSuccessful();

                                            alerta2.setMessage("¡Materia eliminada de manera éxitosa!");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Aviso!");
                                            alert.show();
                                            idNRC.setText("");
                                        }catch (Exception e){
                                            alerta2.setMessage("¡El NRC ingresado no existe!");
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