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

public class ConsultaHorarios extends AppCompatActivity {


    private String baseDatos = "data/data/com.example.horariosuv/DatabaseUV";
    SQLiteDatabase database;
    private EditText buscarHorario;
    private TextView mostrar;
    private ImageButton buscar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_horarios);
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
                        String carrera,ee,lunes,martes,miercoles,jueves,viernes;
                        mostrar.setText("");
                        String sql = "select CARRERA,EE,LUNES,MARTES,MIERCOLES,JUEVES,VIERNES from Materias m INNER JOIN Horario h ON m.NRC = h.IDNRC WHERE h.Lunes='"+hora+"' or h.MARTES='" + hora+"' or h.MIERCOLES='" + hora+ "' or h.JUEVES='" + hora +"' or h.VIERNES='" + hora +"';";
                        Cursor c = database.rawQuery(sql, null);

                        int carreraEntero = c.getColumnIndex("CARRERA");
                        int eeEntero = c.getColumnIndex("EE");
                        int lunesEntero = c.getColumnIndex("LUNES");
                        int martesEntero = c.getColumnIndex("MARTES");
                        int miercolesEntero = c.getColumnIndex("MIERCOLES");
                        int juevesEntero = c.getColumnIndex("JUEVES");
                        int viernesEntero = c.getColumnIndex("VIERNES");

                        while(c.moveToNext()){
                            carrera = c.getString(carreraEntero);
                            ee = c.getString(eeEntero);
                            lunes = c.getString(lunesEntero);
                            martes = c.getString(martesEntero);
                            miercoles = c.getString(miercolesEntero);
                            jueves = c.getString(juevesEntero);
                            viernes = c.getString(viernesEntero);

                            mostrar.append("\nCARRERA: " + carrera + "\nMATERIA: " + ee + "\nLUNES: " + lunes + "\nMARTES: " + martes + "\nMIERCOLES: "+ miercoles + "\nJUEVES: "+ jueves + "\nVIERNES: " + viernes + "\n");
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