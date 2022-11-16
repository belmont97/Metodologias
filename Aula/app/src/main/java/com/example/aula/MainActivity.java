package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database;
    public EditText dit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
        openDatabase();
        //insertarDatosSalon();

        //createTablas();
        //insertarMateria();


        database.close();
        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public void openDatabase(){
        try{
            database = SQLiteDatabase.openDatabase("data/data/com.example.aula/UniversidadVeracruzana",null,SQLiteDatabase.CREATE_IF_NECESSARY);
            Toast.makeText(this, "DATABASE Open", Toast.LENGTH_SHORT).show();
        }catch(SQLiteException e){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }


    private void createTablas(){
        database.beginTransaction();
        try{
            //Tabla Materias que se compone de NRC llave primaria, Carrera, Experiencia Educativa, Seccion, Bloque,Id_Personal del academico que la va a impartir
            database.execSQL("create table Materias(NRC integer PRIMARY KEY NOT NULL, CARRERA text, EE text, BLOQUE integer,SECCION integer,IDPERSONAL integer, FOREIGN KEY(IDPERSONAL) REFERENCES Academico(NUMPERSONAL));");
            //Tabla Salon que se compone de Numero de Salon llave primaria y Edificio
            database.execSQL("create table Salon(NUMSALON text PRIMARY KEY NOT NULL, EDIFICIO text);");
            //Tabla Academico que se compone de Numero Personal, Nombre, Appelido Paterno, Apellido Materno
            database.execSQL("create table Academico(NUMPERSONAL integer PRIMARY KEY NOT NULL , NOMBRE text, APELLIDOPATERNO text, APELLIDOMATERNO text);");
            //Tabla Horario, esta se crea por la relacion de la tabla Materias, con la tabla Salon, asi mismo a esta tabla se le agregara una llave compuesta como primaria que contendran las llaves primarias de la tabla Materia y Salon,ademas de sus respectivos atributos, Lunes, Martes, Miercoles, Jueves, Viernes.
            database.execSQL("create table Horario(IDNRC integer NOT NULL, IDSALON text NOT NULL,LUNES text,MARTES text, MIERCOLES text, JUEVES text, VIERNES text, PRIMARY KEY(IDNRC,IDSALON), FOREIGN KEY(IDNRC) REFERENCES Materias(NRC), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON));");
            //Tabla AcademicoSalon esta se crea por la relacion de la tabla de Salon y Academico, asi mismo a esta tabla se le agregara una llave compuesta como primaria que contendran las llaves primarias de Salon y Academico
            database.execSQL("create table AcademicoSalon(IDPERSONAL integer NOT NULL, IDSALON text NOT NULL, PRIMARY KEY(IDPERSONAL,IDSALON), FOREIGN KEY(IDPERSONAL) REFERENCES Academico(NUMPERSONAL), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON))");
            //Tabla MateriaSalon esta se crea por la relacion de la tabla de Materia y Salon, asi mismo a esta tabla se le agregara una llave compuesta como primaria que contendran las llaves primarias de Materia con Salon
            database.execSQL("create table MateriaSalon(IDNRC integer, IDSALON, PRIMARY KEY(IDNRC,IDSALON), FOREIGN KEY(IDNRC) REFERENCES Materias(NRC), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON))");
            //Toast.makeText(this,"Creado",Toast.LENGTH_LONG).show();

        database.setTransactionSuccessful();
        }catch(SQLException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            database.endTransaction();
        }
    }

    public void insertarMateria(){
        database.beginTransaction();
        try {

            database.execSQL("insert into Materias(NRC,CARRERA,EE,BLOQUE,SECCION,IDPERSONAL) values " +
                    "(72330,'ISO','FUNDAMENTOS DE MATEMATICAS',1,1,0001),"+
                    "(73231,'ISO','INTRODUCCION A LA PROGRAMACION',1,1,0002),"+
                    "(73272,'ISO','HABILIDADES DEL PENSAMIENTO',1,1,0003),"+
                    "(96703,'ISO','LECTURA Y REDACCION',1,1,0004),"+
                    "(73274,'ISO','INGLES I',1,1,0005),"+
                    "(88210,'ISO','COMPUTACION BASICA',1,1,0006)"+";");

            database.setTransactionSuccessful();
        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            database.endTransaction();
        }
    }

    private void insertarDatosSalon(){
        database.beginTransaction();
        try{
            database.execSQL("insert into Salon(NUMSALON,EDIFICIO) values" +
                    "('102','ECONEX')," +
                    "('CC2','ECONEX')," +
                    "('LABRED','FEI')," +
                    "('F404','FEI')," +
                    "('106','ECONEX')," +
                    "('105','ECONEX')," +
                    "('F101','FEI')," +
                    "('FTC','FEI')," +
                    "('CC3','ECONEX'),"+
                    "('104','ECONEX')," +
                    "('CC1','ECONEX')," +
                    "('CIDI','ECONEX')," +
                    "('F101','FEI'),"+
                    "('F103','FEI'),"+
                    "('6','ECONEX'),"+
                    "('4','ECONEX'),"+
                    "('107','ECONEX'),"+
                    "('F403','FEI'),"+
                    "('F102','FEI'),"+
                    "('5','ECONEX'),"+
                    "('111','ECONEX'),"+
                    "('112','ECONEX'),"+
                    "('F402','FEI'),"+
                    "('113','ECONEX'),"+
                    "('214','ECONEX'),"+
                    "('103','ECONEX'),"+
                    "('CDS','FEI')"+";");

            Toast.makeText(this, "Agregados exitosamente", Toast.LENGTH_SHORT).show();
            database.setTransactionSuccessful();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            database.endTransaction();
        }
    }


    public void Usuario(View view){
        Intent usuario = new Intent(this,Usuario.class);
        startActivity(usuario);
    }

    public void Administrador(View view){
        Intent admin = new Intent(this, inicio_sesion.class);
        startActivity(admin);
    }

}