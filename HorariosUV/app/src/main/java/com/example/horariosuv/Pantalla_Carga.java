package com.example.horariosuv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Pantalla_Carga extends AppCompatActivity {

    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

    }

    public void VentanaAcademico(View view){
        Intent ventana = new Intent(getApplication(),ConsultaAcademico.class);
        startActivity(ventana);
    }

    public void VentanaHorario(View view){
        Intent ventana = new Intent(getApplication(), ConsultaHorarios.class);
        startActivity(ventana);
    }

    public void VentanaSalon(View view){
        Intent ventana = new Intent(getApplication(), ConsultaSalon.class);
        startActivity(ventana);
    }

    public void VentanaMateria(View view){
        Intent ventana = new Intent(getApplication(),ConsultaMateria.class);
        startActivity(ventana);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuacciones, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuitem){
        int id = menuitem.getItemId();
        alerta = new AlertDialog.Builder(this);

        if(id == R.id.login){
            alerta.setMessage("¿Eres un administrador?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent ventana = new Intent(Pantalla_Carga.this, Login.class);
                            startActivity(ventana);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alert = alerta.create();
            alert.setTitle("Alerta");
            alert.show();


            return true;
        }
        return super.onOptionsItemSelected(menuitem);
    }


}