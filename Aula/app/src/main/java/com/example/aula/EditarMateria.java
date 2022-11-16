package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditarMateria extends AppCompatActivity {

    SQLiteDatabase database;
    private EditText editNRC;
    private EditText editCarrera;
    private EditText editEE;
    private EditText editBloque;
    private EditText editSeccion;
    private EditText editNumPerson;
    private String carrera, ee, bloque, seccion, numperson;
    private int nrc2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_materia);
        try{
            openDatabase();

        }catch (SQLiteException e){

        }
    }


    private void openDatabase(){
        try{
            database = SQLiteDatabase.openDatabase("data/data/com.example.aula/UniversidadVeracruzana",null,SQLiteDatabase.CREATE_IF_NECESSARY);
            Toast.makeText(this, "DATABASE Open", Toast.LENGTH_SHORT).show();
        }catch(SQLiteException e){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarMateria(View view){
        editNRC = (EditText) findViewById(R.id.EditarNRC);

        if(editNRC.getText().toString().equals("")){
            Toast.makeText(this,"Campo vacio",Toast.LENGTH_SHORT).show();
        }else{
            editCarrera = (EditText) findViewById(R.id.EditarCarrera);
            editEE = (EditText) findViewById(R.id.EditarEE);
            editBloque = (EditText) findViewById(R.id.EditarBloque);
            editSeccion = (EditText) findViewById(R.id.EditarSeccion);
            editNumPerson = (EditText) findViewById(R.id.EditarNumPersonal);


             nrc2 = Integer.parseInt(editNRC.getText().toString());

            try {
                String sql = "select CARRERA,EE,BLOQUE,SECCION, IDPERSONAL from Materias where NRC = " + nrc2;
                Cursor c = database.rawQuery(sql,null);

                int enteroCarrera = c.getColumnIndex("CARRERA");
                int enteroEE = c.getColumnIndex("EE");
                int enteroBloque = c.getColumnIndex("BLOQUE");
                int enteroSeccion = c.getColumnIndex("SECCION");
                int enteroNumpPersonal = c.getColumnIndex("IDPERSONAL");
                while(c.moveToNext()){
                    carrera = c.getString(enteroCarrera);
                    ee = c.getString(enteroEE);
                    bloque = c.getString(enteroBloque);
                    seccion = c.getString(enteroSeccion);
                    numperson = c.getString(enteroNumpPersonal);

                    editCarrera.setText(carrera);
                    editEE.setText(ee);
                    editBloque.setText(bloque);
                    editSeccion.setText(seccion);
                    editNumPerson.setText(numperson);

                }


                Toast.makeText(this, "Consulta exitosa", Toast.LENGTH_SHORT).show();
            }catch (SQLiteException e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void editarMaterias(View view){

        carrera = editCarrera.getText().toString();
        ee = editEE.getText().toString();
        bloque = editBloque.getText().toString();
        seccion = editSeccion.getText().toString();
        numperson = editNumPerson.getText().toString();

        int bloque2 = Integer.parseInt(bloque);
        int seccion2 = Integer.parseInt(seccion);
        int numperson2 = Integer.parseInt(numperson);

        database.beginTransaction();
        try {
            database.execSQL("UPDATE Materias " +
                    "SET CARRERA = '" + carrera + "', EE = '"+ ee +"', BLOQUE = " + bloque2 + ", SECCION = "+ seccion2+ ", IDPERSONAL = " + numperson2 + " WHERE NRC = "+ nrc2 + ";");

            database.setTransactionSuccessful();

            Toast.makeText(this, "!Guardado exitosamente¡", Toast.LENGTH_SHORT).show();

        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            database.endTransaction();
        }

        database.close();
        finish();
    }

}