package com.example.horariosuv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ConsultaMateria extends AppCompatActivity{

    private String baseDatos = "data/data/com.example.horariosuv/DatabaseUV";
    private ListView listaProfes;
    private ImageButton buscar;
    private TextView mostrar;
    private EditText buscarMateria;
    SQLiteDatabase database;
    private String Materia;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_materia);
        buscarMateria = (EditText) findViewById(R.id.textHorario);
        buscar = (ImageButton) findViewById(R.id.botonSearchH2);
        mostrar = (TextView) findViewById(R.id.mostrarHorario);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    database = SQLiteDatabase.openDatabase(baseDatos,null,SQLiteDatabase.OPEN_READONLY);
                    try {
                        Materia = buscarMateria.getText().toString();
                        mostrar.setText("");
                        String NOMText, APEPText, APMText,salon;
                        String sql = "select NOMBRE,APELLIDOPATERNO,APELLIDOMATERNO,IDSALON from Materias AS m INNER JOIN Academico AS a ON m.IDPERSONAL=a.NUMPERSONAL INNER JOIN MateriaSalon AS mn ON m.NRC = mn.IDNRC where m.EE = '" + Materia +"';";
                        Cursor c = database.rawQuery(sql,null);

                        int NOM= c.getColumnIndex("NOMBRE");
                        int APEP = c.getColumnIndex("APELLIDOPATERNO");
                        int APEM = c.getColumnIndex("APELLIDOMATERNO");
                        int idsalon = c.getColumnIndex("IDSALON");

                        while (c.moveToNext()){
                            NOMText = c.getString(NOM);
                            APEPText = c.getString(APEP);
                            APMText = c.getString(APEM);
                            salon = c.getString(idsalon);
                            mostrar.append("\nACADÉMICO: " +NOMText + "\nAPELLIDO PATERNO: "+APEPText + "\nAPELLIDO MATERNO: "+ APMText + "\nSALÓN: " + salon +  "\n");
                        }

                    }catch (Exception e){

                    }
                }catch(Exception e){

                }

            }
        });


    }

    public void BuscarDatos(String s){

    }

    public void botonRegresar(View view){
        finish();
    }


}