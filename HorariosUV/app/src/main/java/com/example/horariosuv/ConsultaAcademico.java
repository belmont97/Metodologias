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
                        String ee,NOMText,APEPText,APMText,salon, lunes, martes, miercoles, jueves, viernes;
                        String sql = "select EE,NOMBRE,APELLIDOPATERNO,APELLIDOMATERNO,IDSALON,LUNES,MARTES,MIERCOLES,JUEVES,VIERNES from Materias AS m INNER JOIN Academico AS ac ON m.IDPERSONAL=ac.NUMPERSONAL INNER JOIN Horario AS h ON m.NRC=h.IDNRC where m.CARRERA ='" + buscarAcademico.getText().toString()+"';";
                        Cursor c = database.rawQuery(sql,null);


                        int eeEntero = c.getColumnIndex("EE");
                        int NOM= c.getColumnIndex("NOMBRE");
                        int APEP = c.getColumnIndex("APELLIDOPATERNO");
                        int APEM = c.getColumnIndex("APELLIDOMATERNO");
                        int salonEntero = c.getColumnIndex("IDSALON");
                        int lunesInt = c.getColumnIndex("LUNES");
                        int martesInt = c.getColumnIndex("MARTES");
                        int miercolesInt = c.getColumnIndex("MIERCOLES");
                        int juevesInt = c.getColumnIndex("JUEVES");
                        int viernesInt =c.getColumnIndex("VIERNES");
                        while (c.moveToNext()){
                            ee = c.getString(eeEntero);
                            NOMText = c.getString(NOM);
                            APEPText = c.getString(APEP);
                            APMText = c.getString(APEM);
                            salon = c.getString(salonEntero);
                            lunes  = c.getString(lunesInt);
                            martes = c.getString(martesInt);
                            miercoles = c.getString(miercolesInt);
                            jueves = c.getString(juevesInt);
                            viernes = c.getString(viernesInt);


                            mostrar.append("\nMATERIA: " + ee +"\nACADÉMICO: "+NOMText+" "+ APEPText+" "+ APMText+"\nSALÓN: " + salon + "\n"+"LUNES: "+ lunes +"\n"+"MARTES: "+martes+"\n"+"MIERCOLES: "+ miercoles+"\n"+"JUEVES: "+jueves+"\n"+"VIERNES: "+ viernes +"\n");
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