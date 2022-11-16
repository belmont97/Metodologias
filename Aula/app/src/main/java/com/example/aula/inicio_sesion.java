package com.example.aula;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class inicio_sesion extends AppCompatActivity {

    private Button iniciarSesion;
    private EditText usuario;
    private EditText password;
    private Intent admin;
    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        iniciarSesion = (Button) findViewById(R.id.IniciarSesion);
        usuario = (EditText) findViewById(R.id.Usuario);
        password = (EditText) findViewById(R.id.Pass);
        admin = new Intent(this, Opciones_Administrador.class);
        alerta = new AlertDialog.Builder(this);
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usuario.getText().toString().equals("admin") && password.getText().toString().equals("12345")){
                    startActivity(admin);
                }else if(usuario.getText().toString().equals("") || password.getText().toString().equals("")){
                    alerta.setMessage("Campos vacios");
                    AlertDialog alert = alerta.create();
                    alert.setTitle("Alerta");
                    alert.show();
                }else{
                    alerta.setMessage("Usuario o contraseña incorrectos");
                    AlertDialog alert = alerta.create();
                    alert.setTitle("Alerta");
                    alert.show();
                }
            }
        });

    }

}