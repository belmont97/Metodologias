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
import android.widget.Toast;

public class EditarHorario extends AppCompatActivity {

    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    SQLiteDatabase database;
    private EditText idNRC, idSalon, idLunes, idMartes, idMiercoles, idJueves, idViernes;
    private Button editar;
    private ImageButton buscar;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    AlertDialog.Builder alerta3;
    AlertDialog.Builder alerta4;
    String numNrc, numSalon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_horario);

        idNRC = (EditText) findViewById(R.id.idNRC);
        idSalon = (EditText) findViewById(R.id.idSalon);
        idLunes = (EditText) findViewById(R.id.idLunes);
        idMartes = (EditText) findViewById(R.id.idMartes);
        idMiercoles = (EditText) findViewById(R.id.idMiercoles);
        idJueves = (EditText) findViewById(R.id.idJueves);
        idViernes = (EditText) findViewById(R.id.idViernes);

        editar = (Button) findViewById(R.id.idagregarHorario);
        buscar = (ImageButton) findViewById(R.id.btBuscarHorario);

        alerta = new AlertDialog.Builder(this);
        alerta2 = new AlertDialog.Builder(this);
        alerta3 = new AlertDialog.Builder(this);
        alerta4 = new AlertDialog.Builder(this);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.setMessage("¿El NRC y número de salón son correctos?")
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
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READONLY);
                                        String dato;
                                        int idNRCEntero = Integer.parseInt(idNRC.getText().toString());

                                        try {
                                            String sql = "select LUNES, MARTES, MIERCOLES, JUEVES, VIERNES from Horario where IDNRC="+idNRCEntero+" and IDSALON = '" + idSalon.getText().toString() +"';";
                                            Cursor c = database.rawQuery(sql, null);

                                            int LunesEntero = c.getColumnIndex("LUNES");
                                            int MartesEntero = c.getColumnIndex("MARTES");
                                            int MiercolesEntero = c.getColumnIndex("MIERCOLES");
                                            int JuevesEntero = c.getColumnIndex("JUEVES");
                                            int ViernesEntero = c.getColumnIndex("VIERNES");

                                            while(c.moveToNext()){
                                                dato = c.getString(LunesEntero);
                                                numNrc = idNRC.getText().toString();
                                                numSalon = idSalon.getText().toString();
                                                idLunes.setText(c.getString(LunesEntero));
                                                idMartes.setText(c.getString(MartesEntero));
                                                idMiercoles.setText(c.getString(MiercolesEntero));
                                                idJueves.setText(c.getString(JuevesEntero));
                                                idViernes.setText(c.getString(ViernesEntero));

                                                if (dato.length() == 0){
                                                    alerta2.setMessage("¡El horario ingresado no existe!");
                                                    AlertDialog alert = alerta2.create();
                                                    alert.setTitle("¡Error!");
                                                    alert.show();
                                                }
                                            }
                                            Toast.makeText(EditarHorario.this, "¡Consulta éxitosa!", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e){
                                            alerta2.setMessage("¡El horario ingresado no existe!");
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
                                if(idNRC.length() == 0 || idSalon.length() == 0){
                                    alerta4.setMessage("Campos vacíos");
                                    AlertDialog alert = alerta4.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int idNRCEntero = Integer.parseInt(idNRC.getText().toString());
                                        int idNRC2 = Integer.parseInt(numNrc);


                                        String salon, lunes, martes, miercoles, jueves, viernes;

                                        salon = idSalon.getText().toString();
                                        lunes = idLunes.getText().toString();
                                        martes = idMartes.getText().toString();
                                        miercoles = idMiercoles.getText().toString();
                                        jueves = idJueves.getText().toString();
                                        viernes = idViernes.getText().toString();

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("UPDATE Horario SET IDNRC="+ idNRCEntero+",IDSALON='" + salon+ "', LUNES='" + lunes+"',MARTES='"+ martes +"', MIERCOLES='" + miercoles +"',JUEVES='" +jueves +"', VIERNES='"+viernes +"' where IDNRC="+idNRC2+" and IDSALON='" +numSalon +"';");

                                            database.setTransactionSuccessful();
                                            alerta4.setMessage("¡Horario modificado con éxito!");
                                            AlertDialog alert = alerta4.create();
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
                                            alerta2.setMessage("Al modificar el horario, por favor vuelva a intentarlo");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("Error");
                                            alert.show();
                                        }finally {
                                            database.endTransaction();
                                        }
                                    }catch (Exception e){
                                        alerta4.setMessage("Campos vacíos");
                                        AlertDialog alert = alerta4.create();
                                        alert.setTitle("¡Alerta!");
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