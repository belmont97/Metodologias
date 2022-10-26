package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Administrador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
    }

    public void registroSalon(View view){
        Intent salon = new Intent(this,RegistroSalon.class);
        startActivity(salon);
    }

    public void registroAcademico(View view){
        Intent academico = new Intent(this, ResgistroAcademico.class);
        startActivity(academico);
    }

    public void resgistroMateria(View view){
        Intent materia = new Intent(this,RegistroMateria.class);
        startActivity(materia);
    }

    public void registroHorario(View view){
        Intent hora = new Intent(this,RegistroHorario.class);
        startActivity(hora);
    }
}