package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Opciones_Administrador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_administrador);
    }

    public void menuAdministradorAgregar(View view){
        Intent menu = new Intent(this,Administrador.class);
        startActivity(menu);
    }

    public void menuAdministradorEliminar(View view){
        Intent menu = new Intent(this,AdministradorEliminar.class);
        startActivity(menu);
    }

    public void menuAdministradorEditar(View view){
        Intent menu = new Intent(this,AdministradorEditar.class);
        startActivity(menu);
    }
}