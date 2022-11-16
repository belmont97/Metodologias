package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdministradorEliminar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_eliminar);
    }

    public void eliminarSalon(View view){
        Intent ventanaEliminarSalon = new Intent(this, eliminar_salon.class);
        startActivity(ventanaEliminarSalon);
    }

    public void eliminarAcademico(View view){
        Intent ventanaEliminarAcademico = new Intent(this, eliminar_academico.class);
        startActivity(ventanaEliminarAcademico);
    }

    public void eliminarMateria(View view){
        Intent ventanaEliminarMateria = new Intent(this, eliminar_materia.class);
        startActivity(ventanaEliminarMateria);
    }

    public void eliminarHorario(View view){
        Intent ventanaEliminarHorario = new Intent(this, eliminar_horario.class);
        startActivity(ventanaEliminarHorario);
    }
}