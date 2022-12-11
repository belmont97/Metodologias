package com.example.horariosuv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ConsultaAcademico extends AppCompatActivity {

    private String baseDatos = "data/data/com.example.horariosuv/DatabaseUV";
    private ImageButton buscar;
    private EditText buscarAcademico;
    SQLiteDatabase database;
    private TextView mostrar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_academico);
        mostrar = (TextView) findViewById(R.id.mostrarHorario);
        buscar = (ImageButton) findViewById(R.id.botonSearchH2);
        buscarAcademico = (EditText) findViewById(R.id.textHorario);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    database = SQLiteDatabase.openDatabase(baseDatos,null,SQLiteDatabase.OPEN_READONLY);
                    try {
                        mostrar.setText("");
                        String ee,salon;
                        String sql = "select EE,IDSALON from Materias AS m INNER JOIN MateriaSalon AS ms ON m.NRC = ms.IDNRC where CARRERA ='" + buscarAcademico.getText().toString()+"';";
                        Cursor c = database.rawQuery(sql,null);

                        int eeEntero = c.getColumnIndex("EE");
                        int salonEntero = c.getColumnIndex("IDSALON");

                        while (c.moveToNext()){
                            ee = c.getString(eeEntero);
                            salon = c.getString(salonEntero);

                            mostrar.append("\nMATERIA: " + ee + "\nSALÓN: " + salon + "\n");
                        }
                    }catch (Exception e){

                    }
                }catch (Exception e){

                }
            }
        });

    }


    public void botonRegresar(View view){
        finish();
    }
}