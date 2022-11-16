package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdministradorEditar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_editar);
    }

    public void editarMateria(View view){
        //corregir la clase editar materia
        Intent ventanaEditarMateria = new Intent(this, EditarMateria.class);
        startActivity(ventanaEditarMateria);
    }

    public void editarAcademico(View view){
        Intent  ventanaEditarAcademico= new Intent(this, EditarAcademico.class);
        startActivity(ventanaEditarAcademico);
    }

    public void editarSalon(View view){
        Intent ventanaEditarSalon = new Intent(this, EditarSalon.class);
        startActivity(ventanaEditarSalon);
    }
}