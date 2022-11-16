package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class eliminar_academico extends AppCompatActivity {

    SQLiteDatabase database;
    private EditText numPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_academico);
        try {
            openDatabase();

        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void eliminarAcademico(View view){
        numPerson = (EditText) findViewById(R.id.editNumPerson);

        int numPersonal = Integer.parseInt(numPerson.getText().toString());
        database.beginTransaction();
        try {
            database.execSQL("delete from Academico where NUMPERSONAL = " + numPersonal +";");

            database.setTransactionSuccessful();
            Toast.makeText(this, "!Academico eliminado¡", Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            database.endTransaction();
        }
        database.close();
        finish();
    }
}