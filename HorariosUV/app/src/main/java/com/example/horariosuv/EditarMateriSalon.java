package com.example.horariosuv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditarMateriSalon extends AppCompatActivity {

    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    private EditText idNRC, idSalon;
    SQLiteDatabase database;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    AlertDialog.Builder alerta3;
    AlertDialog.Builder alerta4;
    private Button editar;
    private ImageButton buscar;
    private String numNRC, numSalon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_materi_salon);

        editar = (Button) findViewById(R.id.btEditarMateriaSalon);
        buscar = (ImageButton) findViewById(R.id.buscarMateria);


        idNRC = (EditText) findViewById(R.id.textNRC);
        idSalon = (EditText) findViewById(R.id.textSalon);

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
                                if(idNRC.length() == 0 || idSalon.length()==0){
                                    alerta2.setMessage("Campos vacíos");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int nrcEntero = Integer.parseInt(idNRC.getText().toString());

                                        try {
                                            String sql = "select IDNRC, IDSALON from MateriaSalon where IDNRC="+nrcEntero+" and IDSALON='" + idSalon.getText().toString() +"';";
                                            Cursor  c = database.rawQuery(sql, null);

                                            String dato1, dato2;

                                            int idNRCentero = c.getColumnIndex("IDNRC");
                                            int idSalonentero = c.getColumnIndex("IDSALON");

                                            while (c.moveToNext()){
                                                dato1 = c.getString(idNRCentero);
                                                dato2 = c.getString(idSalonentero);

                                                if (dato1.length() == 0 || dato2.length() == 0){
                                                    alerta2.setMessage("La relación no existe");
                                                    AlertDialog alert = alerta2.create();
                                                    alert.setTitle("¡Error!");
                                                    alert.show();
                                                }
                                                numNRC = dato1;
                                                numSalon = dato2;

                                                idNRC.setText(dato1);
                                                idSalon.setText(dato2);
                                            }
                                            Toast.makeText(EditarMateriSalon.this, "¡Consulta éxitosa!", Toast.LENGTH_SHORT).show();

                                        }catch (Exception e){
                                            alerta2.setMessage("La relación ya existe");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Error!");
                                            alert.show();
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

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta3.setMessage("¿Los datos son correctos?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(idNRC.length() == 0 || idSalon.length() == 0){
                                    alerta4.setMessage("Campos vacíos");
                                    AlertDialog alert = alerta4.create();
                                    alert.setTitle("¡Error!");
                                    alert.show();
                                }else {
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int numNRCENtero = Integer.parseInt(idNRC.getText().toString());
                                        int num2 = Integer.parseInt(numNRC);

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("UPDATE MateriaSalon SET IDNRC="+numNRCENtero+", IDSALON='" + idSalon.getText().toString()+"' where IDNRC="+num2+" and IDSALON='" + numSalon+"';");

                                            database.setTransactionSuccessful();

                                            alerta4.setMessage("La relación fue editada de manera éxitosa");
                                            AlertDialog alert = alerta4.create();
                                            alert.setTitle("¡Avíso!");
                                            alert.show();
                                            idSalon.setText("");
                                            idNRC.setText("");

                                        }catch (Exception e){
                                            alerta4.setMessage("La relación ya existe");
                                            AlertDialog alert = alerta4.create();
                                            alert.setTitle("¡Error!");
                                            alert.show();
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