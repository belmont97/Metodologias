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
        Intent ventanaSalon = new Intent(this,RegistroSalon.class);
        startActivity(ventanaSalon);
    }

    public void registroAcademico(View view){
        Intent ventanaAcademico = new Intent(this, ResgistroAcademico.class);
        startActivity(ventanaAcademico);
    }

    public void resgistroMateria(View view){
        Intent ventanaMateria = new Intent(this,RegistroMateria.class);
        startActivity(ventanaMateria);
    }

    public void registroHorario(View view){
        Intent ventanaHorario = new Intent(this,RegistroHorario.class);
        startActivity(ventanaHorario);
    }
}