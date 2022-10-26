package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ResgistroAcademico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgistro_academico);
    }

    public void btCancelar(View view){
        Intent cance = new Intent(this,Administrador.class);
        startActivity(cance);
    }
}