package com.example.horariosuv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditarAcademico extends AppCompatActivity {

    SQLiteDatabase database;
    private String ubicacion = "data/data/com.example.horariosuv/DatabaseUV";
    private ImageButton buscar;
    private Button editar;
    private EditText idPersonal;
    private EditText idNombre;
    private EditText idApellidoP;
    private EditText idApellidoM;
    private String numero, nom, apep, apem;
    AlertDialog.Builder alerta;
    AlertDialog.Builder alerta2;
    AlertDialog.Builder alerta3;
    AlertDialog.Builder alerta4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_academico);
        buscar = (ImageButton) findViewById(R.id.imageButton19);
        idPersonal = (EditText) findViewById(R.id.idEditarPersonal);
        idNombre = (EditText) findViewById(R.id.idNombre);
        idApellidoP = (EditText) findViewById(R.id.idApellidoPaterno);
        idApellidoM = (EditText) findViewById(R.id.idApellidoMaterno);
        editar = (Button) findViewById(R.id.idGuardarAcademico);

        alerta = new AlertDialog.Builder(this);
        alerta2 = new AlertDialog.Builder(this);
        alerta3 = new AlertDialog.Builder(this);
        alerta4 = new AlertDialog.Builder(this);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.setMessage("¿Número de personal ingresado correctamente?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (idPersonal.length() == 0){

                                    alerta2.setMessage("Campo sin rellenar");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Aviso!");
                                    alert.show();

                                }else {
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READONLY);

                                        numero = idPersonal.getText().toString();
                                        int idPersonalEntero = Integer.parseInt(idPersonal.getText().toString());

                                        try {
                                            String sql = "select NUMPERSONAL, NOMBRE, APELLIDOPATERNO, APELLIDOMATERNO from Academico where NUMPERSONAL = " + idPersonalEntero +";";
                                            Cursor c = database.rawQuery(sql, null);


                                            int idNombreEntero = c.getColumnIndex("NOMBRE");
                                            int idApellidoPaterno = c.getColumnIndex("APELLIDOPATERNO");
                                            int idApellidoMaterno = c.getColumnIndex("APELLIDOMATERNO");

                                            while(c.moveToNext()){
                                                nom = c.getString(idNombreEntero);
                                                apep = c.getString(idApellidoPaterno);
                                                apem = c.getString(idApellidoMaterno);

                                                idNombre.setText(nom);
                                                idApellidoP.setText(apep);
                                                idApellidoM.setText(apem);
                                            }
                                            if(nom.length() == 0){
                                                alerta2.setMessage("El número personal ingresado no existe");
                                                AlertDialog alert = alerta2.create();
                                                alert.setTitle("¡Aviso!");
                                                alert.show();
                                            }
                                            Toast.makeText(EditarAcademico.this, "¡Consulta éxitosa!", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e){
                                            alerta2.setMessage("El número personal ingresado no existe");
                                            AlertDialog alert = alerta2.create();
                                            alert.setTitle("¡Aviso!");
                                            alert.show();
                                        }

                                    }catch (Exception e){
                                        alerta2.setMessage("Error");
                                        AlertDialog alert = alerta2.create();
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
                AlertDialog alert = alerta.create();
                alert.setTitle("Alerta");
                alert.show();
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta3.setMessage("¿Datos ingresados correctamente?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(idPersonal.length() == 0 || idNombre.length() == 0 || idApellidoP.length() == 0 || idApellidoM.length() == 0){

                                    alerta4.setMessage("Hay campos sin rellenar");
                                    AlertDialog alert = alerta4.create();
                                    alert.setTitle("¡Aviso!");
                                    alert.show();
                                    dialogInterface.cancel();
                                }else{
                                    try {
                                        database = SQLiteDatabase.openDatabase(ubicacion, null, SQLiteDatabase.OPEN_READWRITE);

                                        String idNom, idApep, idApem;

                                        idNom = idNombre.getText().toString();
                                        idApep = idApellidoP.getText().toString();
                                        idApem = idApellidoM.getText().toString();

                                        int idPersonaEntero = Integer.parseInt(idPersonal.getText().toString());
                                        int idAntes = Integer.parseInt(numero);

                                        database.beginTransaction();
                                        try {
                                            database.execSQL("UPDATE Academico SET NUMPERSONAL="+ idPersonaEntero +",NOMBRE = '" + idNom +"', APELLIDOPATERNO = '" + idApep+"', APELLIDOMATERNO='" + idApem+"' where NUMPERSONAL=" + idAntes+";");

                                            database.setTransactionSuccessful();
                                            alerta4.setMessage("¡Académico modificado éxitosamente!");
                                            AlertDialog alert = alerta4.create();
                                            alert.setTitle("¡Aviso!");
                                            alert.show();

                                            idPersonal.setText("");
                                            idNombre.setText("");
                                            idApellidoP.setText("");
                                            idApellidoM.setText("");

                                        }catch (Exception e){
                                            alerta4.setMessage("Error al modificar académico, por favor vuelva a intentarlo");
                                            AlertDialog alert = alerta4.create();
                                            alert.setTitle("¡Alerta!");
                                            alert.show();
                                        }finally {
                                            database.endTransaction();
                                        }
                                    }catch (Exception e){

                                        alerta4.setMessage("Error");
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