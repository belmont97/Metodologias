package com.example.horariosuv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Currency;

public class EditarAcademicoSalon extends AppCompatActivity {

    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    private EditText idSalon;
    private EditText idPersonal;
    private Button editar;
    private ImageButton buscar;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    AlertDialog.Builder alerta3;
    AlertDialog.Builder alerta4;
    private String numPersonal, numSalon;
    SQLiteDatabase database;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_academico_salon);

        idSalon = (EditText) findViewById(R.id.idPersonalAgregar);
        idPersonal = (EditText) findViewById(R.id.idSalonAgregar);

        buscar = (ImageButton) findViewById(R.id.idBuscarAcademicoSalon);

        editar = (Button) findViewById(R.id.btAgregarAcademicoSalon);

        alerta = new AlertDialog.Builder(this);
        alerta2 = new AlertDialog.Builder(this);
        alerta3 = new AlertDialog.Builder(this);
        alerta4 = new AlertDialog.Builder(this);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.setMessage("¿Los datos ingresados son correctos?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(idPersonal.length() == 0 || idSalon.length() == 0){
                                    alerta2.setMessage("Campos vacíos");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int idPersonalEntero = Integer.parseInt(idPersonal.getText().toString());
                                        String dato;
                                        try {
                                            String sql = "select IDPERSONAL, IDSALON from AcademicoSalon where IDPERSONAL="+ idPersonalEntero +" and IDSALON ='" + idSalon.getText().toString()+"';";
                                            Cursor c = database.rawQuery(sql, null);


                                            int idPersonEnte = c.getColumnIndex("IDPERSONAL");
                                            int idSalonEnte = c.getColumnIndex("IDSALON");

                                            while (c.moveToNext()){
                                                dato = c.getString(idSalonEnte);
                                                if(dato.length() == 0){
                                                    alerta2.setMessage("¡La relación no existe!");
                                                    AlertDialog alert = alerta2.create();
                                                    alert.setTitle("¡Alerta!");
                                                    alert.show();
                                                }
                                                numPersonal = idPersonal.getText().toString();
                                                numSalon = dato;
                                            }
                                            Toast.makeText(EditarAcademicoSalon.this, "¡Consulta éxitosa!", Toast.LENGTH_SHORT).show();

                                        }catch (Exception e){
                                            alerta2.setMessage("¡La relación no existe!");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Error!");
                                            alert.show();
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

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta3.setMessage("¿Los datos ingresados son correctos?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(idPersonal.length() == 0 || idSalon.length() == 0){
                                    alerta4.setMessage("Campos vacíos");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int idPersonalEntero = Integer.parseInt(idPersonal.getText().toString());
                                        int idPersonal2 = Integer.parseInt(numPersonal);

                                        database.beginTransaction();
                                        try {
                                          database.execSQL("UPDATE AcademicoSalon SET IDPERSONAL ="+ idPersonalEntero + ", IDSALON='"+ idSalon.getText().toString()+"' where IDPERSONAL="+idPersonal2+" and IDSALON ='"+ numSalon +"';");

                                          database.setTransactionSuccessful();
                                          alerta4.setMessage("¡La relación fue modificada con éxito!");
                                          AlertDialog alert = alerta4.create();
                                          alert.setTitle("¡Error!");
                                          alert.show();

                                        }catch (Exception e){
                                            alerta4.setMessage("¡La relación ya existe!");
                                            AlertDialog alert = alerta4.create();
                                            alert.setTitle("¡Error!");
                                            alert.show();
                                        } finally {
                                            database.endTransaction();
                                        }
                                    }catch (Exception e){
                                        alerta4.setMessage("");
                                        AlertDialog alert = alerta4.create();
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
                AlertDialog alert = alerta3.create();
                alert.setTitle("Alerta");
                alert.show();

            }
        });

    }

    public void botonAtras(View view){
        finish();
    }
}