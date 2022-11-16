package com.example.aula;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegistroSalon extends AppCompatActivity {
    MainActivity menu = new MainActivity();
    private Button agregarSalon;
    private AlertDialog.Builder alerta;
    SQLiteDatabase database;
    private RadioGroup selectEdificio;
    private RadioButton edificio;
    private EditText salon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_salon);
        agregarSalon = (Button) findViewById(R.id.AgregarSalon);
        alerta = new AlertDialog.Builder(this);
        try{
            openDatabase();
        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void btCancelar(View view){
        Intent cance = new Intent(this,Administrador.class);
        startActivity(cance);
    }


    private void openDatabase(){
        try{
            database = SQLiteDatabase.openDatabase("data/data/com.example.aula/UniversidadVeracruzana",null,SQLiteDatabase.CREATE_IF_NECESSARY);
            Toast.makeText(this, "DATABASE Open", Toast.LENGTH_SHORT).show();
        }catch(SQLiteException e){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }


    public void registrarSalon(View view){
        selectEdificio = (RadioGroup) findViewById(R.id.edificios);
        salon = (EditText) findViewById(R.id.idSalon);

        int idRadioButton = selectEdificio.getCheckedRadioButtonId();
        edificio = findViewById(idRadioButton);

        String salo = salon.getText().toString();
        String edi  =edificio.getText().toString();

        database.beginTransaction();
        try {
            database.execSQL("insert into Salon(NUMSALON, EDIFICIO) values('"+ salo +"','" + edi + "');");
//insert into horario(nrc, salon,
            database.setTransactionSuccessful();
            Toast.makeText(this,"Registrado exitosamente", Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }finally {
            database.endTransaction();
        }
        database.close();
        finish();
    }

}