package com.example.aula;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroMateria extends AppCompatActivity {
    private Button agregarMateria;
    private AlertDialog.Builder alerta;
    private EditText NRC;
    private EditText Carrera;
    private EditText EE;
    private EditText Bloque;
    private EditText Seccion;
    private EditText NumPersonal;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_materia);

        agregarMateria = (Button) findViewById(R.id.AgregarMateria);
        alerta = new AlertDialog.Builder(this);
        try{
            openDatabase();

            //database.close();
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }

    public void btCancelar(View view){
        Intent cance = new Intent(this, Administrador.class);
        startActivity(cance);
    }

    private void openDatabase(){
        try{
            database = SQLiteDatabase.openDatabase("data/data/com.example.aula/UniversidadVeracruzana",null,SQLiteDatabase.CREATE_IF_NECESSARY);
            Toast.makeText(this, "DATABASE Open", Toast.LENGTH_SHORT).show();
        }catch(SQLiteException e){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }



    public void registrarMateria(View view){

        //Se expecifican los componentes con las variables para mandar y recolectar datos
        NRC = (EditText) findViewById(R.id.EditarNRC);
        Carrera = (EditText) findViewById(R.id.EditarCarrera);
        EE = (EditText) findViewById(R.id.EditarEE);
        Bloque = (EditText) findViewById(R.id.EditarBloque);
        Seccion = (EditText) findViewById(R.id.EditarSeccion);
        NumPersonal = (EditText) findViewById(R.id.EditarNumPersonal);


        if(NRC.getText().toString().equals("") || Carrera.getText().toString().equals("") || EE.getText().toString().equals("") || Bloque.getText().toString().equals("") || Seccion.getText().toString().equals("") || NumPersonal.getText().toString().equals("")){

        }else{
            database.beginTransaction();
            try {
                    //Se pasan los datos
                    String carrera = Carrera.getText().toString();
                    String materia = EE.getText().toString();

                    //Se pasan los datos en tipo string a enteros para que al ingresarlos a la base de datos no se encunetren errores
                    int nrcEntero = Integer.parseInt(NRC.getText().toString());
                    int bloqueEntero = Integer.parseInt(Bloque.getText().toString());
                    int seccionEntero = Integer.parseInt(Seccion.getText().toString());
                    int numPersonalEntero = Integer.parseInt(NumPersonal.getText().toString());

                    database.execSQL("insert into Materias(NRC,CARRERA,EE,BLOQUE,SECCION,IDPERSONAL) " +
                            "values("+nrcEntero+",'"+carrera+"','"+materia+"',"+bloqueEntero+","+seccionEntero+","+numPersonalEntero+")");

                //Toast.makeText(this,"Agregado exitasamente",Toast.LENGTH_SHORT).show();
                    //alerta.setMessage("Los datos se guardaron de manera exitosa").setTitle("Aviso");
                database.setTransactionSuccessful();
            }catch (SQLiteException e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
                //alerta.setMessage("Error").setTitle("Alerta");
            }finally {
                database.endTransaction();

            }
        }
        database.close();
        finish();
    }
}