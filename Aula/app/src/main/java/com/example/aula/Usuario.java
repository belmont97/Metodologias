package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.database.sqlite.SQLiteDatabaseKt;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Usuario extends AppCompatActivity {
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

    }

    public void consultaAcademico(View view){
        Intent consulAca = new Intent(this, ConsultaAcademico.class);
        startActivity(consulAca);
    }

    public void consultaSalon(View view){
        Intent consulSa = new Intent(this, Consulta_Salon.class);
        startActivity(consulSa);
    }

    public void consultaMateria(){

    }

}