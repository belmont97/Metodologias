package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class eliminar_salon extends AppCompatActivity {

    SQLiteDatabase database;
    private EditText salon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_salon);
        try{
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

    public void eliminarSalon(View view){
        salon = (EditText) findViewById(R.id.delSalon);

        String salontext = salon.getText().toString();

        database.beginTransaction();
        try {
            database.execSQL("delete from Salon where NUMSALON = '" + salontext +"';");

            database.setTransactionSuccessful();
            Toast.makeText(this, "!Salon eliminado¡", Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            database.endTransaction();
        }
        database.close();
        finish();
    }

}