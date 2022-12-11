package com.example.horariosuv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ConsultaHorario2 extends AppCompatActivity {

    private String baseDatos = "data/data/com.example.horariosuv/DatabaseUV";
    SQLiteDatabase database;
    private EditText buscarHorario;
    private TextView mostrar;
    private ImageButton buscar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_horario2);
        buscarHorario = (EditText) findViewById(R.id.textHorario);
        mostrar = (TextView) findViewById(R.id.mostrarHorario);
        buscar = (ImageButton) findViewById(R.id.botonSearchH2);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String hora = buscarHorario.getText().toString();
                    database = SQLiteDatabase.openDatabase(baseDatos,null,SQLiteDatabase.OPEN_READONLY);
                    try {
                        mostrar.setText("");
                        String nombre,apellidop,apellidom,salon,edificio;
                        String sql = "select NOMBRE,APELLIDOPATERNO,APELLIDOMATERNO, NUMSALON, EDIFICIO from Academico AS a INNER JOIN AcademicoSalon AS ac ON a.NUMPERSONAL=ac.IDPERSONAL INNER JOIN Salon AS s ON ac.IDSALON=s.NUMSALON INNER JOIN Horario AS h ON s.NUMSALON=h.IDSALON WHERE h.LUNES = '" + hora +"' or h.MARTES = '"+ hora +"' or h.MIERCOLES='"+ hora +"' or h.JUEVES='"+ hora +"' or h.VIERNES='"+hora +"';";
                        Cursor c = database.rawQuery(sql, null);

                        int nombre1 = c.getColumnIndex("NOMBRE");
                        int apellidopaterno = c.getColumnIndex("APELLIDOPATERNO");
                        int apellidomaterno = c.getColumnIndex("APELLIDOMATERNO");
                        int salon1 = c.getColumnIndex("NUMSALON");
                        int edificio1 = c.getColumnIndex("EDIFICIO");
                        //En este bluque lo que hara sera revisar todas las tuplas de la tabla
                        while(c.moveToNext()){
                            //Se obtinen los datos de la columna por el numero de columna obtenido
                            nombre = c.getString(nombre1);
                            apellidop = c.getString(apellidopaterno);
                            apellidom = c.getString(apellidomaterno);
                            salon = c.getString(salon1);
                            edificio = c.getString(edificio1);
                            //Se muestran en patalla los datos
                            mostrar.append("\n" + "NOMBRE: " + nombre + "\n" + "APELLIDOP: " + apellidop + "\n" + "APELLIDOM: " + apellidom + "\n" + "SALON: " + salon + "\n" + "EDIFICIO: "+ edificio + "\n");
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