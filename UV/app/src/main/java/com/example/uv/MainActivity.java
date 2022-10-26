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
                    "(73294,'TECO','FUNDAMENTOS DE MATEMATICAS',1,2,0001)," +
                    "(75653,'TECO','ORGANIZACION DE COMPUTADORAS',3,2,0002)," +
                    "(75774,'TECO','PROGRAMACION',3,1,0003)," +
                    "(75651,'TECO','ORGANIZACION DE COMPUTADORAS',3,1,0004)," +
                    "(73282,'TECO','TECNOLOGIAS DE LA INFORMACION PARA LA INNOVACION',1,1,0005)," +
                    "(73285,'TECO','INTRODUCCION A LA PROGRAMACION',1,1,0006)," +
                    "(76830,'TECO','HABILIDADES DEL PENSAMIENTO',1,1,0007)," +
                    "(73288,'TECO','INGLES 1',1,1,0008)," +
                    "(75655,'TECO','BASE DE DATOS',3,1,0009)," +
                    "(97582,'TECO','INGLES 2',3,2,0010)," +
                    "(80630,'TECO','ADMINISTRACION DE SERVIDORES',5,1,0011)," +
                    "(80634,'TECO','HABILIDADES DIRECTIVAS',5,1,0012)," +
                    "(73282,'TECO','TECNOLOGIAS DE LA INFORMACION PARA LA INNOVACION',1,1,0013)," +
                    "(73282,'TECO','TECNOLOGIAS DE LA INFORMACION PARA LA INNOVACION',1,1,0014)," +
                    "(73282,'TECO','TECNOLOGIAS DE LA INFORMACION PARA LA INNOVACION',1,1,0015)," +
                    "(73282,'TECO','TECNOLOGIAS DE LA INFORMACION PARA LA INNOVACION',1,1,0016)," +
                    "(73282,'TECO','TECNOLOGIAS DE LA INFORMACION PARA LA INNOVACION',1,1,0017)," +
                    "(73282,'TECO','TECNOLOGIAS DE LA INFORMACION PARA LA INNOVACION',1,1,0018)," +
                    "(73282,'TECO','TECNOLOGIAS DE LA INFORMACION PARA LA INNOVACION',1,1,0019)," +
                    "(73273,'TECO','HABILIDADES DEL PENSAMIENTO',1,2,0006)"+";");



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
                    "(0002,'JUAN MANUEL','GUTIERREZ','MENDEZ')," +
                    "(0003,'OSCAR','ALONSO','RAMIREZ')," +
                    "(0004,'EDSEL','ORTIZ','ROMERO'),"+
                    "(0005,'MARIA DEL CARMEN','MEZURA','GODOY')," +
                    "(0006,'PATRICIA','GONZALEZ','GASPAR')," +
                    "(0007,'CARLOS','ILLESCAS','SANCHEZ')," +
                    "(0008,'ARMINDA','BARRADAS','SANCHEZ')," +
                    "(0009,'EDGAR IVAN','BENITEZ','GUERRERO')," +
                    "(0010,'MARTHA PATRICIA','RODRIGUEZ','GUZMAN')," +
                    "(0011,'JESUS ROBERTO','MENDEZ','ORTIZ')," +
                    "(0012,'AQUILES','ORDUÑA','GONZALEZ')," +
                    "(0013,'RUTH','RODRIGUEZ','RAMIREZ')," +
                    "(0014,'LORENA','ALONSO','RAMIREZ')," +
                    "(0015,'MARIBEL','CARMONA','GARCIA')," +
                    "(0016,'MARIA DE LOURDES','HERNANDEZ','RODRIGUEZ')," +
                    "(0017,'MARIA DE LOS ANGELES','NAVARRO','GUERRERO')," +
                    "(0018,'VICTOR MANUEL','HERNANDEZ','OLIVERA')," +
                    "(0019,'FREDY','CASTAÑEDA','SANCHEZ')," +
                    "(0020,'PATRICIA','DIAZ','GASPAR')," +
                    "(0021,'CRISTHIAN','PEREZ','SALAZAR')," +
                    "(0022,'ULISES','MARINERO','AGUILAR')," +
                    "(0023,'OSCAR','ALONSO','RAMIREZ')," +
                    "(0024,'JOSE RAFAEL','ROJANO','CACERES')," +
                    "(0025,'MARIA DOLORES','VARGAS','CARDAN')," +
                    "(0026,'ERIKA','MENESES','RICO')," +
                    "(0027,'JOSE GUILLERMO','HERNANDEZ','CALDERON'),n" +
                    "(0028,'MARIA LUISA','CORDOBA','TLAXCALTECO')," +
                    "(0029,'ALFONSO','SANCHEZ','OREA')," +
                    "(0030,'LUIS GERARDO','MONTANE','JIMENEZ')," +
                    "(0031,'DIANA ELIZABETH','VALDERRABANO','PEDRAZA')," +
                    "(0032,'MARGARITA EDITH','CANAL','MARTINEZ')," +
                    "(0033,'ALICIA YAZMIN','ROJAS','LUNA')," +
                    "(0034,'RAMON DAVID','SARMIENTO','CERVANTES')," +
                    "(0035,'ITZEL ALESSANDRA','REYES','FLORES')," +
                    "(0036,'MAX WILLIAM','MILLAN','MARTINEZ')," +
                    "(0037,'NIELS','MARTINEZ','GUEVARA')," +
                    "(0038,'RAMON','GOMEZ','ROMERO')," +
                    //MAESTROS REDES
                    "(0039,'JAVIER','SANCHEZ','ACOSTA')," +
                    "(0040,'MARIA','DE','LOS','ANGELES','ARENAS','VALDES')," +
                    "(0041,'ROSELIA','OSORIO','ARMENTA')," +
                    "(0042,'ALFONSO','DURAN','HERMANEZ')," +
                    "(0043,'MARTHA','ELIZABETH','DOMINGUEZ',''BARCENAS)," +
                    "(0044,'DIANA','ELIZABETH','DOMINGUEZ','BARCENAS')," +
                    "(0045,'DIANA','ELIZABETH','VALDERRABANO','PEDRAZA')," +
                    "(0046,'VIRGINIA','LAGUNES','BARRADAS')," +
                    "(0047,'ARMINDA','SANCHEZ','BARRADAS')," +
                    "(0048,'LORENA','ALONSO','RAMIREZ')," +
                    "(0049,'AQUILES','ORDINA','GONZALEZ')," +
                    "(0050,'PATRICIA','GONZALEZ','GASPAR')" +
                    "(0051,'VICTOR','MANUEL','TAPLA','CARRERA')," +
                    "(0052,'JORGE','OCTAVIO','OCHARAN','HERNNADEZ')," +
                    "(0053,'MARIA','DOLORES','VARGAS','CERDAN')," +
                    "(0054,'GERARDO','CONTRERAS','VEGA')," +
                    "(0055,'JUAN','LUIS','LOPEZ','HERRERA')," +
                    "(0056,'LUIS','JACOBO','PEREZ','GUERRERO')," +
                    "(0057,'URBANO','FRANCISCO','ORTEGA','MENDEZ')," +
                    "(0058,'MARIA','SILVANA','GARCIA','RAMIREZ')," +
                    "(0059,'HECTOR','XAVIER','RIAÑO')," +
                    "(0060,'ERIKA','MENESES','RICO')," +
                    "(0061,'WILLIAN','ZARATE','RAMIREZ')," +
                    "(0062,'EZZIO','OTHONIEL','ACOSTA','CANSECO')," +
                    "(0063,'OSCAR','ALONSO','RAMIREZ')," +
                    "(0064,'ANGELICA','PEREZ','HERNANDES')," +
                    "(0065,'LUIS','JACOBO','PEREZ','GUERRERO')," +
                    "(0066,'HECTOR','XAVIER','LIMON','RIAÑO')," +
                    "(0067,'ALBERTO','CRUZ','LANDA')" +
                    "(0068,'JUAN','MANUEL','GUTIERREZ',''MENDEZ')," +
                    "(0069,'CARLOS','ALBERTO','OCHOA','RIVERA')," +
                    "(0070,'MIGUEL','ANGEL','ORTIGOZA','CLEMENTE')," +
                    //TECNOLOGIAS
                    "(0071,'JULIÁN','FELIPE','DÍAZ','CAMACHO')," +
                    "(0072,'GUSTAVO','PADRON','ARRIAGA')," +
                    "(0073,'IRMA','ELIZABETH','ROMERO','ARRIOJA')," +
                    "(0074,'JORGE','OCTAVIO','OCHARAN','HERNANDEZ')," +
                    "(0075,'KARLA','PAOLA','MARTINEZ','RAMILA')," +
                    "(0076,'ANGEL','JUAN','SANCHEZ','GARCIA')," +
                    "(0077,'JUAN','CARLOS','PEREZ','ARRIAGA')," +
                    "(0078,'CARLOS','GARCÍA','TRUJILLO')," +
                    "(0079,'SAUL','DOMINGUEZ','ISIDRO')," +
                    "(0080,'JOSE','EFRAIN','MONTERO','MORA')," +
                    "(0081,'DONAJI','CALLEJAS','DEL','CALLEJO')," +
                    "(0082,'VIRGINIA','ANGELICA','GARCIA','VEGA')," +
                    "(0083,'URBANO','FRANCISCO','ORTEGA','RIVERA')," +
                    //ESTADISTICA
                    "(0084,'JOSÉ','FABIÁN','MUÑOZ','PORTILLA')," +
                    "(0085,'ANA','YAZMÍN','VIVEROS','GARCÍA')," +
                    "(0086,'LORENA','LÓPE','LOZADA')," +
                    "(0087,'ANABELL','YENELLY','RAMÍRE','JIMÉNEZ')," +
                    "(0088,'ELISEO','GABRIEL','ARGÜELLES')," +
                    "(0089,'CLAUDIO','RAFAEL','CASTRO','LÓPEZ')," +
                    "(0090,'MIGUEL','ALONSO','LÓPEZ')," +
                    "(0091,'CECILIA','CRUZ','LÓPEZ')," +
                    "(0092,'PATRICIA','GARCÍA','SÁNCHEZ')," +
                    "(0093,'JESÚS','HERNÁNDEZ','SUÁREZ')," +
                    "(0094,'JOSÉ','JUAN','MUÑOZ','LEÓN')," +
                    "(0095,'MARÍA','DE','LOURDES','VELASCO','VÁZQUEZ')," +
                    "(0096,'JULIÁN','FELIPE','DÍAZ','CAMACHO')," +
                    "(0097,'YESENIA','ZAVALETA','SÁNCHEZ')," +
                    "(0098,'NORA','GUADALUPE','SÁNCHEZ','MONTERO')," +
                    "(0099,'MARIO','ALBERTO','HERNANDEZ','PEREZ')," +
                    "(0100,'JUDITH','GUADALUPE','MONTERO','MORA')," +
                    "(0101,'GUSTAVO','ADOLFO','REYES','AGUILAR')," +
                    "(0102,'ZOYLO','MORALES','ROMERO')," +
                    "(0103,'MIGUEL','ALONSO','LÓPEZ')," +
                    "(0104,'JUDITH','RODRÍGUEZ','CUEVAS')," +
                    "(0105,'EDUARDO','DÍAZ','CAMACHO')," +
                    "(0106,'JOSE','LUIS','COLORADO','HERNÁNDEZ')," +
                    "(0107,'JOSÉ','FABIÁN','MUÑOZ','PORTILLA')," +
                    "(0108,'MINERVA','REYES','FÉLIX')," +
                    "(0109,'ROBERTO','LARA','DOMÍNGUEZ')," +
                    "(0110,'ANABELL','YENELLY','RAMÍREZ','JIMÉNEZ')," +
                    "(0111,'ABIEL','GERARDO','SANTOS','HERNÁNDEZ')," +
                    "(0112,'JUAN','CARLOS','GARCIA','RODRÍGUEZ')," +
                    "(0113,'ÁNGEL','FERNANDO','ARGÜELLO','ORTIZ')," +
                    "(0114,'RAÚL','DE','LA','FUENTE','IZAGUIRRE')," +
                    "(0115,'ÁNGEL','MIÑON','PÉREZ')," +
                    "(0116,'ZOYLO','MORALES','ROMERO')," +
                    "(0117,'NORA','GUADALUPE','SÁNCHEZ','MONTERO')," +
                    "(0118,'EDNA','LILLIAM','MENDOZA','SOLIS')," +
                    "(0119,'JESÚS','HERNÁNDEZ','SUÁREZ')," +
                    "(0120,'AURELIANO','AGUILAR','BONILLA')," +
                    "(0121,'JUAN','RUIZ','RAMÍREZ')," +
                    "(0122,'JULIA','AURORA','MONTANO','RIVAS')," +
                    "(0123,'ALMA','ROSA','CORDOVA','AGUILAR')," +
                    "(0124,'RUTH','ELIZABETH','ALDAMA','ROSAS')," +
                    "(0125,'VÍCTOR','MANUEL','MÉNDEZ','SÁNCHEZ')," +
                    "(0126,'JESUS','ADOLFO','MEJIA','DE','DIOS')," +
                    "(0127,'JOSÉ','ISAÍAS','SILICEO','MURRIETA'),"+";");

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
