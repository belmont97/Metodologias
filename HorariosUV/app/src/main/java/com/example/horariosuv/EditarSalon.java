package com.example.horariosuv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EditarSalon extends AppCompatActivity {

    SQLiteDatabase database;
    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    private Button editar;
    private ImageButton buscar;
    private EditText idSalon;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    AlertDialog.Builder alerta3;
    AlertDialog.Builder alerta4;
    private String numSalon;
    private RadioButton edificioEco;
    private RadioButton edificioFei;
    private RadioGroup seleccionEdificio;
    private String comprobar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_salon);

        buscar = (ImageButton) findViewById(R.id.btBuscarSalon);

        editar = (Button) findViewById(R.id.btAgregarSalon);

        idSalon = (EditText) findViewById(R.id.idNumSalon);
        edificioEco = (RadioButton) findViewById(R.id.idEconex);
        edificioFei = (RadioButton) findViewById(R.id.idFei);
        seleccionEdificio = (RadioGroup) findViewById(R.id.idEdificios);

        alerta = new AlertDialog.Builder(this);
        alerta2 = new AlertDialog.Builder(this);
        alerta3 = new AlertDialog.Builder(this);
        alerta4 = new AlertDialog.Builder(this);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.setMessage("¿El número de salón es correcto?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(idSalon.length() == 0){
                                    alerta2.setMessage("Campo sin rellenar");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Aviso!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READONLY);

                                        numSalon = idSalon.getText().toString();
                                        try {
                                            String sql = "select NUMSALON, EDIFICIO from Salon where NUMSALON = '" + numSalon+"'";
                                            Cursor c = database.rawQuery(sql ,null);

                                            int idNumSalon = c.getColumnIndex("NUMSALON");
                                            int idEdifcioEntero = c.getColumnIndex("EDIFICIO");


                                            while (c.moveToNext()){
                                                numSalon = c.getString(idNumSalon);
                                                comprobar = c.getString(idEdifcioEntero);
                                                if(edificioEco.getText().equals(c.getString(idEdifcioEntero))){
                                                    edificioEco.setChecked(true);
                                                } else if(edificioFei.getText().equals(c.getString(idEdifcioEntero))){
                                                    edificioFei.setChecked(true);
                                                }
                                            }
                                            if (comprobar.length() == 0){
                                                alerta2.setMessage("El número de salón ingresado no existe");
                                                AlertDialog alert = alerta2.create();
                                                alert.setTitle("¡Aviso!");
                                                alert.show();
                                            }
                                            Toast.makeText(EditarSalon.this, "¡Consulta éxitosa!", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e){
                                            alerta2.setMessage("El número de salón ingresado no existe");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Aviso!");
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
                                if(idSalon.length() == 0 || edificioFei.isSelected() || edificioEco.isSelected()){
                                    alerta4.setMessage("Hay campos sin rellenar");
                                    AlertDialog alert = alerta4.create();
                                    alert.setTitle("¡Aviso!");
                                    alert.show();
                                    dialogInterface.cancel();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int idRadioButton = seleccionEdificio.getCheckedRadioButtonId();
                                        edificioEco = findViewById(idRadioButton);

                                        String idSalonText, Edificio;

                                        Edificio = edificioEco.getText().toString();

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("UPDATE Salon SET NUMSALON = '" + idSalon.getText().toString() +"', EDIFICIO='" + Edificio+"' where NUMSALON = '" + numSalon+"';");


                                            database.setTransactionSuccessful();
                                            alerta4.setMessage("¡Salón modificado éxitosamente!");
                                            AlertDialog alert = alerta4.create();
                                            alert.setTitle("¡Aviso!");
                                            alert.show();

                                            idSalon.setText("");
                                            edificioEco.setChecked(false);

                                        }catch (Exception e){
                                            alerta4.setMessage("Error al modificar salón, por favor vuelva a intentarlo");
                                            AlertDialog alert = alerta4.create();
                                            alert.setTitle("¡Alerta!");
                                            alert.show();
                                        }finally {
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