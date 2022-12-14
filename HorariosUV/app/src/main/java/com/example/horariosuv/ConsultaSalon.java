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
                        String nombre, apepp, apemm, Carrera, ee, lunes, martes, miercoles, jueves, viernes;
                        mostrar.setText("");
                        String sql = "select NOMBRE,APELLIDOPATERNO,APELLIDOMATERNO,CARRERA,EE,LUNES,MARTES,MIERCOLES,JUEVES,VIERNES from Academico AS ac INNER JOIN Materias AS m ON ac.NUMPERSONAL = m.IDPERSONAL INNER JOIN Horario AS h ON m.NRC = h.IDNRC where h.IDSALON = '" +buscarSalon.getText().toString() +"';";
                        Cursor c = database.rawQuery(sql,null);

                        int nom = c.getColumnIndex("NOMBRE");
                        int apep = c.getColumnIndex("APELLIDOPATERNO");
                        int apem = c.getColumnIndex("APELLIDOMATERNO");
                        int carreraEntero = c.getColumnIndex("CARRERA");
                        int eeEntero = c.getColumnIndex("EE");
                        int lunesEntero = c.getColumnIndex("LUNES");
                        int martesEntero = c.getColumnIndex("MARTES");
                        int miercolesEntero = c.getColumnIndex("MIERCOLES");
                        int juevesEntero = c.getColumnIndex("JUEVES");
                        int viernesEntero = c.getColumnIndex("VIERNES");

                        while (c.moveToNext()){
                            nombre = c.getString(nom);
                            apepp = c.getString(apep);
                            apemm = c.getString(apem);
                            Carrera = c.getString(carreraEntero);
                            ee = c.getString(eeEntero);
                            lunes = c.getString(lunesEntero);
                            martes = c.getString(martesEntero);
                            miercoles = c.getString(miercolesEntero);
                            jueves = c.getString(juevesEntero);
                            viernes = c.getString(viernesEntero);

                            mostrar.append("\nNOMBRE: " + nombre + " " + apepp+" " + apemm + "\nCARRERA: "+ Carrera + "\nMATERIA: "+ ee + "\nLUNES: "+ lunes + "\nMARTES: "+ martes + "\nMIERCOLES: "+miercoles+ "\nJUEVES: "+ jueves + "\nVIERNES: "+ viernes + "\n");
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