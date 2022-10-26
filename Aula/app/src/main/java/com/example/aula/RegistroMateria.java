package com.example.aula;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroMateria extends AppCompatActivity {
    private Button agregar;
    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_materia);

        agregar = (Button) findViewById(R.id.AgregarMateria);
        alerta = new AlertDialog.Builder(this);

    }

    public void btCancelar(View view){
        Intent cance = new Intent(this, Administrador.class);
        startActivity(cance);
    }

    public void agregarMateria(View view){
        alerta.setMessage("Los datos se and guardado de manera exitosa").setTitle("Aviso");

    }

}