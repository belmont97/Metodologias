package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Consulta_Salon extends AppCompatActivity {

    SQLiteDatabase database;
    private EditText idSalon;
    private TextView mostrarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_salon);
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

    public void consultaSalon(View view){
        idSalon = (EditText) findViewById(R.id.consultar);
        mostrarr = (TextView) findViewById(R.id.mostrar);
        String salon,edificio;
        try {
            String sql = "select NUMSALON, EDIFICIO from Salon";
            Cursor c = database.rawQuery(sql, null);

            int idsalon = c.getColumnIndex("NRC");
            int idedificio = c.getColumnIndex("EE");

            while(c.moveToNext()){

                salon = c.getString(idsalon);
                edificio = c.getString(idedificio);

                mostrarr.append("SALON: " + salon + "\n" + "EDIFICIO: " + edificio + "\n");
            }

        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}