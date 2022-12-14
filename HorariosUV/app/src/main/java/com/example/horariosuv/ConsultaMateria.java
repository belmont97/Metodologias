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
                        String CarreText,NumText,NOMText, APEPText, APMText,salon,lunes,martes,miercoles,jueves,viernes;
                        String sql = "select CARRERA,NOMBRE,APELLIDOPATERNO,APELLIDOMATERNO,IDSALON,LUNES,MARTES,MIERCOLES,JUEVES,VIERNES from Materias AS m INNER JOIN Academico AS a ON m.IDPERSONAL=a.NUMPERSONAL INNER JOIN Horario AS h ON m.NRC=h.IDNRC where m.EE = '" + Materia +"';";
                        Cursor c = database.rawQuery(sql,null);

                        int carre = c.getColumnIndex("CARRERA");
                        int NOM= c.getColumnIndex("NOMBRE");
                        int APEP = c.getColumnIndex("APELLIDOPATERNO");
                        int APEM = c.getColumnIndex("APELLIDOMATERNO");
                        int idsalon = c.getColumnIndex("IDSALON");
                        int lunesInt = c.getColumnIndex("LUNES");
                        int martesInt = c.getColumnIndex("MARTES");
                        int miercolesInt = c.getColumnIndex("MIERCOLES");
                        int juevesInt = c.getColumnIndex("JUEVES");
                        int viernesInt = c.getColumnIndex("VIERNES");

                        while (c.moveToNext()){
                            CarreText = c.getString(carre);
                            NOMText = c.getString(NOM);
                            APEPText = c.getString(APEP);
                            APMText = c.getString(APEM);
                            salon = c.getString(idsalon);
                            lunes = c.getString(lunesInt);
                            martes = c.getString(martesInt);
                            miercoles = c.getString(miercolesInt);
                            jueves = c.getString(juevesInt);
                            viernes = c.getString(viernesInt);
                            mostrar.append("\nCARRERA: " + CarreText +"\n"+"ACAD??MICO: " +NOMText + " "+APEPText + " "+ APMText + "\nSAL??N: " + salon +  "\n"+"LUNES: "+lunes+"\n"+"MARTES: "+martes+"\n"+"MIERCOLES: "+ miercoles+"\n"+"JUEVES: "+jueves+"\n"+"VIERNES: "+viernes+"\n");
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