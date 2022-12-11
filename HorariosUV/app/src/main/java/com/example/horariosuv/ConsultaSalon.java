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
import android.widget.Toast;

public class ConsultaSalon extends AppCompatActivity {

    private String baseDatos = "data/data/com.example.horariosuv/DatabaseUV";
    private ImageButton buscar;
    private EditText buscarSalon;
    private TextView mostrar;
    SQLiteDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_salon);
        buscar = (ImageButton) findViewById(R.id.botonSearchH2);
        buscarSalon = (EditText) findViewById(R.id.textHorario);
        mostrar = (TextView) findViewById(R.id.mostrarHorario);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    database = SQLiteDatabase.openDatabase(baseDatos,null,SQLiteDatabase.OPEN_READONLY);
                    try {
                        String nombre, apepp, apemm, idsalon;
                        mostrar.setText("");
                        String sql = "select IDSALON,NOMBRE,APELLIDOPATERNO,APELLIDOMATERNO from AcademicoSalon AS a INNER JOIN Academico AS ac ON a.IDPERSONAL=ac.NUMPERSONAL where IDSALON = '" +buscarSalon.getText().toString() +"';";
                        Cursor c = database.rawQuery(sql,null);


                        int salon = c.getColumnIndex("IDSALON");
                        int nom = c.getColumnIndex("NOMBRE");
                        int apep = c.getColumnIndex("APELLIDOPATERNO");
                        int apem = c.getColumnIndex("APELLIDOMATERNO");

                        while (c.moveToNext()){
                            nombre = c.getString(nom);
                            apepp = c.getString(apep);
                            apemm = c.getString(apem);
                            idsalon = c.getString(salon);

                            mostrar.append("\nNOMBRE: " + nombre + "\nAPELLIDO PATERNO: " + apepp+"\nAPELLIDO MATERNO: " + apemm + "\nSALÓN: " + idsalon+"\n");
                        }
                        Toast.makeText(ConsultaSalon.this, "Salones", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(ConsultaSalon.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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