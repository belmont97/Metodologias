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

public class EditarMateria extends AppCompatActivity {

    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    SQLiteDatabase database;
    private EditText idNRC;
    private EditText idCARRERA;
    private EditText idEE;
    private EditText idBLOQUE;
    private EditText idSECCION;
    private EditText idPERSONAL;
    private String idNumNRC,idTextCarrera;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    AlertDialog.Builder alerta3;
    AlertDialog.Builder alerta4;
    private Button editar;
    private ImageButton buscar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_materia);

        buscar = (ImageButton) findViewById(R.id.btBuscarMateria);

        editar = (Button) findViewById(R.id.btAgregarMateria);

        alerta = new AlertDialog.Builder(this);
        alerta2 = new AlertDialog.Builder(this);
        alerta3 = new AlertDialog.Builder(this);
        alerta4 = new AlertDialog.Builder(this);

        idNRC = (EditText) findViewById(R.id.editIdNRC);
        idCARRERA = (EditText) findViewById(R.id.editIdCarrera);
        idEE = (EditText) findViewById(R.id.editIdEE);
        idBLOQUE = (EditText) findViewById(R.id.editIdBloque);
        idSECCION = (EditText) findViewById(R.id.editIdSeccion);
        idPERSONAL = (EditText) findViewById(R.id.editIdPersonal);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.setMessage("¿El NRC ingresado es correcto?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(idNRC.length() == 0 ){
                                    alerta2.setMessage("¡Campo vacío sin rellenar!");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int idNRCEntero = Integer.parseInt(idNRC.getText().toString());
                                        try {
                                            String sql = "select CARRERA, EE, BLOQUE, SECCION, IDPERSONAL from Materias where NRC=" + idNRCEntero +";";
                                            Cursor c = database.rawQuery(sql, null);

                                            int idCarreraEntero = c.getColumnIndex("CARRERA");
                                            int idEEEntero = c.getColumnIndex("EE");
                                            int idBloqueEntero = c.getColumnIndex("BLOQUE");
                                            int idSeccionEntero = c.getColumnIndex("SECCION");
                                            int idPersonalEntero = c.getColumnIndex("IDPERSONAL");


                                            while(c.moveToNext()){
                                                idNumNRC = idNRC.getText().toString();
                                                idTextCarrera = c.getString(idCarreraEntero);

                                                idCARRERA.setText(idTextCarrera);
                                                idEE.setText(c.getString(idEEEntero));
                                                idBLOQUE.setText(c.getString(idBloqueEntero));
                                                idSECCION.setText(c.getString(idSeccionEntero));
                                                idPERSONAL.setText(c.getString(idPersonalEntero));
                                            }
                                             if (idTextCarrera.length() == 0){
                                                alerta2.setMessage("¡El NRC ingresado no existe!");
                                                AlertDialog alert = alerta2.create();
                                                alert.setTitle("¡Alerta!");
                                                alert.show();
                                            }
                                            Toast.makeText(EditarMateria.this, "¡Consulta éxitosa!", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e){
                                            alerta2.setMessage("¡El NRC ingresado no existe!");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Alerta!");
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
                alerta3.setMessage("¿Los datos ingresados son correctos?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(idNRC.length() == 0 || idCARRERA.length() == 0 || idEE.length() == 0 || idBLOQUE.length() == 0|| idSECCION.length() == 0){

                                    alerta4.setMessage("¡Hay campos vacíos sin rellenar!");
                                    AlertDialog alert = alerta4.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        int numNRC, numNRC2 ,numBloq, numSecc, numIdPerson;


                                        numNRC = Integer.parseInt(idNumNRC);
                                        numNRC2 = Integer.parseInt(idNRC.getText().toString());
                                        numBloq = Integer.parseInt(idBLOQUE.getText().toString());
                                        numSecc = Integer.parseInt(idSECCION.getText().toString());
                                        numIdPerson = Integer.parseInt(idPERSONAL.getText().toString());

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("UPDATE Materias SET NRC=" + numNRC2 +", CARRERA='" + idCARRERA.getText().toString()+"',EE='" + idEE.getText().toString()+"',BLOQUE=" + numBloq +", SECCION=" + numSecc+", IDPERSONAL="+ numIdPerson +" where NRC = " + numNRC+" ;");

                                            database.setTransactionSuccessful();
                                            alerta4.setMessage("¡Materia modificada con éxito!");
                                            AlertDialog alert = alerta4.create();
                                            alert.setTitle("¡Aviso!");
                                            alert.show();
                                        }catch (Exception e){
                                            alerta4.setMessage("¡Al modificar materia, por favor vuelva a intentarlo!");
                                            AlertDialog alert = alerta4.create();
                                            alert.setTitle("¡Error!");
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