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

public class AgregarMateriaSalon extends AppCompatActivity {

    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    private EditText idNRC, idSalon;
    SQLiteDatabase database;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    private Button agregar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_materia_salon);

        agregar = (Button) findViewById(R.id.btEditarMateriaSalon);

        idNRC = (EditText) findViewById(R.id.textNRC);
        idSalon = (EditText) findViewById(R.id.textSalon);

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
                                if(idNRC.length() == 0 || idSalon.length()==0){
                                    alerta2.setMessage("Campos vacíos");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int nrcEntero = Integer.parseInt(idNRC.getText().toString());

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("insert into MateriaSalon values("+ nrcEntero + ", '"+ idSalon.getText().toString()+  "');");

                                            database.setTransactionSuccessful();
                                            alerta2.setMessage("Relación fue agregada con éxito");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Alerta!");
                                            alert.show();

                                            idNRC.setText("");
                                            idSalon.setText("");
                                        }catch (Exception e){
                                            alerta2.setMessage("La relación ya existe");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Error!");
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