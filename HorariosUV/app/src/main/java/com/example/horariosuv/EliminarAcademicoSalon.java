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

public class EliminarAcademicoSalon extends AppCompatActivity {

    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    SQLiteDatabase database;
    private EditText idPersonal;
    private EditText idSalon;
    private Button eliminar;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_academico_salon);

        idPersonal = (EditText) findViewById(R.id.idPersonalAcademicoSalon);
        idSalon = (EditText) findViewById(R.id.idSalonAcademicoSalon);

        eliminar = (Button) findViewById(R.id.btEliminarAcademicoSalon);

        alerta = new AlertDialog.Builder(this);
        alerta2 = new AlertDialog.Builder(this);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.setMessage("¿Esta seguro de eliminar la relación?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(idPersonal.length() == 0 || idSalon.length()==0){
                                    alerta2.setMessage("Campos vacíos");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int idPersonalEntero = Integer.parseInt(idPersonal.getText().toString());

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("DELETE FROM AcademicoSalon where IDPERSONAL="+idPersonalEntero+" and IDSALON='" + idSalon.getText().toString()+"';");

                                            database.setTransactionSuccessful();
                                            alerta2.setMessage("¡Relación eliminada con éxito");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Aviso!");
                                            alert.show();
                                        }catch (Exception e){
                                            alerta2.setMessage("¡La relación ingresada no existe!");
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