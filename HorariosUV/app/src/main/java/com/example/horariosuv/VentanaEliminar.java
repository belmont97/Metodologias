package com.example.horariosuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VentanaEliminar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_eliminar);
    }

    public void ventanaEliminarAcademico(View view){
        Intent ventana = new Intent(VentanaEliminar.this, EliminarAcademico.class);
        startActivity(ventana);
    }

    public void ventanaEliminarMateria(View view){
        Intent ventana = new Intent(VentanaEliminar.this, EliminarMateria.class);
        startActivity(ventana);
    }

    public void ventanaEliminarSalon(View view){
        Intent ventana = new Intent(VentanaEliminar.this, EliminarSalon.class);
        startActivity(ventana);
    }

    public void ventanaEliminarHorario(View view){
        Intent ventana = new Intent(VentanaEliminar.this, EliminarHorario.class);
        startActivity(ventana);
    }

    public void ventanaEliminarAcademicoSalon(View view){
        Intent ventana = new Intent(VentanaEliminar.this, EliminarAcademicoSalon.class);
        startActivity(ventana);
    }

    public void ventanaEliminarMateriaSalon(View view){
        Intent ventana = new Intent(VentanaEliminar.this, EliminarMateriaSalon.class);
        startActivity(ventana);
    }

    public void botonAtras(View view){
        finish();
    }
}