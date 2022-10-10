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
            //crearTriggerMateriaSalon();
            insertarDatosAcademico();
            insertarDatosAcademicoSalon();
            insertarHorario();
            insertarDatosMateriaSalon();
            //consultaMateriasa();
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


            db.execSQL("create table Materias(NRC integer PRIMARY KEY NOT NULL, CARRERA text, EE text, IDPERSONAL integer, FOREIGN KEY(IDPERSONAL) REFERENCES Academico(NUMPERSONAL));");

            db.execSQL("create table Salon(NUMSALON text PRIMARY KEY NOT NULL, EDIFICIO text);");

            db.execSQL("create table Academico(NUMPERSONAL integer PRIMARY KEY NOT NULL, ACADEMICO text, APELLIDOPATERNO text, APELLIDOMATERNO text);");

            db.execSQL("create table Horario(IDNRC integer NOT NULL, IDSALON text NOT NULL,LUNES text,MARTES text, MIERCOLES text, JUEVES text, VIERNES text, PRIMARY KEY(IDNRC,IDSALON), FOREIGN KEY(IDNRC) REFERENCES Materias(NRC), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON));");

            db.execSQL("create table AcademicoSalon(IDPERSONAL integer NOT NULL, IDSALON text NOT NULL, PRIMARY KEY(IDPERSONAL,IDSALON), FOREIGN KEY(IDPERSONAL) REFERENCES Academico(NUMPERSONAL), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON))");

            db.execSQL("create table MateriaSalon(IDNRC integer, IDSALON, PRIMARY KEY(IDNRC,IDSALON), FOREIGN KEY(IDNRC) REFERENCES Materias(NRC), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON))");
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
            db.execSQL("insert into Materias(NRC,CARRERA,EE,IDPERSONAL) values " +
                    "(73230,'ISOF','FUNDAMENTOS DE MATEMATICAS',0001)," +
                    "(73236,'ISOF','FUNDAMENTOS DE MATEMATICAS',0002)," +
                    "(73231,'ISOF','INTRODUCCON A LA PROGRAMACION',0003)," +
                    "(73237,'ISOF','INTRODUCCION A LA PROGRAMACION',0004)," +
                    "(73272,'ISOF','HABILIDADES DEL PENSAMIENTO',0005)"+";");

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
            db.execSQL("insert into AcademicoSalon(IDPERSONAL,IDSALON) values" +
                    "(0001, '104')," +
                    "(0002,'104')," +
                    "(0002,'106')");
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
            db.execSQL("insert into MateriaSalon(IDNRC,IDSALON) values" +
                    "(73230,'104')," +
                    "(73236,'104')," +
                    "(73236,'106')");
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void insertarDatosSalon(){
        db.beginTransaction();
        try{
            db.execSQL("insert into Salon(NUMSALON,EDIFICIO) values" +
                    "('104','ECONEX')," +
                    "('106','ECONEX')," +
                    "('108','ECONEX');");

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
            db.execSQL("insert into Academico(NUMPERSONAL, ACADEMICO, APELLIDOPATERNO, APELLIDOMATERNO) values" +
                    "(0001,'MARIBEL','CARMONA','GARCIA')," +
                    "(0002,'JOSE JUAN','MUÑOZ','LEON');");

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
            db.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES,JUEVES ) values" +
                    "(73230,'104','08:00-09:00','09:00-11:00','07:00-09:00')");

            db.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES) values" +
                    "(73236,'104','14:00-15:00','13:00-15:00');");

            db.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values" +
                    "(73236,'106','13:00-15:00')");


            //db.execSQL("insert into Horario()");

            db.setTransactionSuccessful();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            db.endTransaction();
        }
    }

   /* private void crearTriggerMateriaSalon(){
        db.beginTransaction();
        try{
            db.execSQL("create trigger triger_MateriaSalon " +
                    "BEFORE INSERT on Materias " +
                    "BEGIN " +
                    "insert into MateriaSalon(IDNRC) " +
                    "values(NEW.NRC);" +
                    "END;");

            db.execSQL("create trigger triger_MateriaSa " +
                    "BEFORE INSERT on Salon " +
                    "BEGIN " +
                    "insert into MateriaSalon(IDSALON) " +
                    "values(NEW.NUMSALON);" +
                    "END;");

            db.setTransactionSuccessful();
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            db.endTransaction();
        }
    }*/

    private void consultaMateriasa(){
        String idnrc,idsalon;
        try{
            String sql = "select IDNRC,IDSALON from MateriaSalon";
            Cursor c = db.rawQuery(sql,null);
            int uno = c.getColumnIndex("IDNRC");
            int dos = c.getColumnIndex("IDSALON");
            while(c.moveToNext()){
                idnrc = c.getString(uno);
                idsalon = c.getString(dos);
                txm.append("\n" + "IDNRC: " + idnrc + "\n" + "IDSALON: " + idsalon + "\n");
            }
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

        private void consultaHorarioMateria(){
        String facu,ee, lune,marte,mier,jue,vier;
        //HAY PEDOS
        try{
            String sql = "select CARRERA,EE,LUNES,MARTES,MIERCOLES,JUEVES,VIERNES from Materias m INNER JOIN Horario h ON m.NRC = h.IDNRC WHERE h.Lunes= '13:00-15:00' or h.Martes='13:00-15:00' or h.Miercoles='13:00-15:00' or h.JUEVES = '13:00-15:00' or h.VIERNES = '13:00-15:00'";
            Cursor c = db.rawQuery(sql,null);
            int fac = c.getColumnIndex("CARRERA");
            int mate = c.getColumnIndex("EE");
            int lu = c.getColumnIndex("LUNES");
            int ma = c.getColumnIndex("MARTES");
            int mi = c.getColumnIndex("MIERCOLES");
            int ju = c.getColumnIndex("JUEVES");
            int vi = c.getColumnIndex("VIERNES");
            while(c.moveToNext()){
                facu = c.getString(fac);
                ee = c.getString(mate);
                lune = c.getString(lu);
                marte = c.getString(ma);
                mier = c.getString(mi);
                jue = c.getString(ju);
                vier = c.getString(vi);
                txm.append("\n" + "CARRERA: "+ facu + "\n"+ "EE: " + ee + "\n" + "LUNES: " + lune + "\n" + "MARTES: " + marte + "\n" + "MIERCOLES " + mier +
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
            String sql = "select NUMSALON, EDIFICIO, ACADEMICO,APELLIDOPATERNO, APELLIDOMATERNO from Salon AS s INNER JOIN AcademicoSalon AS ac ON s.NUMSALON = ac.IDSALON INNER JOIN Academico AS a ON ac.IDPERSONAL = a.NUMPERSONAL WHERE s.NUMSALON = '104'";
            Cursor c = db.rawQuery(sql,null);
            int sa = c.getColumnIndex("NUMSALON");
            int edi = c.getColumnIndex("EDIFICIO");
            int nom = c.getColumnIndex("ACADEMICO");
            int ape = c.getColumnIndex("APELLIDOPATERNO");
            int apemm = c.getColumnIndex("APELLIDOMATERNO");
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
            String sql = "select EE, CARRERA, ACADEMICO, APELLIDOPATERNO,APELLIDOMATERNO, NUMSALON, EDIFICIO from Materias AS m INNER JOIN Academico AS a ON m.IDPERSONAL = a.NUMPERSONAL INNER JOIN AcademicoSalon AS ac ON a.NUMPERSONAL = ac.IDPERSONAL INNER JOIN Salon AS s ON ac.IDSALON = s.NUMSALON WHERE m.EE = 'FUNDAMENTOS DE MATEMATICAS'";
            Cursor c = db.rawQuery(sql,null);
            int eee = c.getColumnIndex("EE");
            int fac = c.getColumnIndex("CARRERA");
            int nom = c.getColumnIndex("ACADEMICO");
            int ape = c.getColumnIndex("APELLIDOPATERNO");
            int apemm = c.getColumnIndex("APELLIDOMATERNO");
            int sa = c.getColumnIndex("NUMSALON");
            int ed = c.getColumnIndex("EDIFICIO");
            while(c.moveToNext()) {
                ee = c.getString(eee);
                facu = c.getString(fac);
                nombre = c.getString(nom);
                apep = c.getString(ape);
                apem = c.getString(apemm);
                salon = c.getString(sa);
                edifi = c.getString(ed);

                txm.append("\n" + "EE: " + ee + "\n" + "CARRERA: " + facu + "\n" + "NOMBRE: "+ nombre + "\n" + "APELLIDOP: "+ apep + "\n" + "APELLIDOM: " + apem + "\n" + "SALON: " + salon + "\n" + "EDIFICIO: " + edifi + "\n");
            }
            }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

        private void consultaMateriaSalonCarrera(){
            String ee, facu, salon, edificio;
            try{
                String sql  = "select  CARRERA, EE,NUMSALON, EDIFICIO from Materias AS m INNER JOIN MateriaSalon AS ms ON m.NRC = ms.IDNRC INNER JOIN Salon AS s ON ms.IDSALON = s.NUMSALON WHERE m.CARRERA = 'ISOF'";
                Cursor c = db.rawQuery(sql,null);
                int fac = c.getColumnIndex("CARRERA");
                int eee = c.getColumnIndex("EE");
                int sa = c.getColumnIndex("NUMSALON");
                int ed = c.getColumnIndex("EDIFICIO");
                while(c.moveToNext()){
                    ee = c.getString(eee);
                    facu = c.getString(fac);
                    salon = c.getString(sa);
                    edificio = c.getString(ed);
                    txm.append("\n" + "EE: " + ee + "\n" + "CARRERA: " + facu + "\n" + "SALON: "+ salon + "\n" + "EDIFICIO: " + edificio + "\n");
                }

            }catch (SQLiteException e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

        private void consultaHorarioSalonProfe(){
            String nombre,apep,apem,salon,edi;
            try{
                String sql = "select ACADEMICO,APELLIDOPATERNO,APELLIDOMATERNO, NUMSALON, EDIFICIO from Academico AS a INNER JOIN AcademicoSalon AS ac ON a.NUMPERSONAL=ac.IDPERSONAL INNER JOIN Salon AS s ON ac.IDSALON=s.NUMSALON INNER JOIN Horario AS h ON s.NUMSALON=h.IDSALON WHERE h.VIERNES = '13:00-15:00';";
                Cursor c = db.rawQuery(sql,null);
                int nom = c.getColumnIndex("ACADEMICO");
                int ap = c.getColumnIndex("APELLIDOPATERNO");
                int apm = c.getColumnIndex("APELLIDOMATERNO");
                int sa = c.getColumnIndex("NUMSALON");
                int ed = c.getColumnIndex("EDIFICIO");
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
