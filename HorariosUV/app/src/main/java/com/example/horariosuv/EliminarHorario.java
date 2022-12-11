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

public class EliminarHorario extends AppCompatActivity {

    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    SQLiteDatabase database;
    private EditText idNrc, idSalon;
    private Button eliminar;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_horario);

        idNrc = (EditText) findViewById(R.id.idEliminarNRC);
        idSalon = (EditText) findViewById(R.id.idSalonEliminar);

        eliminar = (Button) findViewById(R.id.btEliminar);

        alerta = new AlertDialog.Builder(this);
        alerta2 = new AlertDialog.Builder(this);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.setMessage("¿Esta seguro de eliminar el horario?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(idNrc.length()==0 || idSalon.length() == 0){
                                    alerta2.setMessage("Campos vacíos");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int idNRCENtero = Integer.parseInt(idNrc.getText().toString());

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("DELETE FROM Horario where IDNRC=" + idNRCENtero+ " and IDSALON='"+ idSalon.getText().toString() +"';");

                                            database.setTransactionSuccessful();
                                            alerta2.setMessage("Horario eliminado de manera éxitosa");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Aviso!");
                                            alert.show();
                                        }catch (Exception e){
                                            alerta2.setMessage("El horario ingresado no existe");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Error!");
                                            alert.show();
                                        }
                                    }
                                    catch (Exception e){

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