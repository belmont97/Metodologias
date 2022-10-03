package com.example.uv;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.transition.CircularPropagation;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    TextView txm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txm = (TextView) findViewById(R.id.text1);

        try {
            openDatabase();
            createTable();
            insertarDatosMaterias();
            insertarDatosSalon();
            insertarDatosAcademico();
            insertarDatosAcademicoSalon();
            insertarHorario();
            insertarDatosMateriaSalon();
            txm.append("\n Como usuario puedo consultar información de materias según un horario especificado para saber qué materias se imparten en ese horario \n");
            consultaHorarioMateria();
            txm.append("\n Como usuario puedo consultar información sobre profesores según un horario dado para saber los salones en los que da clases \n");
            consultaHorarioSalonProfe();
            txm.append("\n Como usuario puedo consultar información sobre profesores según el salón dado para saber qué profesores imparten clases en ese salón \n");
            consultaSalonProfe();
            txm.append("\n Como usuario quiero puedo consultar por materias, qué profesores las imparten y en qué salones\n");
            consultaMateriaProfeSalon();
            txm.append("\n Como usuario del sistema puedo consultar información sobre las materias que se imparten en un salón según la carrera que se indique\n");
            consultaMateriaSalonCarrera();
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void openDatabase() {
        try {
            db = SQLiteDatabase.openDatabase("data/data/com.example.uv/Aulas", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            Toast.makeText(this, "DB was opened!", Toast.LENGTH_LONG).show();
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }//createDatabase

    private void createTable(){
        db.beginTransaction();
        try{


            db.execSQL("create table Materias(NRC integer PRIMARY KEY NOT NULL, Facultad text, NombreEE text, IDPERSONAL integer, FOREIGN KEY(IDPERSONAL) REFERENCES Academico(NumPersonal));");

            db.execSQL("create table Salon(NumSalon text PRIMARY KEY NOT NULL, Edificio text);");

            db.execSQL("create table Academico(NumPersonal integer PRIMARY KEY NOT NULL, Nombre text, ApellidoP text, ApellidoM text);");

            db.execSQL("create table Horario(IDNRC integer NOT NULL, IDSalon text NOT NULL,Lunes text,Martes text, Miercoles text, Jueves text, Viernes text, PRIMARY KEY(IDNRC,IDSalon), FOREIGN KEY(IDNRC) REFERENCES Materias(NRC), FOREIGN KEY(IDSalon) REFERENCES Salon(NumSalon));");

            db.execSQL("create table AcademicoSalon(IDPERSONAL integer NOT NULL, IDSALON text NOT NULL, PRIMARY KEY(IDPERSONAL,IDSALON), FOREIGN KEY(IDPERSONAL) REFERENCES Academico(NumPersonal), FOREIGN KEY(IDSALON) REFERENCES Salon(NumSalon))");

            db.execSQL("create table MateriaSalon(IDNRC integer, IDSALON, PRIMARY KEY(IDNRC,IDSALON), FOREIGN KEY(IDNRC) REFERENCES Materias(NRC), FOREIGN KEY(IDSALON) REFERENCES Salon(NumSalon))");
            //Toast.makeText(this,"Creado",Toast.LENGTH_LONG).show();
        }catch(SQLException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void dropTable(String Tabla){
        try {
            db.execSQL(" drop table "+ Tabla+";");
            Toast.makeText(this,"eliminado",Toast.LENGTH_LONG).show();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void insertarDatosMaterias(){
        db.beginTransaction();
        try {
            db.execSQL("insert into Materias(NRC,Facultad,NombreEE,IDPERSONAL) values " +
                    "(2020,'TECO','Programación',1111)," +
                    "(2021, 'TECO','Ingeniería de Software',1011)," +
                    "(1919,'TECO','Programación Avanzada',1110)," +
                    "(1012,'REDES','Administración de Servidores',1010)" +
                    ";");

            Toast.makeText(this,"insertados",Toast.LENGTH_LONG).show();
            db.setTransactionSuccessful();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            // finish transaction processing
            db.endTransaction();
        }
    }

    private void insertarDatosAcademicoSalon(){
        db.beginTransaction();
        try{
            db.execSQL("insert into AcademicoSalon(IDPERSONAL,IDSALON) values(1111, 'F102'),(1011,'CC2')");
            db.setTransactionSuccessful();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally{
            db.endTransaction();
        }
    }

    private void insertarDatosMateriaSalon(){
        db.beginTransaction();
        try{
            db.execSQL("insert into MateriaSalon(IDNRC,IDSALON) values(2020,'F102')");
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void insertarDatosSalon(){
        db.beginTransaction();
        try{
            db.execSQL("insert into Salon(NumSalon,Edificio) values('F102','FEI')," +
                    "('CC2','Econex')");

            db.setTransactionSuccessful();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            db.endTransaction();
        }
    }

    private void insertarDatosAcademico(){
        db.beginTransaction();
        try{
            db.execSQL("insert into Academico(NumPersonal, Nombre, ApellidoP, ApellidoM) values(1010,'Rodolfo','Gutierrez','Velasco')," +
                    "(1111,'Jaimito','Gutierrez','Lopez');");
            db.setTransactionSuccessful();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            db.endTransaction();
        }

    }

    private void insertarHorario(){
        db.beginTransaction();
        try{
            db.execSQL("insert into Horario(IDNRC,IDSALON,Martes,Miercoles,Viernes ) " +
                    "values(2020,'F102','15:00-17:00','15:00-17:00','13:00-15:00')," +
                    "(1919,'CC2','11:00-13:00','11:00-13:00','11:00-13:00')");


            db.setTransactionSuccessful();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            db.endTransaction();
        }
    }

        private void consultaHorarioMateria(){
        String facu,ee, lune,marte,mier,jue,vier;
        try{
            String sql = "select Facultad,NombreEE,Lunes,Martes,Miercoles,Jueves,Viernes from Materias m INNER JOIN Horario h ON m.NRC = h.IDNRC WHERE h.Martes = '11:00-13:00'";
            Cursor c = db.rawQuery(sql,null);
            int fac = c.getColumnIndex("Facultad");
            int mate = c.getColumnIndex("NombreEE");
            int lu = c.getColumnIndex("Lunes");
            int ma = c.getColumnIndex("Martes");
            int mi = c.getColumnIndex("Miercoles");
            int ju = c.getColumnIndex("Jueves");
            int vi = c.getColumnIndex("Viernes");
            while(c.moveToNext()){
                facu = c.getString(fac);
                ee = c.getString(mate);
                lune = c.getString(lu);
                marte = c.getString(ma);
                mier = c.getString(mi);
                jue = c.getString(ju);
                vier = c.getString(vi);
                txm.append("\n" + "FACULTAD: "+ facu + "\n"+ "EE: " + ee + "\n" + "LUNES: " + lune + "\n" + "MARTES: " + marte + "\n" + "MIERCOLES " + mier +
                "\n" + "JUEVES: " + jue + "\n" + "VIERNES: " + vier + "\n");
            }
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

        private void consultaHorarioMateriaSalon(){
        String facu,ee,lune,marte,mier,jue,vier,salon,edi;
        try {
            String sql = "select Facultad,NombreEE,Lunes,Martes,Miercoles,Jueves,Viernes,NumSalon,Edificio from Materias as m INNER JOIN Horario as h ON m.NRC = h.IDNRC INNER JOIN Salon as s ON h.IDSALON = s.NumSalon";
            Cursor c = db.rawQuery(sql,null);
            int fac = c.getColumnIndex("Facultad");
            int mate = c.getColumnIndex("NombreEE");
            int lu = c.getColumnIndex("Lunes");
            int ma = c.getColumnIndex("Martes");
            int mi = c.getColumnIndex("Miercoles");
            int ju = c.getColumnIndex("Jueves");
            int vi = c.getColumnIndex("Viernes");
            int sa = c.getColumnIndex("NumSalon");
            int ed = c.getColumnIndex("Edificio");
            while(c.moveToNext()){
                facu = c.getString(fac);
                ee = c.getString(mate);
                lune = c.getString(lu);
                marte = c.getString(ma);
                mier = c.getString(mi);
                jue = c.getString(ju);
                vier = c.getString(vi);
                salon = c.getString(sa);
                edi = c.getString(ed);
                txm.append("\n" + "FACULTAD: "+ facu + "\n"+ "EE: " + ee + "\n" + "LUNES: " + lune + "\n" + "MARTES: " + marte + "\n" + "MIERCOLES " + mier +
                        "\n" + "JUEVES: " + jue + "\n" + "VIERNES: " + vier + "\n" + "SALON: "+ salon + "\n" + "EDIFICIO: " + edi  +"\n");
            }
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

        private void consultaSalonProfe(){
        String salon, edifi, nombre, apep,apem;
        try{
            String sql = "select NumSalon, Edificio, Nombre,ApellidoP, ApellidoM from Salon AS s INNER JOIN AcademicoSalon AS ac ON s.NumSalon = ac.IDSALON INNER JOIN Academico AS a ON ac.IDPERSONAL = a.NumPersonal WHERE s.NumSalon = 'F102'";
            Cursor c = db.rawQuery(sql,null);
            int sa = c.getColumnIndex("NumSalon");
            int edi = c.getColumnIndex("Edificio");
            int nom = c.getColumnIndex("Nombre");
            int ape = c.getColumnIndex("ApellidoP");
            int apemm = c.getColumnIndex("ApellidoM");
            while(c.moveToNext()){
                salon  = c.getString(sa);
                edifi = c.getString(edi);
                nombre = c.getString(nom);
                apep = c.getString(ape);
                apem = c.getString(apemm);
                txm.append("\n" + "SALON: " + salon + "\n" + "EDIFICIO: " + edifi + "\n" + "NOMBRE: " + nombre + "\n" + "APELLIDOP: "+ apep + "\n" + "APELLIDOM: " + apem + "\n");
            }
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

        private void consultaMateriaProfeSalon(){
        String ee, facu, nombre, apep,apem, salon, edifi;
        try{
            String sql = "select NombreEE, Facultad, Nombre, ApellidoP,ApellidoM, NumSalon, Edificio from Materias AS m INNER JOIN Academico AS a ON m.IDPERSONAL = a.NumPersonal INNER JOIN AcademicoSalon AS ac ON a.NumPersonal = ac.IDPERSONAL INNER JOIN Salon AS s ON ac.IDSALON = s.NumSalon WHERE m.NombreEE = 'Programación'";
            Cursor c = db.rawQuery(sql,null);
            int eee = c.getColumnIndex("NombreEE");
            int fac = c.getColumnIndex("Facultad");
            int nom = c.getColumnIndex("Nombre");
            int ape = c.getColumnIndex("ApellidoP");
            int apemm = c.getColumnIndex("ApellidoM");
            int sa = c.getColumnIndex("NumSalon");
            int ed = c.getColumnIndex("Edificio");
            while(c.moveToNext()) {
                ee = c.getString(eee);
                facu = c.getString(fac);
                nombre = c.getString(nom);
                apep = c.getString(ape);
                apem = c.getString(apemm);
                salon = c.getString(sa);
                edifi = c.getString(ed);

                txm.append("\n" + "EE: " + ee + "\n" + "FACULTAD: " + facu + "\n" + "NOMBRE: "+ nombre + "\n" + "APELLIDOP: "+ apep + "\n" + "APELLIDOM: " + apem + "\n" + "SALON: " + salon + "\n" + "EDIFICIO: " + edifi + "\n");
            }
            }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

        private void consultaMateriaSalonCarrera(){
            String ee, facu, salon, edificio;
            try{
                String sql  = "select NombreEE, Facultad, NumSalon, Edificio from Materias AS m INNER JOIN MateriaSalon AS ms ON m.NRC = ms.IDNRC INNER JOIN Salon AS s ON ms.IDSALON = s.NumSalon WHERE m.Facultad = 'TECO'";
                Cursor c = db.rawQuery(sql,null);
                int eee = c.getColumnIndex("NombreEE");
                int fac = c.getColumnIndex("Facultad");
                int sa = c.getColumnIndex("NumSalon");
                int ed = c.getColumnIndex("Edificio");
                while(c.moveToNext()){
                    ee = c.getString(eee);
                    facu = c.getString(fac);
                    salon = c.getString(sa);
                    edificio = c.getString(ed);
                    txm.append("\n" + "EE: " + ee + "\n" + "FACULTAD: " + facu + "\n" + "SALON: "+ salon + "\n" + "EDIFICIO: " + edificio + "\n");
                }

            }catch (SQLiteException e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

        private void consultaHorarioSalonProfe(){
            String nombre,apep,apem,salon,edi;
            try{
                String sql = "select Nombre,ApellidoP,ApellidoM, NumSalon, Edificio from Horario AS h INNER JOIN Salon AS s ON h.IDSALON = s.NumSalon INNER JOIN AcademicoSalon AS ac ON s.NumSalon = ac.IDSALON INNER JOIN Academico AS a ON ac.IDPERSONAL = a.NumPersonal WHERE h.Martes = '15:00-17:00'";
                Cursor c = db.rawQuery(sql,null);
                int nom = c.getColumnIndex("Nombre");
                int ap = c.getColumnIndex("ApellidoP");
                int apm = c.getColumnIndex("ApellidoM");
                int sa = c.getColumnIndex("NumSalon");
                int ed = c.getColumnIndex("Edificio");
                while(c.moveToNext()){
                    nombre = c.getString(nom);
                    apep = c.getString(ap);
                    apem = c.getString(apm);
                    salon = c.getString(sa);
                    edi = c.getString(ed);
                    txm.append("\n" + "NOMBRE " + nombre + "\n" + "APELLIDOP: " + apep + "\n" + "APELLIDOM: " + apem + "\n" + "SALON: " + salon + "\n" + "EDIFICIO: "+ edi + "\n");
                }
            }catch(SQLiteException e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
}
