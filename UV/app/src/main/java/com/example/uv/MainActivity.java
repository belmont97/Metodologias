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
        //Es la variable la cual se utilizara para mostrar la información en la interfaz del sistema
        txm = (TextView) findViewById(R.id.text1);
        try {
            //Menu donde llamaremos a los metodos que se ejucataran cuando se ejecute el programa completo
            openDatabase();
            createTable();
            //getTriggerMateriaSalon();
            insertarDatosMaterias();
            insertarDatosSalon();

            insertarDatosAcademico();
            insertarDatosAcademicoSalon();
            insertarHorario();
            insertarDatosMateriaSalon();
            consultaMateriasa();
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
        //Se crea la base de datos de manera local
        try {
            //Se indica donde la ruta donde guardara la base de datos, ademas pregunta si ya esta creada si no la crea si es necesario
            db = SQLiteDatabase.openDatabase("data/data/com.example.uv/Aulas", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            Toast.makeText(this, "DB was opened!", Toast.LENGTH_LONG).show();
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }//createDatabase

    private void createTable(){
        //Creación de todas la tablas que se necesitaran
        db.beginTransaction();
        try{
            //Tabla Materias que se compone de NRC llave primaria, Carrera, Experiencia Educativa, Seccion, Bloque,Id_Personal del academico que la va a impartir
            db.execSQL("create table Materias(NRC integer PRIMARY KEY NOT NULL, CARRERA text, EE text, BLOQUE integer,SECCION integer,IDPERSONAL integer, FOREIGN KEY(IDPERSONAL) REFERENCES Academico(NUMPERSONAL));");
            //Tabla Salon que se compone de Numero de Salon llave primaria y Edificio
            db.execSQL("create table Salon(NUMSALON text PRIMARY KEY NOT NULL, EDIFICIO text);");
            //Tabla Academico que se compone de Numero Personal, Nombre, Appelido Paterno, Apellido Materno
            db.execSQL("create table Academico(NUMPERSONAL integer PRIMARY KEY NOT NULL, NOMBRE text, APELLIDOPATERNO text, APELLIDOMATERNO text);");
            //Tabla Horario, esta se crea por la relacion de la tabla Materias, con la tabla Salon, asi mismo a esta tabla se le agregara una llave compuesta como primaria que contendran las llaves primarias de la tabla Materia y Salon,ademas de sus respectivos atributos, Lunes, Martes, Miercoles, Jueves, Viernes.
            db.execSQL("create table Horario(IDNRC integer NOT NULL, IDSALON text NOT NULL,LUNES text,MARTES text, MIERCOLES text, JUEVES text, VIERNES text, PRIMARY KEY(IDNRC,IDSALON), FOREIGN KEY(IDNRC) REFERENCES Materias(NRC), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON));");
            //Tabla AcademicoSalon esta se crea por la relacion de la tabla de Salon y Academico, asi mismo a esta tabla se le agregara una llave compuesta como primaria que contendran las llaves primarias de Salon y Academico
            db.execSQL("create table AcademicoSalon(IDPERSONAL integer NOT NULL, IDSALON text NOT NULL, PRIMARY KEY(IDPERSONAL,IDSALON), FOREIGN KEY(IDPERSONAL) REFERENCES Academico(NUMPERSONAL), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON))");
            //Tabla MateriaSalon esta se crea por la relacion de la tabla de Materia y Salon, asi mismo a esta tabla se le agregara una llave compuesta como primaria que contendran las llaves primarias de Materia con Salon
            db.execSQL("create table MateriaSalon(IDNRC integer, IDSALON, PRIMARY KEY(IDNRC,IDSALON), FOREIGN KEY(IDNRC) REFERENCES Materias(NRC), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON))");
            //Toast.makeText(this,"Creado",Toast.LENGTH_LONG).show();
        }catch(SQLException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void dropTable(String Tabla){
        //Eliminacion de tablas
        //Este metodo no se utilizara por el momento
        try {
            db.execSQL(" drop table "+ Tabla+";");
            Toast.makeText(this,"eliminado",Toast.LENGTH_LONG).show();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void insertarDatosMaterias(){
        //En este metodo se agregaran todas la materias de las carreras, se iran comentando las
        db.beginTransaction();
        try {
            //Insertar Información de Materia de la Facultad de Ingeniería de Software
            db.execSQL("insert into Materias(NRC,CARRERA,EE,BLOQUE,SECCION,IDPERSONAL) values " +
                    "(73230,'ISOF','FUNDAMENTOS DE MATEMATICAS',1,1,0001)," +
                    "(73236,'ISOF','FUNDAMENTOS DE MATEMATICAS',1,2,0002)," +
                    "(73231,'ISOF','INTRODUCCON A LA PROGRAMACION',1,1,0003)," +
                    "(73237,'ISOF','INTRODUCCION A LA PROGRAMACION',1,2,0004)," +
                    "(73272,'ISOF','HABILIDADES DEL PENSAMIENTO  (TEST)',1,1,0005)," +
                    "(73273,'ISOF','HABILIDADES DEL PENSAMIENTO',1,2,0006)"+";");

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
                    "(0001,'EVERARDO FRANCISCO','GARCIA','MENIER')," +
                    "(0002,'JUAN MANUEL','GUTIERREZ','MENDEZ')" +
                    "(0003,'OSCAR','ALONSO','RAMIREZ')" +
                    "(0004,'EDSEL','ORTIZ','ROMERO')" +
                    "(0005,'MARIA DEL CARMEN','MEZURA','GODOY')\"+\n" +
                    "(0006,'PATRICIA','GONZALEZ','GASPAR')\"+\n" +
                    "(0007,'CARLOS','ILLESCAS','SANCHEZ')\"+\n" +
                    "(0008,'ARMINDA','BARRADAS','SANCHEZ')\"+\n" +
                    "(0009,'EDGAR IVAN','BENITEZ','GUERRERO')\"+\n" +
                    "(0010,'MARTHA PATRICIA','RODRIGUEZ','GUZMAN')\"+\n" +
                    "(0011,'JESUS ROBERTO','MENDEZ','ORTIZ')\"+\n" +
                    "(0012,'AQUILES','ORDUÑA','GONZALEZ')\"+\n" +
                    "(0013,'RUTH','RODRIGUEZ','RAMIREZ')\"+\n" +
                    "(0014,'LORENA','ALONSO','RAMIREZ')\"+\n" +
                    "(0015,'MARIBEL','CARMONA','GARCIA')\"+\n" +
                    "(0016,'MARIA DE LOURDES','HERNANDEZ','RODRIGUEZ')\"+\n" +
                    "(0017,'MARIA DE LOS ANGELES','NAVARRO','GUERRERO')\"+\n" +
                    "(0018,'VICTOR MANUEL','HERNANDEZ','OLIVERA')\"+\n" +
                    "(0019,'FREDY','CASTAÑEDA','SANCHEZ')\"+\n" +
                    "(0020,'PATRICIA','DIAZ','GASPAR')\"+\n" +
                    "(0021,'CRISTHIAN','PEREZ','SALAZAR')\"+\n" +
                    "(0022,'ULISES','MARINERO','AGUILAR')\"+\n" +
                    "(0023,'OSCAR','ALONSO','RAMIREZ')\"+\n" +
                    "(0024,'JOSE RAFAEL','ROJANO','CACERES')\"+\n" +
                    "(0025,'MARIA DOLORES','VARGAS','CERDAN')\"+\n" +
                    "(0026,'ERIKA','MENESES','RICO')\"+\n" +
                    "(0027,'JOSE GUILLERMO','HERNANDEZ','CALDERON')\"+\n" +
                    "(0028,'MARIA LUISA','CORDOBA','TLAXCALTECO')\"+\n" +
                    "(0029,'ALFONSO','SANCHEZ','OREA')\"+\n" +
                    "(0030,'LUIS GERARDO','MONTANE','JIMENEZ')\"+\n" +
                    "(0031,'DIANA ELIZABETH','VALDERRABANO','PEDRAZA')\"+\n" +
                    "(0032,'MARGARITA EDITH','CANAL','MARTINEZ')\"+\n" +
                    "(0033,'ALICIA YAZMIN','ROJAS','LUNA')\"+\n" +
                    "(0034,'RAMON DAVID','SARMIENTO','CERVANTES')\"+\n" +
                    "(0035,'ITZEL ALESSANDRA','REYES','FLORES')\"+\n" +
                    "(0036,'MAX WILLIAM','MILLAN','MARTINEZ')\"+\n" +
                    "(0037,'NIELS','MARTINEZ','GUEVARA')\"+\n" +
                    "(0038,'RAMON','GOMEZ','ROMERO')\"+\n" +
                    //MAESTROS REDES
                    "(0039,'Javier Sanchez Acosta')\"+\n" +
                    "(0040,'Maria de los Angeles Arenas Valdes')\"+\n" +
                    "(0041,'Reselia Osorio Armenta')\"+\n" +
                    "(0042,'Alfonso Duran Hernandez')\"+\n" +
                    "(0043,'Martha Elizabeth Dominguez Barcenas')\"+\n" +
                    "(0044,'Victor Manuel Tlapa Carrera')\"+\n" +
                    "(0038,'Viginia Lagunes Barradas')\"+\n" +
                    "(0038,'Arminda Sanchez Barradas')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0038,'')\"+\n" +
                    "(0000,'')\";");

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


            db.setTransactionSuccessful();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            db.endTransaction();
        }
    }

    //Por el momento funciona pero no de forma que esperaba, tendre que modificarlo
    private void getTriggerMateriaSalon(){
        //se crea el trigger para insertar la llave primaria de las tablas Materia, Salon para que se agregen a la tabla MateriaSalon de manera automatica
        db.beginTransaction();
        try{
            //trigger para agregar la llave primaria de la tabla Materias a MateriaSalon en el espacio que se le indica
            db.execSQL("create trigger triger_MateriaSalon " +
                    "BEFORE INSERT on Materias " +
                    "BEGIN " +
                    "insert into MateriaSalon(IDNRC) " +
                    "values(NEW.NRC);" +
                    "END;");
            //trigger para agregar la llave primaria de la tabla Salon a MateriaSalon en el espacio que se le indica
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
    }

    //Metodo para consulta
    private void consultaMateriasa(){
        //Es una consulta de la tabla MateriaSalon donde se muestran su campos que son la llaves foraneas de Materias y Salon
        //Variables que ocuparemos para guardar la informacion de la tuplas
        String idnrc,idsalon;
        try{
            //En un string guardamos la consulta
            String sql = "select IDNRC,IDSALON from MateriaSalon";
            //Aqui se colocara la instruccion que debe realizar la base de datos.
            Cursor c = db.rawQuery(sql,null);
            //Se guardan en variables de tipo entero, el numero de la columna, en este caso del IDNRC
            int idnrc1 = c.getColumnIndex("IDNRC");
            int idsalon1 = c.getColumnIndex("IDSALON");
            //En este bluque lo que hara sera revisar todas las tuplas de la tabla
            while(c.moveToNext()){
                //Se obtinen los datos de la columna por el numero de columna obtenido
                idnrc = c.getString(idnrc1);
                idsalon = c.getString(idsalon1);
                //Se muestran en patalla los datos
                txm.append("\n" + "IDNRC: " + idnrc + "\n" + "IDSALON: " + idsalon + "\n");
            }
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

        private void consultaHorarioMateria(){
        //Variables que ocuparemos para guardar la informacion de la tuplas
        String carrera,ee, lunes,martes,miercoles,jueves,viernes;

        try{
            //En un string guardamos la consulta
            String sql = "select CARRERA,EE,LUNES,MARTES,MIERCOLES,JUEVES,VIERNES from Materias m INNER JOIN Horario h ON m.NRC = h.IDNRC WHERE h.Lunes= '13:00-15:00' or h.Martes='13:00-15:00' or h.Miercoles='13:00-15:00' or h.JUEVES = '13:00-15:00' or h.VIERNES = '13:00-15:00'";
            //Aqui se colocara la instruccion que debe realizar la base de datos.
            Cursor c = db.rawQuery(sql,null);
            //Se guardan en variables de tipo entero, el numero de la columna
            int carrera1 = c.getColumnIndex("CARRERA");
            int materia = c.getColumnIndex("EE");
            int lunes1 = c.getColumnIndex("LUNES");
            int martes1 = c.getColumnIndex("MARTES");
            int miercoles1 = c.getColumnIndex("MIERCOLES");
            int jueves1 = c.getColumnIndex("JUEVES");
            int viernes1 = c.getColumnIndex("VIERNES");
            //En este bluque lo que hara sera revisar todas las tuplas de la tabla
            while(c.moveToNext()){
                //Se obtinen los datos de la columna por el numero de columna obtenido
                carrera = c.getString(carrera1);
                ee = c.getString(materia);
                lunes = c.getString(lunes1);
                martes = c.getString(martes1);
                miercoles = c.getString(miercoles1);
                jueves = c.getString(jueves1);
                viernes = c.getString(viernes1);
                //Se muestran en patalla los datos
                txm.append("\n" + "CARRERA: "+ carrera + "\n"+ "EE: " + ee + "\n" + "LUNES: " + lunes + "\n" + "MARTES: " + martes + "\n" + "MIERCOLES " + miercoles +
                "\n" + "JUEVES: " + jueves + "\n" + "VIERNES: " + viernes + "\n");
            }
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

        private void consultaHorarioMateriaSalon(){
        String carrera,ee,lunes,martes,miercoles,jueves,viernes,salon,edificio;
        try {
            //En un string guardamos la consulta
            String sql = "select Facultad,NombreEE,Lunes,Martes,Miercoles,Jueves,Viernes,NumSalon,Edificio from Materias as m INNER JOIN Horario as h ON m.NRC = h.IDNRC INNER JOIN Salon as s ON h.IDSALON = s.NumSalon";
            //Aqui se colocara la instruccion que debe realizar la base de datos.
            Cursor c = db.rawQuery(sql,null);
            //Se guardan en variables de tipo entero, el numero de la columna
            int carrera1 = c.getColumnIndex("Facultad");
            int materia = c.getColumnIndex("NombreEE");
            int lunes1 = c.getColumnIndex("Lunes");
            int martes1 = c.getColumnIndex("Martes");
            int miercoles1 = c.getColumnIndex("Miercoles");
            int jueves1 = c.getColumnIndex("Jueves");
            int viernes1 = c.getColumnIndex("Viernes");
            int salon1 = c.getColumnIndex("NumSalon");
            int edificio1 = c.getColumnIndex("Edificio");
            //En este bluque lo que hara sera revisar todas las tuplas de la tabla
            while(c.moveToNext()){
                //Se obtinen los datos de la columna por el numero de columna obtenido
                carrera = c.getString(carrera1);
                ee = c.getString(materia);
                lunes = c.getString(lunes1);
                martes = c.getString(martes1);
                miercoles = c.getString(miercoles1);
                jueves = c.getString(jueves1);
                viernes = c.getString(viernes1);
                salon = c.getString(salon1);
                edificio = c.getString(edificio1);
                //Se muestran en patalla los datos
                txm.append("\n" + "FACULTAD: "+ carrera + "\n"+ "EE: " + ee + "\n" + "LUNES: " + lunes + "\n" + "MARTES: " + martes + "\n" + "MIERCOLES " + miercoles +
                        "\n" + "JUEVES: " + jueves + "\n" + "VIERNES: " + viernes + "\n" + "SALON: "+ salon + "\n" + "EDIFICIO: " + edificio  +"\n");
            }
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

        private void consultaSalonProfe(){
        String salon, edificio, nombre, apellidop,apellidom;
        try{
            //En un string guardamos la consulta
            String sql = "select NUMSALON, EDIFICIO, ACADEMICO,APELLIDOPATERNO, APELLIDOMATERNO from Salon AS s INNER JOIN AcademicoSalon AS ac ON s.NUMSALON = ac.IDSALON INNER JOIN Academico AS a ON ac.IDPERSONAL = a.NUMPERSONAL WHERE s.NUMSALON = '104'";
            //Aqui se colocara la instruccion que debe realizar la base de datos.
            Cursor c = db.rawQuery(sql,null);
            //Se guardan en variables de tipo entero, el numero de la columna
            int salon1 = c.getColumnIndex("NUMSALON");
            int edificio1 = c.getColumnIndex("EDIFICIO");
            int nombre1 = c.getColumnIndex("ACADEMICO");
            int apellidop1 = c.getColumnIndex("APELLIDOPATERNO");
            int apellidom1 = c.getColumnIndex("APELLIDOMATERNO");
            //En este bluque lo que hara sera revisar todas las tuplas de la tabla
            while(c.moveToNext()){
                //Se obtinen los datos de la columna por el numero de columna obtenido
                salon  = c.getString(salon1);
                edificio = c.getString(edificio1);
                nombre = c.getString(nombre1);
                apellidop = c.getString(apellidop1);
                apellidom = c.getString(apellidom1);
                //Se muestran en patalla los datos
                txm.append("\n" + "SALON: " + salon + "\n" + "EDIFICIO: " + edificio + "\n" + "NOMBRE: " + nombre + "\n" + "APELLIDOP: "+ apellidop + "\n" + "APELLIDOM: " + apellidom + "\n");
            }
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

        private void consultaMateriaProfeSalon(){
        //Variables que ocuparemos para guardar la informacion de la tuplas
        String ee, carrera, nombre, apellidop,apellidom, salon, edificio;
        try{
            //En un string guardamos la consulta
            String sql = "select EE, CARRERA, ACADEMICO, APELLIDOPATERNO,APELLIDOMATERNO, NUMSALON, EDIFICIO from Materias AS m INNER JOIN Academico AS a ON m.IDPERSONAL = a.NUMPERSONAL INNER JOIN AcademicoSalon AS ac ON a.NUMPERSONAL = ac.IDPERSONAL INNER JOIN Salon AS s ON ac.IDSALON = s.NUMSALON WHERE m.EE = 'FUNDAMENTOS DE MATEMATICAS'";
            //Aqui se colocara la instruccion que debe realizar la base de datos.
            Cursor c = db.rawQuery(sql,null);
            //Se guardan en variables de tipo entero, el numero de la columna
            int materia = c.getColumnIndex("EE");
            int carrera1 = c.getColumnIndex("CARRERA");
            int nombre1 = c.getColumnIndex("ACADEMICO");
            int apellidopaterno = c.getColumnIndex("APELLIDOPATERNO");
            int apellidomaterno = c.getColumnIndex("APELLIDOMATERNO");
            int salon1 = c.getColumnIndex("NUMSALON");
            int edificio1 = c.getColumnIndex("EDIFICIO");
            //En este bluque lo que hara sera revisar todas las tuplas de la tabla
            while(c.moveToNext()) {
                //Se obtinen los datos de la columna por el numero de columna obtenido
                ee = c.getString(materia);
                carrera = c.getString(carrera1);
                nombre = c.getString(nombre1);
                apellidop = c.getString(apellidopaterno);
                apellidom = c.getString(apellidomaterno);
                salon = c.getString(salon1);
                edificio = c.getString(edificio1);
                //Se muestran en patalla los datos
                txm.append("\n" + "EE: " + ee + "\n" + "CARRERA: " + carrera + "\n" + "NOMBRE: "+ nombre + "\n" + "APELLIDOP: "+ apellidop + "\n" + "APELLIDOM: " + apellidom + "\n" + "SALON: " + salon + "\n" + "EDIFICIO: " + edificio + "\n");
            }
            }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

        private void consultaMateriaSalonCarrera(){
        //Metodo para hacer una consulta por la carrera
            //Variables que ocuparemos para guardar la informacion de la tuplas
            String ee, carrera, salon, edificio;
            try{
                //En un string guardamos la consulta
                String sql  = "select  CARRERA, EE,NUMSALON, EDIFICIO from Materias AS m INNER JOIN MateriaSalon AS ms ON m.NRC = ms.IDNRC INNER JOIN Salon AS s ON ms.IDSALON = s.NUMSALON WHERE m.CARRERA = 'ISOF'";
                //Aqui se colocara la instruccion que debe realizar la base de datos.
                Cursor c = db.rawQuery(sql,null);
                //Se guardan en variables de tipo entero, el numero de la columna
                int carrera1 = c.getColumnIndex("CARRERA");
                int materia = c.getColumnIndex("EE");
                int salon1 = c.getColumnIndex("NUMSALON");
                int edificio1 = c.getColumnIndex("EDIFICIO");
                //En este bluque lo que hara sera revisar todas las tuplas de la tabla
                while(c.moveToNext()){
                    //Se obtinen los datos de la columna por el numero de columna obtenido
                    ee = c.getString(materia);
                    carrera = c.getString(carrera1);
                    salon = c.getString(salon1);
                    edificio = c.getString(edificio1);
                    //Se muestran en patalla los datos
                    txm.append("\n" + "EE: " + ee + "\n" + "CARRERA: " + carrera + "\n" + "SALON: "+ salon + "\n" + "EDIFICIO: " + edificio + "\n");
                }

            }catch (SQLiteException e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

        private void consultaHorarioSalonProfe(){
        //Metodo para hacer una consulta por media de un horario y que me arroje datos del academico, salon y edificio
            //Variables que ocuparemos para guardar la informacion de la tuplas
            String nombre,apellidop,apellidom,salon,edificio;
            try{
                //En un string guardamos la consulta
                String sql = "select ACADEMICO,APELLIDOPATERNO,APELLIDOMATERNO, NUMSALON, EDIFICIO from Academico AS a INNER JOIN AcademicoSalon AS ac ON a.NUMPERSONAL=ac.IDPERSONAL INNER JOIN Salon AS s ON ac.IDSALON=s.NUMSALON INNER JOIN Horario AS h ON s.NUMSALON=h.IDSALON WHERE h.VIERNES = '13:00-15:00';";
                //Aqui se colocara la instruccion que debe realizar la base de datos.
                Cursor c = db.rawQuery(sql,null);
                //Se guardan en variables de tipo entero, el numero de la columna
                int nombre1 = c.getColumnIndex("ACADEMICO");
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
                    txm.append("\n" + "NOMBRE " + nombre + "\n" + "APELLIDOP: " + apellidop + "\n" + "APELLIDOM: " + apellidom + "\n" + "SALON: " + salon + "\n" + "EDIFICIO: "+ edificio + "\n");
                }
            }catch(SQLiteException e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
}
