package com.example.horariosuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VentanaAgregar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_agregar);
    }

    public void ventanaAcademico(View view){
        Intent ventana = new Intent(this, AgregarAcademico.class);
        startActivity(ventana);
    }

    public void ventanaSalon(View view){
        Intent ventana = new Intent(this, AgregarSalon.class);
        startActivity(ventana);
    }

    public void ventanaHorario(View view){
        Intent ventana = new Intent(this, AgregarHorario.class);
        startActivity(ventana);
    }

    public void ventanaMateria(View view){
        Intent ventana = new Intent(this, AgregarMateria.class);
        startActivity(ventana);
    }

    public void ventanaMateriaSalon(View view){
        Intent ventana = new Intent(this, AgregarMateriaSalon.class);
        startActivity(ventana);
    }

    public void ventanaAcademicoSalon(View view){
        Intent ventana = new Intent(this, AgregarAcademicoSalon.class);
        startActivity(ventana);
    }

    public void botonAtras(View view){
        finish();
    }
}