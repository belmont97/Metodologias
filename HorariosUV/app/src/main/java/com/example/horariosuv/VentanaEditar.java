package com.example.horariosuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VentanaEditar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_editar);
    }

    public void ventanaEditarAcademico(View view){
        Intent ventana = new Intent(VentanaEditar.this, EditarAcademico.class);
        startActivity(ventana);
    }

    public void ventanaEditarMateria(View view){
        Intent ventana = new Intent(VentanaEditar.this, EditarMateria.class);
        startActivity(ventana);
    }

    public void ventanaEditarSalon(View view){
        Intent ventana = new Intent(VentanaEditar.this, EditarSalon.class);
        startActivity(ventana);
    }

    public void ventanaEditarHorario(View view){
        Intent ventana= new Intent(VentanaEditar.this, EditarHorario.class);
        startActivity(ventana);
    }

    public void ventanaEditarAcademicoSalon(View view){
        Intent ventana = new Intent(VentanaEditar.this,EditarAcademicoSalon.class);
        startActivity(ventana);
    }

    public void ventanaEditarMateriaSalon(View view){
        Intent ventana = new Intent(VentanaEditar.this, EditarMateriSalon.class);
        startActivity(ventana);
    }
    public void botonAtras(View view){
        finish();
    }
}