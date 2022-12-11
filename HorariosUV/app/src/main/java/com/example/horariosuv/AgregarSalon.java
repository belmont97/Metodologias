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
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AgregarSalon extends AppCompatActivity {

    String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    SQLiteDatabase database;
    private EditText idSalon;
    private Button agregar;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    private RadioGroup selectEdificio;
    private RadioButton edificio;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_salon);
        idSalon = (EditText) findViewById(R.id.idNumSalon);
        agregar = (Button) findViewById(R.id.btAgregarSalon);
        selectEdificio = (RadioGroup) findViewById(R.id.idEdificios);


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
                                if(idSalon.length() == 0 ){

                                    alerta2.setMessage("Hay campos sin rellenar");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Aviso!");
                                    alert.show();
                                    dialogInterface.cancel();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);


                                        int selectRadioButton = selectEdificio.getCheckedRadioButtonId();
                                        edificio = findViewById(selectRadioButton);

                                        String idNumSalonText, idEdificioText;

                                        idNumSalonText = idSalon.getText().toString();
                                        idEdificioText = edificio.getText().toString();

                                        database.beginTransaction();

                                        try {
                                            database.execSQL("insert into Salon values('" + idNumSalonText +"', '" + idEdificioText +"');");

                                            database.setTransactionSuccessful();
                                            alerta2.setMessage("Salón agregado con éxito");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Aviso!");
                                            alert.show();

                                            idSalon.setText("");
                                            edificio.setChecked(false);
                                        }catch (Exception e){

                                            alerta2.setMessage("Número de salón ocupado, favor de ingresar otro");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Error!");
                                            alert.show();
                                        }finally {
                                            database.endTransaction();
                                        }

                                    }catch (Exception e){

                                        alerta2.setMessage(e.getMessage());
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