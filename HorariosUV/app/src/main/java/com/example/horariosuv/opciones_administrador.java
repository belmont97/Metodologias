package com.example.horariosuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class opciones_administrador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_administrador);
    }

    public void botonRegresar(View view){
        finish();
    }

    public void botonAgregar(View view){
        Intent intent = new Intent(this, VentanaAgregar.class);
        startActivity(intent);
    }

    public void botonEditar(View view){
        Intent intent = new Intent(this,VentanaEditar.class);
        startActivity(intent);
    }

    public void botonEliminar(View view){
        Intent intent = new Intent(this, VentanaEliminar.class);
        startActivity(intent);
    }
}