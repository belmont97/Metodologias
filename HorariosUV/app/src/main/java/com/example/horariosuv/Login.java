package com.example.horariosuv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Login extends AppCompatActivity {

    private String ubicacion = "data/data/com.example.horariosuv/AdminUV";
    SQLiteDatabase dataAdmin;
    private EditText usuario;
    private EditText password;
    private String user,pass;
    private Button iniciarSesion;
    private Intent admin;
    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarSesion = (Button) findViewById(R.id.inicio);
        usuario = (EditText) findViewById(R.id.idUsuario);
        password = (EditText) findViewById(R.id.idPassword);
        admin = new Intent(this, opciones_administrador.class);
        alerta = new AlertDialog.Builder(this);
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    dataAdmin = SQLiteDatabase.openDatabase(ubicacion,null,SQLiteDatabase.OPEN_READONLY);
                    String mayus = usuario.getText().toString().toUpperCase();

                    try {
                        String sql = "select USUARIO, PASSSWORD from Admins where USUARIO = '" + mayus+"';";
                        Cursor c = dataAdmin.rawQuery(sql,null);

                        int userEntero = c.getColumnIndex("USUARIO");
                        int passEntero = c.getColumnIndex("PASSSWORD");
                        while(c.moveToNext()){
                            user = c.getString(userEntero);
                            pass = c.getString(passEntero);
                        }
                        if(usuario.getText().toString().equals("") || password.getText().toString().equals("")){
                            alerta.setMessage("Campos vacios");
                            AlertDialog alert = alerta.create();
                            alert.setTitle("Alerta");
                            alert.show();
                        }else if(usuario.getText().toString().equals(user.toString()) && password.getText().toString().equals(pass.toString())){
                            startActivity(admin);
                        }else{
                            alerta.setMessage("Usuario o contraseña incorrectos");
                            AlertDialog alert = alerta.create();
                            alert.setTitle("Alerta");
                            alert.show();
                        }
                    }catch (Exception e){
                        alerta.setMessage("Usuario o contraseña incorrectos");
                        AlertDialog alert = alerta.create();
                        alert.setTitle("Alerta");
                        alert.show();
                    }

                }catch (Exception e ){

                }

            }
        });
    }

    public void botonRegresar(View view){
        finish();
    }

}