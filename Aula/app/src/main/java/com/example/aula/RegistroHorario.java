package com.example.aula;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegistroHorario extends AppCompatActivity {

    private Button agregarHorario;
    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_horario);
        //agregarHorario = (Button) findViewById(R.id.AgregarHorario);
        alerta = new AlertDialog.Builder(this);
    }

    public void btCancelar(View view){
        Intent cancel = new Intent(this, Administrador.class);
        startActivity(cancel);
    }

    private void registrarHorario(){

    }
}