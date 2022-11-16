package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLWarning;

public class eliminar_materia extends AppCompatActivity {

    SQLiteDatabase database;
    private EditText nrc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_materia);
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

    public void eliminarMateria(View view){
        nrc = (EditText) findViewById(R.id.delMateria);

        int datoNRC = Integer.parseInt(nrc.getText().toString());
        database.beginTransaction();
        try {
            database.execSQL("delete from Materias where NRC = " + datoNRC + ";");

            Toast.makeText(this, "!Materia eliminada¡", Toast.LENGTH_SHORT).show();
            database.setTransactionSuccessful();
        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            database.endTransaction();
        }
        database.close();
        finish();
    }
}