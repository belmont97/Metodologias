package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditarAcademico extends AppCompatActivity {

    SQLiteDatabase database;
    private EditText numPersonal;
    private EditText nombre;
    private EditText apellidop;
    private EditText apellidom;
    private int numPersonalEntero;

    private String id_personal, nombreText, apellidopText, apellidomText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_academico);
        try{
            //Abrimos la base de datos
            openDatabase();

        }catch(SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para abrir la base de datos
    private void openDatabase(){
        try{
            database = SQLiteDatabase.openDatabase("data/data/com.example.aula/UniversidadVeracruzana",null,SQLiteDatabase.CREATE_IF_NECESSARY);
            Toast.makeText(this, "DATABASE Open", Toast.LENGTH_SHORT).show();
        }catch(SQLiteException e){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }
    //Metodo para buscar atraves del numero personal de academico si existe un academico con ese numero personal
    //y al hacerlo sacara la informacion para rellenar los EditText
    public void buscar(View view){
        numPersonal = (EditText) findViewById(R.id.editNumPersonal);

        //Preguntamos si
        if(numPersonal.getText().toString().equals("")){
            Toast.makeText(this,"Campo vacio",Toast.LENGTH_SHORT).show();
        }else{
            nombre = (EditText) findViewById(R.id.editNombre);
            apellidop = (EditText) findViewById(R.id.editApellidoP);
            apellidom = (EditText) findViewById(R.id.editApellidoM);

            numPersonalEntero = Integer.parseInt(numPersonal.getText().toString());

            try{
                String sql = "select NOMBRE, APELLIDOPATERNO, APELLIDOMATERNO from Academico where NUMPERSONAL = " + numPersonalEntero;
                Cursor c = database.rawQuery(sql, null);

                int nombre2 = c.getColumnIndex("NOMBRE");
                int apep2 = c.getColumnIndex("APELLIDOPATERNO");
                int apem2 = c.getColumnIndex("APELLIDOMATERNO");

                while(c.moveToNext()){
                    nombreText = c.getString(nombre2);
                    apellidopText = c.getString(apep2);
                    apellidomText = c.getString(apem2);

                    nombre.setText(nombreText);
                    apellidop.setText(apellidopText);
                    apellidom.setText(apellidomText);

                }
                Toast.makeText(this, "Consulta exitosa", Toast.LENGTH_SHORT).show();

            }catch(SQLiteException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void editarAcademico(View view){
        nombreText = nombre.getText().toString();
        apellidopText = apellidop.getText().toString();
        apellidomText = apellidom.getText().toString();


        database.beginTransaction();
        try {

            database.execSQL("UPDATE Academico SET NOMBRE = '"+ nombreText +"', APELLIDOPATERNO = '" + apellidopText +"', APELLIDOMATERNO = '" + apellidomText +"' where NUMPERSONAL = " + numPersonalEntero + ";");

            database.setTransactionSuccessful();
            Toast.makeText(this, "!Guardado Exitosamente¡", Toast.LENGTH_SHORT).show();

        }catch(SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            database.endTransaction();
        }
        database.close();
        finish();
    }
}