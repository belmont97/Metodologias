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

    public void editarAcademico(View view){
        //corregir la clase editar materia
        Intent editarAca = new Intent(this, EditarMateria.class);
        startActivity(editarAca);
    }
}