package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void Usuario(View view){
        Intent usuario = new Intent(this,Usuario.class);
        startActivity(usuario);
    }

    public void Administrador(View view){
        Intent admin = new Intent(this, inicio_sesion.class);
        startActivity(admin);
    }

}