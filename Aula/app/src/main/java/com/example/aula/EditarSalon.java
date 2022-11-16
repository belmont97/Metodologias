package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EditarSalon extends AppCompatActivity {

    SQLiteDatabase database;
    private EditText editSalon;
    private RadioGroup seleccionEdificio;
    private RadioButton editEdificio;
    private RadioButton editEditficio2;
    private String salon, edificio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_salon);
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

    public void buscarSalon(View view){
        editSalon = (EditText) findViewById(R.id.editNumSalon);

        if(editSalon.getText().toString().equals("")){
            Toast.makeText(this, "Campo Vacio", Toast.LENGTH_SHORT).show();
        }else{
            editEdificio = (RadioButton) findViewById(R.id.editEconex);
            editEditficio2 = (RadioButton) findViewById(R.id.editFei);

            salon = editSalon.getText().toString();
            try{
                String sql = "select EDIFICIO from Salon where NUMSALON = '" + salon + "';";
                Cursor c = database.rawQuery(sql,null);

                int edificio2 = c.getColumnIndex("EDIFICIO");

                while(c.moveToNext()){
                    edificio = c.getString(edificio2);

                }
                editEdificio.setSelected(true);
                /*if(editEdificio.toString().equals(edificio)){
                    editEdificio.setSelected(true);
                }else if(editEditficio2.toString().equals(edificio)){
                    editEditficio2.setSelected(true);
                }*/

                Toast.makeText(this, "Consulta exitosa", Toast.LENGTH_SHORT).show();
            }catch (SQLiteException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

    }


    public void editarSalon(View view){
        seleccionEdificio = (RadioGroup) findViewById(R.id.radioGroup);

        int idRadioButton = seleccionEdificio.getCheckedRadioButtonId();
        editEdificio = findViewById(idRadioButton);

        edificio = editEdificio.getText().toString();

        database.beginTransaction();
        try {
            database.execSQL("update Salon set EDIFICIO = '" + edificio +"' where NUMSALON = '" + salon +"'");

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