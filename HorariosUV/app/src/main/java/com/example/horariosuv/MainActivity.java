package com.example.horariosuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ProgressBar cargar;
    SQLiteDatabase database;
    SQLiteDatabase admins;
    String ubicacion = "data/data/com.example.horariosuv/AdminUV";
    String datosUV = "data/data/com.example.horariosuv/DatabaseUV";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_carga);

        try {
            if(!(checkDatabase2(datosUV))){
                createDatabaseAdmins(ubicacion);
                createTableAdmins();
                insertarDatosAdmins();

                createDatabaseUV(datosUV);
                dropTable();
                createTablas();
                insertarDatosMaterias();
                insertarDatosSalon();
                insertarDatosAcademico();
                insertarDatosHorario();
                insertDatosMateriaSalon();
                insertdatosAcademicoSalon();

                Toast.makeText(this, "Cargando...", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Cargando...", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this, "Cargando...", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

        }

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Pantalla_Carga.class);
                startActivity(intent);
                finish();
            }
        };


        Timer tiempo = new Timer();
        tiempo.schedule(tarea,3000);

    }

    private boolean checkDataBase(String Database_path) {
        SQLiteDatabase check = null;
        try {
            check = SQLiteDatabase.openDatabase(Database_path, null, SQLiteDatabase.OPEN_READONLY);
            check.close();
        } catch (SQLiteException e) {
            //Log.e("Error", "No existe la base de datos " ,null);
        }
        return check != null;
    }

    private boolean checkDatabase2(String Database_path){
        SQLiteDatabase check = null;
        try {
            check = SQLiteDatabase.openDatabase(Database_path, null, SQLiteDatabase.OPEN_READONLY);
            check.close();
        }catch (SQLiteException e){

        }
        return check != null;
    }


    private void createDatabaseUV(String database_path){
        try {
            database = SQLiteDatabase.openDatabase(database_path,null,SQLiteDatabase.CREATE_IF_NECESSARY);
            Toast.makeText(this, "Cargando...", Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e){
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    private void createDatabaseAdmins(String database_path){
        try {
            admins = SQLiteDatabase.openDatabase(database_path,null,SQLiteDatabase.CREATE_IF_NECESSARY);
            Toast.makeText(this, "Cargando...", Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void createTableAdmins(){
        admins.beginTransaction();
        try {
            admins.execSQL("create table Admins(USUARIO text not null, PASSSWORD integer not null, primary key(USUARIO));");
            Toast.makeText(this, "Cargando...", Toast.LENGTH_SHORT).show();
            admins.setTransactionSuccessful();
        }catch (SQLiteException e){

        }finally {
            admins.endTransaction();
        }
    }


    private void dropTable(){
        //Eliminacion de tablas
        //Este metodo no se utilizara por el momento
        database.beginTransaction();
        try {
            database.execSQL(" drop table MateriaSalon;");
            database.execSQL(" drop table AcademicoSalon;");
            database.execSQL(" drop table Materias;");
            database.execSQL(" drop table Salon;");
            database.execSQL(" drop table Academico;");
            database.execSQL(" drop table Horario;");

            database.setTransactionSuccessful();
            Toast.makeText(this,"",Toast.LENGTH_LONG).show();
        }catch(SQLiteException e){
            Toast.makeText(this,"",Toast.LENGTH_LONG).show();
        }finally {
            database.endTransaction();
        }
    }

    private void insertarDatosAdmins(){
        admins.beginTransaction();
        try {
            admins.execSQL("insert into Admins(USUARIO, PASSSWORD) values"+
                    "('S20018152',20018152),"+
                    "('S20018168',20018168),"+
                    "('S19016410',19016410),"+
                    "('S19030171',19030171),"+
                    "('S20018150',20018150)"+";");
            Toast.makeText(this,"Cargando Datos...",Toast.LENGTH_LONG).show();
            admins.setTransactionSuccessful();
        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            admins.endTransaction();
        }
    }


    private void insertarDatosAcademico(){
        database.beginTransaction();
        try{
            database.execSQL("insert into Academico(NUMPERSONAL, NOMBRE, APELLIDOPATERNO, APELLIDOMATERNO) values" +
                    "(0001,'MARIBEL','CARMONA','GARCIA')," +
                    "(0002,'MARIA DE LOS ANGELES','ARENAS','VALDES'),"+
                    "(0003,'NIZA','RENDON','FLORES'),"+
                    "(0004,'CRISTINA','TRIANA','CORTINA'),"+
                    "(0005,'DOLORES','CARRILLO','COTO'),"+
                    "(0006,'OLGA REGINA','ROSAS','TOLENTINO'),"+
                    "(0007,'JOSE JUAN','MUÑOZ','LEON'),"+
                    "(0008,'VERONICA ELIZABETH','OROZCO','RIOS'),"+
                    "(0009,'RODOLFO','BARUCH','MALDONADO'),"+
                    "(0010,'EDNA LILLIAM','MENDOZA','SOLIS'),"+
                    "(0011,'PABLO ISRAEL','GUZMAN','MARTINEZ'),"+
                    "(0012,'EZZIO OTHONIEL','ACOSTA','CANSECO'),"+
                    "(0013,'MITL','MELGAREJO','GONZALES'),"+
                    "(0014,'MAX WILLIAM','MILLAN','MARTINEZ'),"+
                    "(0015,'SAUL','DOMINGUEZ','ISIDRO'),"+
                    "(0016,'AURELIANO','AGUILAR','BONILLA'),"+
                    "(0017,'MIGUEL ANGEL','ORTIGOZA','CLEMENTE'),"+
                    "(0018,'ITZEL ALESSANDRA','REYES','FLORES'),"+
                    "(0019,'OSCAR','ALONSO','RAMIREZ'),"+
                    "(0020,'MARIA DOLORES','VARGAS','CERDAN'),"+
                    "(0021,'ANGEL JUAN','SANCHEZ','GARCIA'),"+
                    "(0022,'MARIA DE LOURDES','WATTY','URQUIDI'),"+
                    "(0023,'JUAN LUIS','LOPEZ','HERRERA'),"+
                    "(0024,'ADRIANA','CERVANTES','CASTILLO'),"+
                    "(0025,'ELIZABETH','MURRIETA','SANGABRIEL'),"+
                    "(0026,'JAVIER','SANCHEZ','ACOSTA'),"+
                    "(0027,'MARIA DE LOURDES','HERNANDEZ','RODRIGUEZ'),"+
                    "(0028,'PAOLA FABIOLA','CUELLAR','GUTIERREZ'),"+
                    "(0029,'ANA LUZ','POLO','ESTRELLA'),"+
                    "(0030,'JUAN CARLOS','PEREZ','ARRIAGA'),"+
                    "(0031,'GUSTAVO','PADRON','RIVERA'),"+
                    "(0032,'IRMA ELIZABETH','ROMERO','ARRIOJA'),"+
                    "(0033,'RAMON','GOMEZ','ROMERO'),"+
                    "(0034,'JORGE OCTAVIO','OCHARAN','HERNANDEZ'),"+
                    "(0035,'MARIA','ANGELICA','CERDAN'),"+
                    "(0036,'MARIO ALBERTO','HERNANDEZ','PEREZ'),"+
                    "(0037,'CARLOS','GARCIA','TRUJILLO'),"+
                    "(0038,'SAUL','DOMINGUEZ','ISIDRO'),"+
                    "(0039,'JOSE EFRAIN','MONTERO','MORA'),"+
                    "(0040,'URBANO FRANCISCO','ORTEGA','RIEVRA'),"+
                    "(0041,'JUAN MANUEL','GUTIERREZ','MENDEZ'),"+
                    "(0042,'KARLA PAOLA','MARTINEZ','RAMILA'),"+
                    "(0043,'DONAJI','CALLEJAS DEL','CALLEJO'),"+
                    "(0044,'MARIA DEL CARMEN','MEZURA','GODOY'),"+
                    "(0045,'RUTH','RODRIGUEZ','RAMIREZ'),"+
                    "(0046,'PATRICIA','GONZALES','GASPAR'),"+
                    "(0047,'CARLOS','ILLESCAS','SANCHEZ'),"+
                    "(0048,'ARMINDA','BARRADAS','SANCHEZ'),"+
                    "(0049,'EVERARDO FRANCISCO','GARCIA', 'MENIER'),"+
                    "(0050,'LORENA','ALONSO','RAMIREZ'),"+
                    "(0051,'URANIA FABIOLA','CRUZ','MARQUEZ'),"+
                    "(0052,'LESTER ADOLFO','REYES','LOPEZ'),"+
                    "(0053,'ERIKA','MENESES','RICO'),"+
                    "(0054,'PATRICIA','DIAZ','GASPAR'),"+
                    "(0055,'CHRISTIAN','PEREZ','SALAZAR'),"+
                    "(0056,'EDGARD IVAN','BENITEZ','GUERRERO'),"+
                    "(0057,'YOSELYN NOHEMI','ORTEGA','GIJON'),"+
                    "(0058,'ULISES','MARINERO','AGUILAR'),"+
                    "(0059,'EDSEL','ORTIZ','MORENO'),"+
                    "(0060,'ALICIA YAZMIN','ROJAS','LUNA'),"+
                    "(0061,'DIANA ELIZABETH','VALDERRABANO','PEDRAZA'),"+
                    "(0062,'JOSE GUILLERMO','HERNANDEZ','CALDERON'),"+
                    "(0063,'MARIA LUISA','CORDOBA','TLAXCALTECO'),"+
                    "(0064,'ALFONSO','SANCHEZ','OREA'),"+
                    "(0065,'MARTHA PATRICIA','RODRIGUEZ','GUZMAN'),"+
                    "(0066,'JESUS ROBERTO','MENDEZ','ORTIZ'),"+
                    "(0067,'YANETH','REYES','ESTUDILLO'),"+
                    "(0068,'AQUILES','ORDUÑA','GONZALES'),"+
                    "(0069,'FREDY','CASTAÑEDA','SANCHEZ'),"+
                    "(0070,'JOSE RAFAEL','ROJANO','CACERES'),"+
                    "(0071,'MARIA DE LOS ANGELES','NAVARRO','GUERRERO'),"+
                    "(0072,'VICTOR MANUEL','HERNANDEZ','OLIVERA'),"+
                    "(0073,'LUIS GERARDO','MONTANE','JIMENEZ'),"+
                    "(0074,'MARGARITA EDITH','CANAL','MARTINEZ'),"+
                    "(0075,'NIELS','MARTINEZ','GUEVARA'),"+
                    "(0076,'VICTOR MANUEL','TLAPA','CARRERA'),"+
                    "(0077,'ROSELIA','OSORIO','ARMENTA'),"+
                    "(0078,'ALFONSO','DURAN','HERNANDEZ'),"+
                    "(0079,'MARTHA ELIZABET','DOMINGUEZ','BARCENAS'),"+
                    "(0080,'VIRGINIA','LAGUNES','BARRADAS'),"+
                    "(0081,'WILLIAN','ZARATE','NAVARRO'),"+
                    "(0082,'ANGELICA','PEREZ','HERNANDEZ'),"+
                    "(0083,'MARIA SILVA','GARCIA','RAMIREZ'),"+
                    "(0084,'CARLOS ALBERTO','OCHOA','RIVERA'),"+
                    "(0085,'GERARDO','CONTRERAS','VEGA'),"+
                    "(0086,'LUIS JACOBO','PEREZ','GUERRERO'),"+
                    "(0087,'HECTOR XAVIER','LIMON','RIAÑO'),"+
                    "(0088,'ALBERTO JAIR','CRUZ','LANDA'),"+
                    "(0089,'URBANO FRANCISCO','ORTEGA','RIVERA'),"+
                    "(0090,'MINERVA','REYES','FELIX'),"+
                    "(0091,'JOSE FABIAN','MUÑOZ','PORTILLA'),"+
                    "(0092,'ANABELL YENELLY','RAMIRZ','JIMENEZ'),"+
                    "(0093,'GUSTAVO ADOLFO','REYES','JIMENEZ'),"+
                    "(0094,'ROBERTO','LARA','DOMINGUEZ'),"+
                    "(0095,'MIGUEL','ALONSO','LOPEZ'),"+
                    "(0096,'EDUARDO','DIAZ','CAMACHO'),"+
                    "(0097,'ABIEL GERARDO','SANTOS','HERNANDEZ'),"+
                    "(0098,'JUAN CARLOS','GARCIA','RODRIGUEZ'),"+
                    "(0099,'ANGEL','MIÑON','PEREZ'),"+
                    "(0100,'ELISEO','GABRIEL','ARGUELLES'),"+
                    "(0101,'JESUS','HERNANDEZ','SUAREZ'),"+
                    "(0102,'ANGEL FERNANDO','ARGUELLO','ORTIZ'),"+
                    "(0103,'RAUL','DE LA FUENTE','IZAGUIRRE'),"+
                    "(0104,'ANA YAZMIN','VIVIEROS','GARCIA'),"+
                    "(0105,'JUDITH','RODRIGUEZ','CUEVAS'),"+
                    "(0106,'CECILIA','CRUZ','LOPEZ'),"+
                    "(0107,'PATRICIA','GARCIA','SANCHEZ'),"+
                    "(0108,'MARIA DE LOURDES',' VELASCO', 'VAZQUEZ'),"+
                    "(0109,'ZOYLO','MORALES','ROMERO'),"+
                    "(0110,'NORA GUADALUPE','SANCHEZ','MONTERO'),"+
                    "(0111,'RUTH ELIZABETH','ALDAMAS','ROSAS'),"+
                    "(0112,'JOSE LUIS','COLORADO','HERNANDEZ'),"+
                    "(0113,'MA. YESENIA','ZAVALETA','SANCHEZ'),"+
                    "(0114,'CLAUDIO RAFAEL','CASTRO','LOPEZ'),"+
                    "(0115,'JUAN','RUIZ','RAMIREZ'),"+
                    "(0116,'JUDITH GUADALUPE','MONTERO','RIVAS'),"+
                    "(0117,'JULIA AURORA','MONTANO','RIVAS'),"+
                    "(0118,'ALMA ROSA','CORDOVA','AGUILAR'),"+
                    "(0119,'LORENA','LOPEZ','LOZADA'),"+
                    "(0120,'JULIAN FELIPE','DIAZ','CAMACHO'),"+
                    "(0121,'VICTOR MANUEL','MENDEZ','SANCHEZ'),"+
                    "(0122,'JESUS ADOLFO','MEJIA','DE DIOS'),"+
                    "(0123,'JOSE ISAIAS','SILICEO','MURRIETA');");


            database.setTransactionSuccessful();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            database.endTransaction();
        }

    }


    private void createTablas(){
        database.beginTransaction();
        try{
            //Tabla Materias que se compone de NRC llave primaria, Carrera, Experiencia Educativa, Seccion, Bloque,Id_Personal del academico que la va a impartir
            database.execSQL("create table Materias(NRC integer PRIMARY KEY NOT NULL, CARRERA text NOT NULL, EE text NOT NULL, BLOQUE integer NOT NULL,SECCION integer NOT NULL,IDPERSONAL integer, FOREIGN KEY(IDPERSONAL) REFERENCES Academico(NUMPERSONAL));");
            //Tabla Salon que se compone de Numero de Salon llave primaria y Edificio
            database.execSQL("create table Salon(NUMSALON text PRIMARY KEY NOT NULL, EDIFICIO text NOT NULL);");
            //Tabla Academico que se compone de Numero Personal, Nombre, Appelido Paterno, Apellido Materno
            database.execSQL("create table Academico(NUMPERSONAL integer PRIMARY KEY AUTOINCREMENT NOT NULL, NOMBRE text NOT NULL, APELLIDOPATERNO text NOT NULL, APELLIDOMATERNO text NOT NULL);");
            //Tabla Horario, esta se crea por la relacion de la tabla Materias, con la tabla Salon, asi mismo a esta tabla se le agregara una llave compuesta como primaria que contendran las llaves primarias de la tabla Materia y Salon,ademas de sus respectivos atributos, Lunes, Martes, Miercoles, Jueves, Viernes.
            database.execSQL("create table Horario(IDNRC integer NOT NULL, IDSALON text NOT NULL,LUNES text,MARTES text, MIERCOLES text, JUEVES text, VIERNES text, PRIMARY KEY(IDNRC,IDSALON), FOREIGN KEY(IDNRC) REFERENCES Materias(NRC), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON));");
            //Tabla AcademicoSalon esta se crea por la relacion de la tabla de Salon y Academico, asi mismo a esta tabla se le agregara una llave compuesta como primaria que contendran las llaves primarias de Salon y Academico
            database.execSQL("create table AcademicoSalon(IDPERSONAL integer NOT NULL, IDSALON text NOT NULL, PRIMARY KEY(IDPERSONAL,IDSALON), FOREIGN KEY(IDPERSONAL) REFERENCES Academico(NUMPERSONAL), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON))");
            //Tabla MateriaSalon esta se crea por la relacion de la tabla de Materia y Salon, asi mismo a esta tabla se le agregara una llave compuesta como primaria que contendran las llaves primarias de Materia con Salon
            database.execSQL("create table MateriaSalon(IDNRC integer NOT NULL, IDSALON text NOT NULL, PRIMARY KEY(IDNRC,IDSALON), FOREIGN KEY(IDNRC) REFERENCES Materias(NRC), FOREIGN KEY(IDSALON) REFERENCES Salon(NUMSALON))");
            //Toast.makeText(this,"Creado",Toast.LENGTH_LONG).show();

            database.setTransactionSuccessful();
            Toast.makeText(this, "Cargando Datos...",Toast.LENGTH_SHORT).show();
        }catch(SQLException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            database.endTransaction();
        }
    }

    public void insertdatosAcademicoSalon(){
        database.beginTransaction();
        try {
            //ESTADÍSTICA
            database.execSQL("insert into AcademicoSalon(IDPERSONAL,IDSALON) values" +
                    "(0001,'104'),"+
                    "(0001,'F403'),"+
                    "(0001,'112'),"+
                    "(0001,'105'),"+
                    "(0001,'5'),"+
                    "(0002,'104'),"+
                    "(0002,'CC2'),"+
                    "(0002,'F403'),"+
                    "(0002,'102'),"+
                    "(0003,'104'),"+
                    "(0004,'104'),"+
                    "(0004,'112'),"+
                    "(0005,'104'),"+
                    "(0006,'CC3'),"+
                    "(0007,'6'),"+
                    "(0007,'4'),"+
                    "(0008,'104'),"+
                    "(0008,'CC2'),"+
                    "(0009,'104'),"+
                    "(0010,'104'),"+
                    "(0010,'5'),"+
                    "(0010,'112'),"+
                    "(0010,'4'),"+
                    "(0011,'CC3'),"+
                    "(0012,'106'),"+
                    "(0013,'104'),"+
                    "(0013,'102'),"+
                    "(0014,'F403'),"+
                    "(0014,'CC3'),"+
                    "(0014,'104'),"+
                    "(0014,'CC1'),"+
                    "(0014,'103'),"+
                    "(0014,'102'),"+
                    "(0014,'FTC'),"+
                    "(0014,'F102'),"+
                    "(0015,'CC2'),"+
                    "(0015,'103'),"+
                    "(0015,'F403'),"+
                    "(0016,'112'),"+
                    "(0016,'F403'),"+
                    "(0016,'111'),"+
                    "(0016,'106'),"+
                    "(0017,'CC2'),"+
                    "(0017,'F403'),"+
                    "(0017,'LABRED'),"+
                    "(0018,'CC3'),"+
                    "(0018,'F103'),"+
                    "(0018,'F403'),"+
                    "(0018,'113'),"+
                    "(0018,'105'),"+
                    "(0018,'F102'),"+
                    "(0019,'104'),"+
                    "(0019,'111'),"+
                    "(0019,'F402'),"+
                    "(0019,'106'),"+
                    "(0019,'102'),"+
                    "(0019,'CC2'),"+
                    "(0019,'FTC'),"+
                    "(0019,'F101'),"+
                    "(0019,'F404'),"+
                    "(0020,'F403'),"+
                    "(0020,'CC1'),"+
                    "(0020,'106'),"+
                    "(0020,'F102'),"+
                    "(0020,'CC3'),"+
                    "(0020,'F404'),"+
                    "(0020,'105'),"+
                    "(0020,'F101'),"+
                    "(0021,'F403'),"+
                    "(0021,'CC2'),"+
                    "(0021,'CDS'),"+
                    "(0021,'113'),"+
                    "(0021,'105'),"+
                    "(0022,'F403'),"+
                    "(0023,'F403'),"+
                    "(0023,'CC3'),"+
                    "(0023,'CIDI'),"+
                    "(0024,'105'),"+
                    "(0024,'F404'),"+
                    "(0024,'CC1'),"+
                    "(0025,'104'),"+
                    "(0025,'CC3'),"+
                    "(0026,'CC1'),"+
                    "(0026,'CC2'),"+
                    "(0026,'F403'),"+
                    "(0026,'102'),"+
                    "(0027,'F103'),"+
                    "(0027,'CC2'),"+
                    "(0027,'CC3'),"+
                    "(0027,'CC1'),"+
                    "(0027,'CDS'),"+
                    "(0027,'105'),"+
                    "(0027,'106'),"+
                    "(0028,'F103'),"+
                    "(0029,'F103'),"+
                    "(0029,'CC1'),"+
                    "(0030,'F103'),"+
                    "(0030,'CC1'),"+
                    "(0030,'CC2'),"+
                    "(0030,'CC4'),"+
                    "(0030,'105'),"+
                    "(0031,'103'),"+
                    "(0031,'CC2'),"+
                    "(0031,'106'),"+
                    "(0032,'105'),"+
                    "(0032,'CC1'),"+
                    "(0032,'111'),"+
                    "(0033,'CC1'),"+
                    "(0033,'CC2'),"+
                    "(0033,'106'),"+
                    "(0033,'F102'),"+
                    "(0033,'FTC'),"+
                    "(0034,'F103'),"+
                    "(0034,'CC1'),"+
                    "(0034,'F402'),"+
                    "(0034,'CC4'),"+
                    "(0034,'111'),"+
                    "(0035,'CC3'),"+
                    "(0035,'F103'),"+
                    "(0036,'CC4'),"+
                    "(0036,'F103'),"+
                    "(0036,'F101'),"+
                    "(0037,'112'),"+
                    "(0037,'CC1'),"+
                    "(0038,'112'),"+
                    "(0038,'CC3'),"+
                    "(0038,'113'),"+
                    "(0038,'CC4'),"+
                    "(0039,'112'),"+
                    "(0039,'111'),"+
                    "(0040,'CIDI'),"+
                    "(0040,'CDS'),"+
                    "(0041,'CDS'),"+
                    "(0041,'CIDI'),"+
                    "(0041,'F101'),"+
                    "(0042,'105'),"+
                    "(0043,'111'),"+
                    "(0043,'105'),"+
                    "(0044,'F402'),"+
                    "(0044,'F404'),"+
                    "(0044,'CC2'),"+
                    "(0044,'103'),"+
                    "(0045,'111'),"+
                    "(0045,'104'),"+
                    "(0045,'106'),"+
                    "(0045,'112'),"+
                    "(0045,'5'),"+
                    "(0046,'CC3'),"+
                    "(0046,'103'),"+
                    "(0046,'CC1'),"+
                    "(0046,'104'),"+
                    "(0046,'CC2'),"+
                    "(0046,'105'),"+
                    "(0047,'103'),"+
                    "(0048,'103'),"+
                    "(0048,'102'),"+
                    "(0049,'103'),"+
                    "(0049,'102'),"+
                    "(0049,'106'),"+
                    "(0050,'104'),"+
                    "(0050,'103'),"+
                    "(0050,'CC2'),"+
                    "(0050,'CC3'),"+
                    "(0050,'105'),"+
                    "(0050,'102'),"+
                    "(0051,'103'),"+
                    "(0052,'103'),"+
                    "(0053,'111'),"+
                    "(0053,'112'),"+
                    "(0053,'CC2'),"+
                    "(0053,'F101'),"+
                    "(0053,'F404'),"+
                    "(0054,'113'),"+
                    "(0054,'106'),"+
                    "(0054,'112'),"+
                    "(0054,'6'),"+
                    "(0055,'106'),"+
                    "(0055,'113'),"+
                    "(0056,'F402'),"+
                    "(0056,'CC2'),"+
                    "(0056,'103'),"+
                    "(0057,'106'),"+
                    "(0058,'106'),"+
                    "(0058,'103'),"+
                    "(0058,'CC2'),"+
                    "(0059,'105'),"+
                    "(0059,'103'),"+
                    "(0060,'CC3'),"+
                    "(0060,'112'),"+
                    "(0060,'111'),"+
                    "(0060,'F102'),"+
                    "(0060,'F101'),"+
                    "(0060,'F402'),"+
                    "(0060,'FTC'),"+
                    "(0061,'112'),"+
                    "(0061,'111'),"+
                    "(0061,'102'),"+
                    "(0062,'113'),"+
                    "(0062,'CC3'),"+
                    "(0063,'112'),"+
                    "(0063,'FTC'),"+
                    "(0064,'CC1'),"+
                    "(0064,'5'),"+
                    "(0065,'F102'),"+
                    "(0065,'111'),"+
                    "(0066,'102'),"+
                    "(0066,'103'),"+
                    "(0066,'FTC'),"+
                    "(0067,'F102'),"+
                    "(0067,'111'),"+
                    "(0067,'113'),"+
                    "(0067,'106'),"+
                    "(0068,'103'),"+
                    "(0068,'112'),"+
                    "(0068,'106'),"+
                    "(0068,'104'),"+
                    "(0068,'105'),"+
                    "(0069,'CC3'),"+
                    "(0069,'106'),"+
                    "(0069,'111'),"+
                    "(0069,'105'),"+
                    "(0070,'CC4'),"+
                    "(0070,'FTC'),"+
                    "(0070,'111'),"+
                    "(0070,'F402'),"+
                    "(0070,'106'),"+
                    "(0070,'CIDI'),"+
                    "(0071,'106'),"+
                    "(0071,'F102'),"+
                    "(0071,'FTC'),"+
                    "(0071,'105'),"+
                    "(0072,'105'),"+
                    "(0072,'F102'),"+
                    "(0073,'CC1'),"+
                    "(0073,'112'),"+
                    "(0074,'CC1'),"+
                    "(0074,'F102'),"+
                    "(0075,'F103'),"+
                    "(0075,'F102'),"+
                    "(0075,'107'),"+
                    "(0075,'CC2'),"+
                    "(0075,'CC3'),"+
                    "(0075,'F402'),"+
                    "(0076,'102'),"+
                    "(0076,'106'),"+
                    "(0076,'105'),"+
                    "(0076,'F101'),"+
                    "(0076,'CC3'),"+
                    "(0076,'FTC'),"+
                    "(0076,'F404'),"+
                    "(0077,'102'),"+
                    "(0078,'102'),"+
                    "(0079,'102'),"+
                    "(0079,'LABRED'),"+
                    "(0079,'F404'),"+
                    "(0080,'CC2'),"+
                    "(0080,'102'),"+
                    "(0081,'FTC'),"+
                    "(0081,'F101'),"+
                    "(0082,'F404'),"+
                    "(0082,'LABRED'),"+
                    "(0082,'F101'),"+
                    "(0082,'F104'),"+
                    "(0083,'CC2'),"+
                    "(0083,'CC3'),"+
                    "(0084,'F404'),"+
                    "(0084,'LABRED'),"+
                    "(0085,'CIDI'),"+
                    "(0086,'CIDI'),"+
                    "(0086,'112'),"+
                    "(0086,'F101'),"+
                    "(0087,'F101'),"+
                    "(0087,'F404'),"+
                    "(0088,'LABRED'),"+
                    "(0088,'CIDI'),"+
                    "(0088,'F404'),"+
                    "(0088,'F101'),"+
                    "(0090,'6'),"+
                    "(0090,'5'),"+
                    "(0091,'6'),"+
                    "(0091,'106'),"+
                    "(0091,'107'),"+
                    "(0091,'112'),"+
                    "(0092,'CC1'),"+
                    "(0092,'CC2'),"+
                    "(0092,'107'),"+
                    "(0092,'CC3'),"+
                    "(0093,'105'),"+
                    "(0093,'4'),"+
                    "(0093,'6'),"+
                    "(0094,'6'),"+
                    "(0095,'6'),"+
                    "(0095,'F403'),"+
                    "(0095,'F101'),"+
                    "(0095,'111'),"+
                    "(0095,'105'),"+
                    "(0096,'6'),"+
                    "(0097,'6'),"+
                    "(0098,'6'),"+
                    "(0099,'6,'),"+
                    "(0099,'105'),"+
                    "(0099,'4'),"+
                    "(0099,'5'),"+
                    "(0100,'4'),"+
                    "(0101,'4'),"+
                    "(0101,'112'),"+
                    "(0101,'105'),"+
                    "(0101,'6'),"+
                    "(0101,'113'),"+
                    "(0102,'4'),"+
                    "(0102,'F402'),"+
                    "(0102,'103'),"+
                    "(0103,'CC3'),"+
                    "(0103,'107'),"+
                    "(0104,'4'),"+
                    "(0104,'113'),"+
                    "(0104,'111'),"+
                    "(0104,'F403'),"+
                    "(0104,'F404'),"+
                    "(0105,'4'),"+
                    "(0106,'106'),"+
                    "(0106,'F102'),"+
                    "(0106,'5'),"+
                    "(0106,'112'),"+
                    "(0106,'F402'),"+
                    "(0106,'4'),"+
                    "(0106,'107'),"+
                    "(0107,'103'),"+
                    "(0107,'F101'),"+
                    "(0107,'F404'),"+
                    "(0108,'111'),"+
                    "(0108,'4'),"+
                    "(0108,'113'),"+
                    "(0109,'4'),"+
                    "(0109,'112'),"+
                    "(0109,'5'),"+
                    "(0109,'103'),"+
                    "(0110,'CC1'),"+
                    "(0110,'F102'),"+
                    "(0110,'113'),"+
                    "(0110,'112'),"+
                    "(0111,'4'),"+
                    "(0111,'5'),"+
                    "(0112,'4'),"+
                    "(0112,'113'),"+
                    "(0113,'CC2'),"+
                    "(0113,'107'),"+
                    "(0114,'113'),"+
                    "(0114,'111'),"+
                    "(0114,'CC2'),"+
                    "(0114,'105'),"+
                    "(0115,'102'),"+
                    "(0115,'F102'),"+
                    "(0115,'113'),"+
                    "(0116,'107'),"+
                    "(0116,'111'),"+
                    "(0116,'5'),"+
                    "(0116,'112'),"+
                    "(0117,'106'),"+
                    "(0117,'111'),"+
                    "(0117,'F402'),"+
                    "(0117,'107'),"+
                    "(0117,'112'),"+
                    "(0117,'113'),"+
                    "(0117,'214'),"+
                    "(0118,'112'),"+
                    "(0118,'107'),"+
                    "(0118,'4'),"+
                    "(0118,'5'),"+
                    "(0119,'F404'),"+
                    "(0119,'102'),"+
                    "(0119,'103'),"+
                    "(0119,'113'),"+
                    "(0119,'F402'),"+
                    "(0120,'5'),"+
                    "(0120,'CC4'),"+
                    "(0120,'F101'),"+
                    "(0120,'CC1'),"+
                    "(0120,'F103'),"+
                    "(0121,'5'),"+
                    "(0122,'105'),"+
                    "(0122,'4'),"+
                    "(0122,'106'),"+
                    "(0123,'103'),"+
                    "(0123,'F402'),"+
                    "(0123,'104')"+";");

            Toast.makeText(this, "Cargando Datos...", Toast.LENGTH_SHORT).show();
            database.setTransactionSuccessful();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }finally {
            database.endTransaction();
        }
    }

    public void insertDatosMateriaSalon(){
        database.beginTransaction();
        try {
            //Datos INGENIERÍA de software
            //Bloque 1
            //Seccion 1
            database.execSQL("insert into MateriaSalon(IDNRC,IDSALON) values" +
                    "(73230,'104'),"+
                    "(73231,'104'),"+
                    "(73231,'CC2'),"+
                    "(73272,'104'),"+
                    "(96703,'104'),"+
                    "(73274,'104'),"+
                    "(88210,'CC3'),"+
            //BLOQUE 1
            //SECCION 2
                    "(73236,'104'),"+
                    "(73236,'106'),"+
                    "(73237,'104'),"+
                    "(73237,'CC2'),"+
                    "(73275,'104'),"+
                    "(96708,'112'),"+
                    "(96708,'104'),"+
                    "(73279,'104'),"+
                    "(88220,'CC3'),"+
            //BLOQUE 1
            //SECCION 3
                    "(80555,'106'),"+
                    "(76745,'102'),"+
                    "(76745,'104'),"+
            //BLOQUE 3
            //SECCION 1
                    "(75641,'F403'),"+
                    "(75641,'CC3'),"+
                    "(75637,'CC2'),"+
                    "(75637,'103'),"+
                    "(75637,'F403'),"+
                    "(75649,'112'),"+
                    "(75649,'F403'),"+
                    "(75633,'F403'),"+
                    "(75633,'CC2'),"+
                    "(75647,'CC2'),"+
                    "(75647,'F403'),"+
                    "(75778,'CC3'),"+
                    "(75778,'F103'),"+
                    "(75778,'F403'),"+
                    "(12665,'104'),"+
                    "(12665,'111'),"+
                    "(12665,'F402'),"+
            //BLOQUE 3
            //SECCION 2
                    "(75642,'F403'),"+
                    "(75642,'CC1'),"+
                    "(75640,'F403'),"+
                    "(75640,'CC2'),"+
                    "(75650,'F403'),"+
                    "(75635,'F403'),"+
                    "(75635,'CC3'),"+
                    "(75648,'F403'),"+
                    "(75648,'CC3'),"+
            //BLOQUE 3
            //SECCION 3
                    "(95590,'104'),"+
                    "(95590,'CC1'),"+
                    "(95590,'F403'),"+
                    "(95596,'105'),"+
                    "(95596,'F404'),"+
                    "(95596,'CC1'),"+
                    "(95598,'104'),"+
                    "(95598,'F403'),"+
                    "(95599,'104'),"+
                    "(95599,'CC3'),"+
                    "(95600,'CC1'),"+
                    "(95600,'CC2'),"+
                    "(95600,'F403'),"+
            //BLOQUE 5
            //SECCION 1
                    "(80598,'F103'),"+
                    "(80598,'CC2'),"+
                    "(80600,'F103'),"+
                    "(80602,'F103'),"+
                    "(80602,'CC1'),"+
                    "(80604,'F103'),"+
                    "(80604,'CC3'),"+
                    "(80606,'F103'),"+
                    "(80606,'CC1'),"+
                    "(80607,'103'),"+
                    "(80607,'CC2'),"+
                    "(80607,'106'),"+
                    "(80610,'105'),"+
                    "(80610,'CC1'),"+
                    "(80610,'111'),"+
                    "(80612,'CC1'),"+
                    "(80612,'CC2'),"+
                    "(80612,'106'),"+
            //SECCION 2

                    "(80613,'F103'),"+
                    "(80613,'CC1'),"+
                    "(80615,'F103'),"+
                    "(80616,'F103'),"+
                    "(80616,'CC1'),"+
                    "(80617,'F103'),"+
                    "(80617,'CC3'),"+
                    "(80622,'CC2'),"+
                    "(80622,'F103'),"+
            //BLOQUE 7
            //SECCION 1
                    "(83492,'F402'),"+
                    "(83492,'CC4'),"+
                    "(12342,'CDS'),"+
                    "(83497,'CC4'),"+
                    "(83501,'112'),"+
                    "(83501,'CC1'),"+
                    "(83503,'112'),"+
                    "(83503,'CC3'),"+
                    "(83503,'113'),"+
                    "(87276,'112'),"+
                    "(87276,'113'),"+
                    "(87276,'CC4'),"+
                    "(12355,'112'),"+
                    "(12355,'111'),"+
                    "(87275,'113'),"+
                    "(87275,'105'),"+
                    "(87272,'111'),"+
                    "(87272,'F103'),"+
                    "(91336,'F403'),"+
                    "(91336,'113'),"+
                    "(95601,'CIDI'),"+
                    "(95601,'CDS'),"+
                    "(95603,'113'),"+
                    "(95603,'105'),"+
                    "(12423,'CDS'),"+
            //SECCION 2
                    "(83504,'105'),"+
                    "(12345,'CDS'),"+
                    "(83506,'CC4'),"+
                    "(83506,'105'),"+
                    "(83506,'F103'),"+
                    "(83507,'111'),"+
                    "(83507,'105'),"+

            //TECNOLOGÍAS COMPUTACIÓNALES
            //BLOQUE 1
            //SECCION 1
                    "(73282,'103'),"+
                    "(73283,'111'),"+
                    "(73283,'104'),"+
                    "(73283,'106'),"+
                    "(73285,'CC3'),"+
                    "(73285,'103'),"+
                    "(76830,'103'),"+
                    "(73288,'103'),"+
            //SECCION 2
                    "(73292,'103'),"+
                    "(73292,'102'),"+
                    "(73294,'103'),"+
                    "(73294,'102'),"+
                    "(73294,'106'),"+
                    "(73295,'104'),"+
                    "(73295,'103'),"+
                    "(73295,'CC2'),"+
                    "(73297,'103'),"+
                    "(73298,'103'),"+
            //SECCION 3
                    "(81891,'111'),"+
                    "(81891,'112'),"+
                    "(81881,'113'),"+
                    "(81881,'106'),"+
                    "(81881,'112'),"+
                    "(81864,'106'),"+
                    "(81864,'113'),"+
                    "(81861,'CC2'),"+
            //BLOQUE 3
            //SECCION 1
                    "(75655,'F402'),"+
                    "(75655,'CC2'),"+
                    "(75655,'103'),"+
                    "(75667,'106'),"+
                    "(75660,'106'),"+
                    "(75651,'105'),"+
                    "(75651,'103'),"+
                    "(75662,'CC3'),"+
                    "(75662,'112'),"+
                    "(75662,'111'),"+
                    "(97579,'112'),"+
                    "(97579,'111'),"+
                    "(75776,'113'),"+
                    "(75776,'CC3'),"+
                    "(75774,'106'),"+
                    "(75774,'102'),"+
                    "(75774,'CC2'),"+
                    "(75775,'104'),"+
                    "(75775,'112'),"+
                    "(75775,'105'),"+
            //SECCION 2
                    "(75657,'112'),"+
                    "(75657,'FTC'),"+
                    "(75657,'CC1'),"+
                    "(75668,'111'),"+
                    "(75668,'CC2'),"+
                    "(75661,'F102'),"+
                    "(75661,'111'),"+
                    "(75653,'102'),"+
                    "(75664,'F102'),"+
                    "(75664,'111'),"+
                    "(75664,'113'),"+
                    "(97582,'103'),"+
                    "(97582,'112'),"+
            //BLOQUE 5
            //SECCION 1
                    "(80630,'103'),"+
                    "(80630,'FTC'),"+
                    "(80633,'106'),"+
                    "(80633,'111'),"+
                    "(80634,'103'),"+
                    "(80634,'106'),"+
                    "(80637,'CC2'),"+
                    "(80637,'F404'),"+
                    "(80637,'F402'),"+
                    "(80638,'CC3'),"+
                    "(80638,'106'),"+
                    "(80638,'111'),"+
                    "(80640,'CC4'),"+
                    "(80640,'FTC'),"+
            //SECCION 2
                    "(80675,'FTC'),"+
                    "(80676,'106'),"+
                    "(80676,'F102'),"+
                    "(80679,'105'),"+
                    "(80679,'106'),"+
                    "(80680,'111'),"+
                    "(80680,'F402'),"+
                    "(80685,'FTC'),"+
                    "(80685,'106'),"+
                    "(80685,'105'),"+
                    "(80688,'106'),"+
                    "(80688,'CIDI'),"+
                    "(80688,'F402'),"+
            //BLOQUE 7
            //SECCION 1
                    "(83571,'F102'),"+
                    "(83571,'F101'),"+
                    "(83572,'106'),"+
                    "(83572,'F102'),"+
                    "(83573,'105'),"+
                    "(83573,'F102'),"+
                    "(83574,'F102'),"+
                    "(87590,'CC1'),"+
                    "(87590,'112'),"+
                    "(87608,'105'),"+
            //SECCION 2
                    "(83575,'F102'),"+
                    "(83576,'F402'),"+
                    "(83576,'F102'),"+
                    "(83576,'FTC'),"+
                    "(83577,'CC1'),"+
                    "(83577,'F102'),"+
                    "(87591,'FTC'),"+
                    "(87591,'F102'),"+
                    "(85237,'F103'),"+
                    "(85237,'F102'),"+
                    "(19564,'F402'),"+
            //SECCION 3
                    "(98689,'FTC'),"+

            //REDES
            //BLOQUE 1
            //SECCION 1
                    "(73238,'102'),"+
                    "(73239,'102'),"+
                    "(73241,'102'),"+
                    "(73241,'CC2'),"+
                    "(73246,'102'),"+
                    "(76731,'102'),"+
            //SECCION 2
                    "(73248,'102'),"+
                    "(73267,'102'),"+
                    "(73269,'CC2'),"+
                    "(73269,'102'),"+
                    "(73270,'102'),"+
                    "(73271,'102'),"+
            //SECCION 3
                    "(13434,'LABRED'),"+
                    "(13434,'F404'),"+
                    "(13451,'106'),"+
                    "(13451,'105'),"+
                    "(13451,'F101'),"+
                    "(13460,'FTC'),"+
                    "(13460,'F101'),"+
            //BLOQUE 3
            //SECCION 1
                    "(75723,'CC3'),"+
                    "(75723,'105'),"+
                    "(75723,'102'),"+
                    "(13468,'CC2'),"+
                    "(13468,'F101'),"+
                    "(13468,'F404'),"+
                    "(75732,'104'),"+
                    "(75732,'105'),"+
                    "(75720,'CC1'),"+
                    "(75720,'105'),"+
                    "(75720,'104'),"+
                    "(75717,'F404'),"+
                    "(75717,'LABRED'),"+
                    "(76756,'CC2'),"+
                    "(76756,'CC1'),"+
                    "(95821,'105'),"+
                    "(95821,'CC3'),"+
                    "(95993,'F101'),"+
            //SECCION 2
                    "(75724,'CC3'),"+
                    "(75724,'F404'),"+
                    "(75724,'105'),"+
                    "(75731,'FTC'),"+
                    "(75731,'F101'),"+
                    "(75731,'F404'),"+
                    "(75733,'F404'),"+
                    "(75733,'102'),"+
                    "(75733,'103'),"+
                    "(75721,'CC3'),"+
                    "(75721,'FTC'),"+
                    "(75721,'F404'),"+
                    "(75718,'F404'),"+
                    "(75718,'LABRED'),"+
                    "(76733,'CC2'),"+
            //SECCION 3
                    "(95897,'CC2'),"+
                    "(95897,'CC3'),"+
            //BLOQUE 5
            //SECCION 1
                    "(80693,'CIDI'),"+
                    "(80695,'F101'),"+
                    "(80695,'F104'),"+
                    "(80697,'LABRED'),"+
                    "(80697,'F404'),"+
                    "(80700,'F101'),"+
                    "(70705,'F101'),"+
                    "(70705,'CC1'),"+
                    "(80707,'LABRED'),"+
            //SECCION 2
                    "(83555,'LABRED'),"+
                    "(83555,'CIDI'),"+
                    "(83557,'CIDI'),"+
                    "(83558,'LABRED'),"+
                    "(83558,'F404'),"+
                    "(83559,'F101'),"+
                    "(83559,'112'),"+
                    "(83561,'CIDI'),"+
                    "(83562,'CIDI'),"+
            //BLOQUE 7
            //SECCION 1
                    "(83565,'F404'),"+
                    "(83565,'F101'),"+
                    "(83566,'CIDI'),"+
                    "(83567,'F101'),"+
                    "(95922,'LABRED'),"+
                    "(19666,'F103'),"+
                    "(19666,'F101'),"+
            //SECCION 2
                    "(87500,'LABRED'),"+
                    "(87503,'F101'),"+
                    "(87503,'CIDI'),"+
                    "(87504,'F101'),"+
                    "(19469,'CIDI'),"+
            //SECCION 3
                    "(87507,'F101'),"+
                    "(87508,'F404'),"+
            //SECCION 4
                    "(91570,'F404'),"+

            //ESTADÍSTICA
            //BLOQUE 1
            //SECCION 1

                    "(85505,'6'),"+
                    "(85506,'6'),"+
                    "(85506,'4'),"+
                    "(85507,'6'),"+
                    "(85508,'6'),"+
                    "(85511,'CC1'),"+
                    "(85511,'CC2'),"+
                    "(85511,'107'),"+
                    "(85515,'105'),"+
                    "(85515,'4'),"+
                    "(85515,'6'),"+
                    "(85524,'6'),"+
            //SECCION 2
                    "(85528,'6'),"+
                    "(85528,'4'),"+
                    "(85531,'6'),"+
                    "(85536,'6'),"+
                    "(85535,'6'),"+
                    "(85540,'CC3'),"+
                    "(85543,'6'),"+
                    "(85544,'6'),"+
            //SECCION 3
                    "(85545,'6'),"+
                    "(85556,'4'),"+
                    "(85549,'4'),"+
                    "(85558,'4'),"+
                    "(85559,'CC3'),"+
                    "(85559,'107'),"+
                    "(85560,'4'),"+
                    "(85560,'113'),"+
                    "(85574,'4'),"+
            //BLOQUE 3
            //SECCION 1

                    "(88311,'F403'),"+
                    "(88311,'F101'),"+
                    "(19166,'106'),"+
                    "(19166,'F102'),"+
                    "(19166,'5'),"+
                    "(19168,'103'),"+
                    "(19168,'CC2'),"+
                    "(19178,'5'),"+
                    "(20577,'F404'),"+
                    "(20577,'103'),"+
                    "(20576,'111'),"+
                    "(20576,'F403'),"+
                    "(20576,'F404'),"+
            //SECCION 2
                    "(19182,'5'),"+
                    "(19182,'112'),"+
                    "(19053,'107'),"+
                    "(19053,'CC2'),"+
                    "(19199,'F402'),"+
                    "(19199,'112'),"+
                    "(19199,'5'),"+
                    "(92747,'105'),"+
                    "(92747,'5'),"+
                    "(92747,'4'),"+
                    "(20578,'F101'),"+
            //BLOQUE 5
            //SECCION 1
                    "(87222,'111'),"+
                    "(87222,'4'),"+
                    "(87222,'113'),"+
                    "(87224,'4'),"+
                    "(87224,'112'),"+
                    "(87228,'CC1'),"+
                    "(87235,'111'),"+
                    "(87235,'105'),"+
                    "(87236,'4'),"+
                    "(87242,'107'),"+
                    "(87242,'111'),"+
                    "(89274,'5'),"+
                    "(89274,'112'),"+
                    "(89274,'4'),"+
            //SECCION 2
                    "(87246,'4'),"+
                    "(87246,'113'),"+
                    "(87248,'112'),"+
                    "(87248,'4'),"+
                    "(87248,'105'),"+
                    "(87250,'CC2'),"+
                    "(87250,'107'),"+
                    "(87251,'111'),"+
                    "(87251,'106'),"+
                    "(87252,'113'),"+
                    "(87253,'111'),"+
                    "(87253,'113'),"+
            //SECCION 3
                    "(91576,'F402'),"+
                    "(91576,'4'),"+
                    "(91576,'112'),"+
                    "(91577,'113'),"+
                    "(91577,'CC2'),"+
                    "(91577,'105'),"+
                    "(91578,'CC2'),"+
                    "(91578,'CC3'),"+
                    "(91581,'111'),"+
                    "(91581,'106'),"+
                    "(91582,'106'),"+
                    "(91582,'6'),"+
                    "(91582,'107'),"+
                    "(91584,'102'),"+
                    "(91584,'F102'),"+
                    "(91584,'113'),"+
            //BLOQUE 5
            //SECCION 1
                    "(91528,'CC1'),"+
                    "(91528,'113'),"+
                    "(91531,'6'),"+
                    "(91531,'113'),"+
                    "(91534,'5'),"+
                    "(91534,'112'),"+
                    "(91534,'107'),"+
                    "(91535,'106'),"+
                    "(91535,'111'),"+
                    "(91535,'F402'),"+
                    "(91537,'113'),"+
            //SECCION 2
                    "(91538,'5'),"+
                    "(91538,'4'),"+
                    "(91538,'103'),"+
                    "(91540,'107'),"+
                    "(91541,'107'),"+
                    "(91542,'4'),"+
                    "(91545,'F102'),"+
                    "(91545,'113'),"+
                    "(91545,'112'),"+
            //SECCION 3
                    "(97138,'112'),"+
                    "(97138,'5'),"+
                    "(97139,'112'),"+
                    "(97139,'107'),"+
                    "(97140,'107'),"+
                    "(97141,'F402'),"+
                    "(97141,'107'),"+
                    "(97142,'5'),"+
            //BLOQUE 7
            //SECCION 1
                    "(96781,'112'),"+
                    "(96781,'113'),"+
                    "(96781,'214'),"+
                    "(19541,'F402'),"+
                    "(19524,'F402'),"+
                    "(97025,'5'),"+
                    "(97027,'5'),"+
            //SECCION 2
                    "(15758,'F402'),"+
                    "(15758,'103'),"+
                    "(19599,'105'),"+
                    "(19599,'4'),"+
                    "(19599,'106'),"+
                    "(19529,'103'),"+
                    "(19529,'F402'),"+
                    "(19529,'104'),"+
                    "(15757,'5'),"+
                    "(13594,'5'),"+
            //SECCION 3
                    "(15941,'4'),"+
                    "(15941,'5'),"+
                    "(20806,'CC4'),"+
                    "(20806,'F101'),"+
            //SECCION 4
                    "(20811,'CC1'),"+
                    "(20811,'F103'),"+
                    "(21493,'112'),"+
                    "(21493,'106')"+";");
            Toast.makeText(this, "Cargando Datos...", Toast.LENGTH_SHORT).show();
            database.setTransactionSuccessful();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

            Toast.makeText(this, "Cargando Datos...", Toast.LENGTH_SHORT).show();
            database.setTransactionSuccessful();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            database.endTransaction();
        }
    }


    private void insertarDatosMaterias(){
        //En este metodo se agregaran todas la materias de las carreras, se iran comentando las
        database.beginTransaction();
        try {
            //Insertar Materias de la carrera de  Ingeniería de Software
            //Seccion 1
            database.execSQL("insert into Materias(NRC,CARRERA,EE,BLOQUE,SECCION,IDPERSONAL) values" +
                    "(72230,'ISO','FUNDAMENTOS DE MATEMÁTICAS',1,1,0001),"+
                    "(73231,'ISO','INTRODUCCIÓN A LA PROGRAMACIÓN',1,1,0002),"+
                    "(73272,'ISO','HABILIDADES DEL PENSAMIENTO',1,1,0003),"+
                    "(96703,'ISO','LECTURA Y REDACCIÓN',1,1,0004),"+
                    "(73274,'ISO','INGLÉS I',1,1,0005),"+
                    "(88210,'ISO','COMPUTACIÓN BÁSICA',1,1,0006),"+
                    //Seccion 2
                    "(72236,'ISO','FUNDAMENTOS DE MATEMÁTICAS',1,2,0007),"+
                    "(73237,'ISO','INTRODUCCIÓN A LA PROGRAMACIÓN',1,2,0008),"+
                    "(73275,'ISO','HABILIDADES DEL PENSAMIENTO',1,2,0009),"+
                    "(96708,'ISO','LECTURA Y REDACCIÓN',1,2,0004),"+
                    "(73279,'ISO','INGLÉS I',1,2,0010),"+
                    "(88220,'ISO','COMPUTACIÓN BÁSICA',1,1,0011),"+
                    //Seccion 3
                    "(80555,'ISO','HABILIDADES DEL PENSAMIENTO',1,3,0012),"+
                    "(76745,'ISO','LECTURA Y REDACCIÓN',1,3,0013),"+

                    //Bloque 3
                    //Seccion 1
                    "(75641,'ISO','BASES DE DATOS',3,1,0014),"+
                    "(75637,'ISO','ESTRUCTURAS DE DATOS',3,1,0015),"+
                    "(75649,'ISO','PROBABILIDAD Y ESTADÍSTICA PARA COMPUTACIÓN',3,1,0016),"+
                    "(75633,'ISO','REQUERIMIENTOS DE SOFTWARE',3,1,0002),"+
                    "(75647,'ISO','SISTEMAS OPERATIVOS',3,1,0017),"+
                    "(75778,'ISO','PROGRAMACIÓN',3,1,0018),"+
                    "(12665,'ISO','ÁLGEBRA LINEAL PARA COMPUTACIÓN',3,1,0019),"+
                    //Seccion 2
                    "(75642,'ISO','BASES DE DATOS',3,2,0020),"+
                    "(75640,'ISO','ESTRUCTURAS DE DATOS',3,2,0021),"+
                    "(75650,'ISO','PROBABILIDAD Y ESTADÍSTICA PARA COMPUTACIÓN',3,1,0022),"+
                    "(75635,'ISO','REQUERIMIENTO DE SOFTWARE',3,2,0018),"+
                    "(75648,'ISO','SISTEMAS OPERATIVOS',3,2,0023),"+
                    //Seccion 3
                    "(95590,'ISO','BASES DE DATOS',3,3,0014),"+
                    "(95596,'ISO','ESTRUCTURAS DE DATOS',3,3,0024),"+
                    "(95598,'ISO','PROBABILIDAD Y ESTADÍSTICA PARA COMPUTACIÓN',3,3,0001),"+
                    "(95599,'ISO','REQUERIMIENTOS DE SOFTWARE',3,3,0025),"+
                    "(95600,'ISO','SISTEMAS OPERATIVOS',3,3,0026),"+
                    //Bloque 5
                    //Seccion 1
                    "(80598,'ISO','ADMINISTRACIÓN DE PROYECTOS DE SOFTWARE',5,1,0027),"+
                    "(80600,'ISO','DERECHO DE LAS TECNOLOGÍAS DE INFORMACIÓN Y COMUNICACIÓN',5,1,0028),"+
                    "(80602,'ISO','DISEÑO DE SOFTWARE',5,1,0029),"+
                    "(80604,'ISO','PRUEBA DE SOFTWARE',5,1,0027),"+
                    "(80606,'ISO','TECNOLOGÍAS PARA LA CONSTRUCCIÓN DE SOFTWARE',5,1,0030),"+
                    "(80607,'ISO','PARADIGMAS DE PROGRAMACIÓN',5,1,0031),"+
                    "(80610,'ISO','PRINCIPIOS DE DISEÑO DE SOFTWARE',5,1,0032),"+
                    "(80612,'ISO','PRINCIPIOS DE CONSTRUCCIÓN DE SOFTWARE',5,1,0033),"+
                    //Seccion 2
                    "(80613,'ISO','ADMINISTRACIÓN DE PROYECTOS DE SOFTWARE',5,2,0027),"+
                    "(80615,'ISO','DERECHO DE LAS TECNOLOGÍAS DE INFORMACIÓN Y COMUNICACIÓN',5,2,0028),"+
                    "(80616,'ISO','DISEÑO DE SOFTWARE',5,2,0034),"+
                    "(80617,'ISO','PRUEBA DE SOFTWARE',5,2,0035),"+
                    "(80622,'ISO','TECNOLOGÍAS PARA LA CONSTRUCCIÓN DE SOFTWARE',5,2,0030),"+
                    //Bloque 7
                    //Seccion 1
                    "(83492,'ISO','PROYECTO GUIADO',7,1,0034),"+
                    "(12342,'ISO','PRÁCTICAS DE INGENIERÍA DE SOFTWARE',7,1,0021),"+
                    "(83497,'ISO','DESARROLLO DE SISTEMAS WEB',7,1,0036),"+
                    "(83501,'ISO','DESARROLLO DE SOFTWARE',7,1,0037),"+
                    "(83503,'ISO','DESARROLLO DE APLICACIONES',7,1,0038),"+
                    "(87276,'ISO','DESARROLLO DE SISTEMAS EN RED',7,1,0038),"+
                    "(12355,'ISO','ECONOMÍA PARA LA TOMA DE DECISIONES',7,1,0039),"+
                    "(87275,'ISO','DISEÑO DE INTERFACES DE USUARIO',7,1,0018),"+
                    "(87272,'ISO','EXPERIENCIA RECEPCIONAL',7,1,0034),"+
                    "(91336,'ISO','SERVICIO SOCIAL',7,1,0021),"+
                    "(95601,'ISO','PRUEBAS DE PENETRACIÓN',7,1,0040),"+
                    "(95603,'ISO','MEDICIÓN DE SOFTWARE',7,1,0021),"+
                    "(12423,'ISO','ADMINISTRACIÓN AVANZADA DE SERVICIOS',7,1,0041),"+
                    //Seccion 2
                    "(83504,'ISO','PROYECTO GUIADO',7,2,0042),"+
                    "(12345,'ISO','PRÁCTICAS DE INGENIERÍA DE SOFTWARE',7,2,0027),"+
                    "(83506,'ISO','DESARROLLO DE SISTEMAS WEB',7,2,0030),"+
                    "(83507,'ISO','ECONOMÍA PARA LA TOMA DE DECISIONES',7,2,0043),"+


                    //Carrera de TECNOLOGÍAS COMPUTACIÓNales
                    //Bloque 1
                    //Seccion 1
                    "(73282,'TECO','TECNOLOGÍAS DE INFORMACIÓN PARA LA INNOVACIÓN',1,1,0044),"+
                    "(73283,'TECO','FUNDAMENTOS DE MATEMÁTICAS',1,1,0045),"+
                    "(73285,'TECO','INTRODUCCIÓN A LA PROGRAMACIÓN',1,1,0046),"+
                    "(76830,'TECO','HABILIDADES DEL PENSAMIENTO',1,1,0047),"+
                    "(73288,'TECO','INGLÉS I',1,1,0048),"+

                    //Seccion 2
                    "(73292,'TECO','TECNOLOGÍAS DE INFORMACIÓN PARA LA INNOVACIÓN',1,2,0014),"+
                    "(73294,'TECO','FUNDAMENTOS DE MATEMÁTICAS',1,2,0049),"+
                    "(73295,'TECO','INTRODUCCIÓN A LA PROGRAMACIÓN',1,2,0050),"+
                    "(73897,'TECO','HABILIDADES DEL PENSAMIENTO',1,2,0051),"+
                    "(73298,'TECO','INGLÉS I',1,2,0052),"+
                    //Seccion 3
                    "(81891,'TECO','TECNOLOGÍAS DE INFORMACIÓN PARA LA INNOVACIÓN',1,3,0053),"+
                    "(81881,'TECO','FUNDAMENTOS DE MATEMÁTICAS',1,3,0054),"+
                    "(81864,'TECO','INTRODUCCIÓN A LA PROGRAMACIÓN',1,3,0055),"+
                    //Bloque 3
                    //Seccion 1
                    "(75655,'TECO','BASES DE DATOS',3,1,0056),"+
                    "(75667,'TECO','ESTRUCTURA DE DATOS',3,1,0057),"+
                    "(75660,'TECO','MATEMÁTICAS DISCRETAS',3,1,0058),"+
                    "(75651,'TECO','ORGANIZACIÓN DE COMPUTADORAS',3,1,0059),"+
                    "(75662,'TECO','SISTEMAS OPERATIVOS',3,1,0060),"+
                    "(97579,'TECO','INGLÉS II',3,1,0061),"+
                    "(75776,'TECO','PROBABILIDAD Y ESTADÍSTICA PARA COMPUTACIÓN',3,1,0062),"+
                    "(75774,'TECO','PROGRAMACIÓN',3,1,0019),"+
                    "(75775,'TECO','ÁLGEBRA LINEAL PARA COMPUTACIÓN',3,1,0001),"+
                    //Seccion 2
                    "(75657,'TECO','BASES DE DATOS',3,2,0063),"+
                    "(75668,'TECO','ESTRUCTURA DE DATOS',3,2,0064),"+
                    "(75661,'TECO','MATEMÁTICAS DISCRETAS',3,2,0065),"+
                    "(75653,'TECO','ORGANIZACIÓN DE COMPUTADORAS',3,2,0066),"+
                    "(75664,'TECO','SISTEMAS OPERATIVOS',3,2,0067),"+
                    "(97582,'TECO','INGLÉS II',3,2,0068),"+
                    //BLOQUE 5
                    //SECCION 1
                    "(80630,'TECO','ADMINISTRADOR DE SERVIDORES',5,1,0066),"+
                    "(80633,'TECO','ÉTICA Y LEGISLACIÓN INFORMÁTICA',5,1,0067),"+
                    "(80634,'TECO','HABILIDADES DIRECTIVAS',5,1,0068),"+
                    "(80637,'TECO','INTERACCIÓN HUMANO COMPUTADORA',5,1,0044),"+
                    "(80638,'TECO','METODOLOGÍAS DE DESARROLLO',5,1,0069),"+
                    "(80640,'TECO','SISTEMAS WEB',5,1,0070),"+
                    //SECCION 2
                    "(80675,'TECO','ADMINISTRADOR DE SERVIDORES',5,2,0066),"+
                    "(80676,'TECO','ÉTICA Y LEGISLACIÓN INFORMÁTICA',5,2,0071),"+
                    "(80679,'TECO','HABILIDADES DIRECTIVAS',5,2,0027),"+
                    "(80680,'TECO','INTERACCIÓN HUMANO COMPUTADORA',5,2,0070),"+
                    "(80685,'TECO','METODOLOGÍAS DE DESARROLLO',5,2,0071),"+
                    "(80688,'TECO','SISTEMAS WEB',5,2,0070),"+
                    //BLOQUE 7
                    //SECCION 1
                    "(83571,'TECO','SEGURIDAD',7,1,0060),"+
                    "(83572,'TECO','PROYECTO INTEGRADOR',7,1,0020),"+
                    "(83573,'TECO','GRAFICACIÓN',7,1,0072),"+
                    "(83574,'TECO','INTERFACES DE USUARIO AVANZADAS',7,1,0018),"+
                    "(87590,'TECO','EXPERIENCIA RECEPCIONAL',7,1,0073),"+
                    "(87608,'TECO','SERVICIO SOCIAL',7,1,0069),"+
                    //SECCION 2
                    "(83575,'TECO','INTEGRACIÓN DE SOLUCIONES',7,2,0033),"+
                    "(83576,'TECO','SEGURIDAD',7,2,0060),"+
                    "(83577,'TECO','PROYECTO INTEGRADOR',7,2,0074),"+
                    "(87591,'TECO','ADMINISTRACIÓN DE BASES DE DATOS',7,2,0014),"+
                    "(85237,'TECO','SERVICIOS DE VIRTUALIZACIÓN',7,2,0075),"+
                    "(19564,'TECO','EXPERIENCIA RECEPCIONAL',7,2,0019),"+
                    //SECCION 3
                    "(98689,'TECO','INTEGRACIÓN A SOLUCIONES',7,3,0033),"+

                    //CARRERA DE REDES
                    //BLOQUE 1
                    //SECCION 1

                    "(73238,'REDES','ARQUITECTURA DE DISPOSITIVOS DE RED',1,1,0026),"+
                    "(73239,'REDES','FUNDAMENTOS DE MATEMÁTICAS',1,1,0076),"+
                    "(73241,'REDES','INTRODUCCIÓN A LA PROGRAMACIÓN',1,1,0002),"+
                    "(73246,'REDES','LECTURA Y REDACCIÓN',1,1,0041),"+
                    "(76731,'REDES','INGLÉS I',1,1,0042),"+
                    //SECCION 2
                    "(73248,'REDES','ARQUITECTURA DE DISPOSITIVOS DE RED',1,2,0079),"+
                    "(73267,'REDES','FUNDAMENTOS DE MATEMÁTICAS',1,2,0061),"+
                    "(73269,'REDES','INTRODUCCIÓN A LA PROGRAMACIÓN',1,2,0080),"+
                    "(73270,'REDES','LECTURA Y REDACCIÓN',1,2,0077),"+
                    "(73271,'REDES','INGLÉS I',1,2,0048),"+
                    //SECCION 3
                    "(13434,'REDES','ARQUITECTURA DE DISPOSITIVOS DE RED',1,3,0079),"+
                    "(13451,'REDES','FUNDAMENTOS DE MATEMÁTICAS',1,3,0076),"+
                    "(13460,'REDES','INTRODUCCIÓN A LA PROGRAMACIÓN',1,3,0081),"+
                    //BLOQUE 3
                    //SECCION 1
                    "(75723,'REDES','BASES DE DATOS',3,1,0050),"+
                    "(13468,'REDES','ESTRUCTURA DE DATOS',3,1,0053),"+
                    "(75732,'REDES','METODOLOGÍA DE LA INVESTIGACIÓN',3,1,0068),"+
                    "(75720,'REDES','PROGRAMACIÓN DE SISTEMAS',3,1,0046),"+
                    "(75717,'REDES','REDES',3,1,0082),"+
                    "(76756,'REDES','COMPUTACIÓN BÁSICA',3,1,0083),"+
                    "(95821,'REDES','PROGRAMACIÓN',3,1,0018),"+
                    "(98993,'REDES','HABILIDADES DEL PENSAMIENTO CRÍTICO Y CREATIVO',3,1,0012),"+
                    //SECCION 2
                    "(75724,'REDES','BASES DE DATOS',3,2,0020),"+
                    "(75731,'REDES','ESTRUCTURAS DE DATOS',3,2,0019),"+
                    "(75733,'REDES','METODOLOGÍA DE LA INVESTIGACIÓN',3,2,0119),"+
                    "(75721,'REDES','PROGRAMACIÓN DE SISTEMAS',3,2,0076),"+
                    "(75718,'REDES','REDES',3,2,0084),"+
                    "(76733,'REDES','COMPUTACIÓN BÁSICA',3,2,0083),"+
                    //SECCION 3
                    "(95897,'REDES','INTRODUCCIÓN A LA PROGRAMACIÓN',3,3,0046),"+
                    //BLOQUE 5
                    "(80693,'REDES','ADMINISTRACIÓN DE SERVIDORES',5,1,0085),"+
                    "(80695,'REDES','ANÁLISIS DE PROTOCOLOS',5,1,0082),"+
                    "(80697,'REDES','ENRUTAMIENTO AVANZADO',5,1,0084),"+
                    "(80700,'REDES','ÉTICA Y NORMATIVIDAD EN COMUNICACIONES Y REDES',5,1,0086),"+
                    "(80705,'REDES','PROGRAMACIÓN EN LA ADMINISTRACIÓN DE REDES',5,1,0087),"+
                    "(80707,'REDES','SISTEMAS OPERATIVOS APLICADOS',5,1,0079),"+
                    //SECCION 2
                    "(83555,'REDES','ADMINISTRACIÓN DE SERVIDORES',5,2,0088),"+
                    "(83557,'REDES','ANÁLISIS DE PROTOCOLOS',5,2,0023),"+
                    "(83558,'REDES','ENRUTAMIENTO AVANZADO',5,2,0088),"+
                    "(83559,'REDES','ÉTICA Y NORMATIVIDAD EN COMUNICACIONES Y REDES',5,2,0086),"+
                    "(83561,'REDES','PROGRAMACIÓN EN LA ADMINISTRACIÓN DE REDES',5,2,0023),"+
                    "(83562,'REDES','SISTEMAS OPERATIVOS APLICADOS',5,2,0023),"+
                    //BLOQUE 7
                    //SECCION 1
                    "(83565,'REDES','PRÁCTICAS DE REDES',7,1,0088),"+
                    "(83566,'REDES','ADMINISTRACIÓN DE PROYECTOS DE RED',7,1,0086),"+
                    "(83567,'REDES','DESARROLLO DE SISTEMAS WEB',7,1,0087),"+
                    "(95922,'REDES','PRUEBAS DE PENETRACION',7,1,0017),"+
                    "(19666,'REDES','ADMINISTRACIÓN EN BASES DE DATOS',7,1,0036),"+
                    //SECCION 2
                    "(87500,'REDES','PRÁCTICAS DE REDES',7,2,0079),"+
                    "(87503,'REDES','ADMINISTRACIÓN DE PROYECTOS DE RED',7,2,0041),"+
                    "(87504,'REDES','DESARROLLO DE SISTEMAS WEB',7,2,0081),"+
                    "(19469,'REDES','ANÁLISIS FORENSE PARA SISTEMAS DE ESCRITORIO',7,2,0040),"+
                    //BLOQUE 9
                    // SECCION 1
                    "(87507,'REDES','EXPERIENCIA RECEPCIONAL',9,1,0020),"+
                    "(87508,'REDES','SERVICIO SOCIAL',9,1,0088),"+
                    //SECCION 2
                    "(91570,'REDES','EXPERIENCIA RECEPCIONAL',9,2,0087),"+

                    //CARRERA ESTADÍSTICA
                    //BLOQUE 1
                    //SECCION 1

                    "(85505,'ESTADÍSTICA','ÁLGEBRA LINEAL APLICADA A LA ESTADÍSTICA I',1,1,0007),"+
                    "(85506,'ESTADÍSTICA','CÁLCULO APLICADO A LA ESTADÍSTICA I',1,1,0090),"+
                    "(85507,'ESTADÍSTICA','METODOLOGÍA ESTADÍSTICA PARA LA INVESTIGACIÓN',1,1,0054),"+
                    "(85508,'ESTADÍSTICA','ESTADÍSTICA DESCRIPTIVA Y EXPLORATORIA',1,1,0091),"+
                    "(85511,'ESTADÍSTICA','LITERACIDAD DIGITAL',1,1,0092),"+
                    "(85515,'ESTADÍSTICA','LENGUA I',1,1,0093),"+
                    "(85524,'ESTADÍSTICA','LECTURA Y ESCRITURA DE TEXTOS ACADÉMICOS',1,1,0094),"+
                    //SECCION 2
                    "(85528,'ESTADÍSTICA','ÁLGEBRA LINEAL APLICADA A LA ESTADÍSTICA I',1,2,0007),"+
                    "(85531,'ESTADÍSTICA','CÁLCULO APLICADO A LA ESTADÍSTICA I',1,2,0095),"+
                    "(85536,'ESTADÍSTICA','METODOLOGÍA ESTADÍSTICA PARA LA INVESTIGACIÓN',1,2,0091),"+
                    "(85535,'ESTADÍSTICA','ESTADÍSTICA DESCRIPTIVA Y EXPLORATORIA',1,2,0096),"+
                    "(85540,'ESTADÍSTICA','LITERACIDAD DIGITAL',1,2,0092),"+
                    "(85543,'ESTADÍSTICA','LENGUA I',1,2,0097),"+
                    "(85544,'ESTADÍSTICA','LECUTURA Y ESCRITURA DE TEXTOS ACADÉMICOS',1,2,0098),"+
                    //SECCION 3
                    "(85545,'ESTADÍSTICA','ÁLGEBRA LINEAL APLICADA A LA ESTADÍSTICA I',1,3,0099),"+
                    "(85556,'ESTADÍSTICA','CÁLCULO APLICADO A LA ESTADÍSTICA I',1,3,0100),"+
                    "(85549,'ESTADÍSTICA','METODOLOGÍA ESTADÍSTICA PARA LA INVESTIGACIÓN',1,3,0101),"+
                    "(85558,'ESTADÍSTICA','ESTADÍSTICA DESCRIPTIVA Y EXPLORATORIA',1,3,0102),"+
                    "(85559,'ESTADÍSTICA','LITERACIDAD DIGITAL',1,3,0103),"+
                    "(85560,'ESTADÍSTICA','LENGUA I',1,3,0104),"+
                    "(85574,'ESTADÍSTICA','LECTURA Y ESCRITURA DE TEXTOS ACADÉMICOS',1,3,0105),"+
                    //BLOQUE 2
                    //SECCION 1
                    "(88311,'ESTADÍSTICA','CÁLCULO APLICADO A LA ESTADÍSTICA II',2,1,0095),"+
                    "(19166,'ESTADÍSTICA','PENSAMIENTO Y CULTURA ESTADÍSTICA',2,1,0106),"+
                    "(19168,'ESTADÍSTICA','INTRODUCCIÓN A LA PROGRAMACIÓN ESTADÍSTICA',2,1,0058),"+
                    "(19178,'ESTADÍSTICA','ÁLGEBRA LINEAL APLICADA A LA ESTADÍSTICA II',2,1,0063),"+
                    "(20577,'ESTADÍSTICA','PENSAMIENTO CRÍTICO PARA LA SOLUCIÓN DE PROBLEMAS',2,1,0107),"+
                    "(20576,'ESTADÍSTICA','LENGUA I',2,1,0104),"+
                    //SECCION 2
                    "(19182,'ESTADÍSTICA','PENSAMIENTO Y CULTURA ESTADÍSTICA',2,2,0106),"+
                    "(19053,'ESTADÍSTICA','INTRODUCCIÓN A LA PROGRAMACIÓN ESTADÍSTICA',2,2,0075),"+
                    "(19199,'ESTADÍSTICA','CÁLCULO APLICADO A LA ESTADÍSTICA II',2,2,0106),"+
                    "(92747,'ESTADÍSTICA','ÁLGEBRA LINEAL APLICADA A LA ESTADÍSTICA II',2,2,0099),"+
                    "(20578,'ESTADÍSTICA','PENSAMIENTO CRÍTICO PARA LA SOLUCIÓN DE PROBLEMAS',2,2,0107),"+
                    //BLOQUE 3
                    //SECCION 1
                    "(87222,'ESTADÍSTICA','MUESTREO',3,1,0108),"+
                    "(87224,'ESTADÍSTICA','MODELOS DE REGRESIÓN LINEAL Y NO LINEAL',3,1,0109),"+
                    "(87228,'ESTADÍSTICA','PROGRAMACIÓN ESTADÍSTICA',3,1,0110),"+
                    "(87235,'ESTADÍSTICA','PROBABILIDAD I',3,1,0095),"+
                    "(87236,'ESTADÍSTICA','INSTRUMENTOS DE CAPTACIÓN Y ANÁLISIS DE DATOS',3,1,0111),"+
                    "(87242,'ESTADÍSTICA','SEMINARIO DE APLICACIONES ESTADÍSTICAS',3,1,0116),"+
                    "(89274,'ESTADÍSTICA','LENGUA II',3,1,0010),"+
                    //SECCION 2
                    "(87246,'ESTADÍSTICA','MUESTREO',3,2,0112),"+
                    "(87248,'ESTADÍSTICA','MODELOS DE REGRESIÓN LINEAL Y NO LINEAL',3,2,0101),"+
                    "(87250,'ESTADISTICA','PROGRAMACIÓN ESTADÍSTICA',3,2,0113),"+
                    "(87251,'ESTADÍSTICA','PROBABILIDAD I',3,2,0016),"+
                    "(87252,'ESTADÍSTICA','INTRUMENTOS DE CAPTACIÓN Y ANÁLISIS DE DATOS',3,2,0114),"+
                    "(87253,'ESTADÍSTICA','SEMINARIO DE APLICACIONES ESTADÍSTICAS',3,2,0114),"+
                    //SECCION 3
                    "(91576,'ESTADÍSTICA','MUESTREO',3,3,0106),"+
                    "(91577,'ESTADÍSTICA','MODELOS DE REGRESIÓN LINEAL Y NO LINEAL',3,3,0114),"+
                    "(91578,'ESTADÍSTICA','PROGRAMACIÓN ESTADÍSTICA',3,3,0075),"+
                    "(91581,'ESTADÍSTICA','PROBABILIDAD I',3,3,0045),"+
                    "(91582,'ESTADÍSTICA','INSTRUMENTOS DE CAPTACIÓN Y ANÁLISIS DE DATOS',3,3,0091),"+
                    "(91584,'ESTADÍSTICA','SEMINARIO DE APLICACIONES ESTADÍSTICAS',3,3,0115),"+
                    //BLOQUE 5
                    //SECCION 1
                    "(91128,'ESTADÍSTICA','MODELOS ESPACIOS-TEMPORALES',5,1,0110),"+
                    "(91531,'ESTADÍSTICA','MODELOS DE REGRESIÓN NO PARAMÉTRICA Y SEMIPARAMÉTRICA',5,1,0101),"+
                    "(91534,'ESTADÍSTICA','MINERÍA DE DATOS Y APRENDIZAJE MÁQUINA',5,1,0116),"+
                    "(91535,'ESTADÍSTICA','TEORÍA ESTADÍSTICA',5,1,0117),"+
                    "(91537,'ESTADÍSTICA','CONSULTORÍA ESTADÍSTICA',5,1,0119),"+
                    //SECCION 2
                    "(91538,'ESTADÍSTICA','MODELOS ESPACIOS-TEMPORALES',5,2,0109),"+
                    "(91540,'ESTADÍSTICA','MODELOS DE REGRESIÓN NO PARAMÉTRICA Y SEMIPARAMÉTRICA',5,2,0116),"+
                    "(91541,'ESTADÍSTICA','MINERÍA DE DATOS Y APRENDIZAJE MÁQUINA',5,2,0117),"+
                    "(91542,'ESTADÍSTICA','TEORÍA ESTADÍSTICA',5,2,0109),"+
                    "(91545,'ESTADÍSTICA','CONSULTORÍA ESTADÍSTICA',5,2,0110),"+
                    //SECCION 3
                    "(97138,'ESTADÍSTICA','MODELOS ESPACIOS-TEMPORALES',5,3,0045),"+
                    "(97139,'ESTADÍSTICA','MODELOS DE REGRESIÓN NO PARAMÉTRICA Y SEMIPARAMÉTRICA',5,3,0118),"+
                    "(97140,'ESTADÍSTICA','MINERÍA DE DATOS Y APREDIZAJE MÁQUINA',5,3,0075),"+
                    "(97141,'ESTADÍSTICA','TEORÍA ESTADÍSTICA',5,3,0106),"+
                    "(97142,'ESTADÍSTICA','CONSULTORÍA ESTADÍSTICA',5,3,0111),"+
                    //BLOQUE 7
                    //SECCION 1

                    "(96781,'ESTADÍSTICA','BOOTSTRAP Y PRUEBAS DE PERMUTACIÓN',7,1,0117),"+
                    "(19541,'ESTADÍSTICA','VIZUALIZACIÓN DE DATOS',7,1,0075),"+
                    "(19524,'ESTADÍSTICA','ESTADÍSTICA EN LA SALUD',7,1,0119),"+
                    "(97025,'ESTADÍSTICA','SERVICIO SOCIAL',7,1,0120),"+
                    "(97027,'ESTADÍSTICA','EXPERIENCIA RECEPCIONAL I',7,1,0121),"+
                    //SECCION 2
                    "(15758,'ESTADÍSTICA','BOOTSTRAP Y PRUEBAS DE PERMUTACION',7,2,0102),"+
                    "(19599,'ESTADÍSTICA','VISUALIZACIÓN DE DATOS',7,2,0122),"+
                    "(19529,'ESTADÍSTICA','ESTADÍSTICA EN LA SALUD',7,2,0123),"+
                    "(15757,'ESTADÍSTICA','SERVICIO SOCIAL',7,2,0120),"+
                    "(13594,'ESTADÍSTICA','EXPERIENCIA RECEPCIONAL I',7,2,0001),"+
                    //SECCION 3
                    "(15941,'ESTADÍSTICA','EXPERIENCIA RECEPCIONAL II',7,3,0118),"+
                    "(20806,'ESTADÍSTICA','SERVICIO SOCIAL',7,3,0120),"+
                    //SECCION 4
                    "(20811,'ESTADÍSTICA','SERVICIO SOCIAL',7,4,0120),"+
                    "(21493,'ESTADÍSTICA','EXPERIENCIA RECEPCIONAL',7,4,0091)"+";");
            Toast.makeText(this,"Descargando Datos...",Toast.LENGTH_LONG).show();
            database.setTransactionSuccessful();
        }catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            // finish transaction processing
            database.endTransaction();
        }
    }
    
    public void insertarDatosHorario(){
        database.beginTransaction();
        try {
            //BLOQUE 1 - SECCION 1
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,JUEVES,VIERNES) values"+
                    "(85505,'6','11:00-13:00','11:00-13:00','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,VIERNES) values"+
                    "(85506,'6','11:00-13:00','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(85506,'4','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES,JUEVES) values"+
                    "(85507,'6','09:00-11:00','09:00-11:00','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES,VIERNES) values"+
                    "(85508,'6','07:00-09:00','11:00-13:00','07:00-9:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(85511,'CC1','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(85511,'CC2','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(85511,'214','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(85515,'105','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(85515,'4','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(85515,'6','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES) values"+
                    "(85524,'6','13:00-15:00','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,VIERNES) values"+
                    "(88311,'F403','09:00-11:00','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(88311,'F101','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(92747,'105','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(92747,'5','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(92747,'4','17:00-19:00');");


//BLOQUE 1 - SECCION 2
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,VIERNES) values"+
                    "(85528,'6','15:00-17:00','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(85528,'4','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES,JUEVES) values"+
                    "(85531,'6','13:00-15:00','13:00-15:00','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES,MIERCOLES) values"+
                    "(85536,'6','19:00-21:00','15:00-17:00','19:00-21:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES,JUEVES) values"+
                    "(85535,'6','17:00-19:00','17:00-19:00','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES,VIERNES) values"+
                    "(85540,'CC3','13:00-15:00','13:00-15:00','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,JUEVES,VIERNES) values"+
                    "(85543,'6','19:00-21:00','19:00-21:00','19:00-21:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,VIERNES) values"+
                    "(85544,'6','15:00-17:00','17:00-19:00');");

//BLOQUE 1 - SECCION 3
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES,JUEVES) values"+
                    "(85545,'6','07:00-09:00','07:00-09:00','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,JUEVES,VIERNES) values"+
                    "(85556,'4','09:00-11:00','09:00-11:00','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES,JUEVES) values"+
                    "(85549,'4','13:00-15:00', '11:00-13:00', '13:00-15:00');");
           database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,JUEVES,VIERNES) values"+
                    "(85558,'4','11:00-13:00','11:00-13:00','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES) values"+
                    "(85559,'CC3','09:00-11:00','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(85559,'214','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,VIERNES) values"+
                    "(85560,'4','11:00-13:00','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(85560,'113','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES) values"+
                    "(85574,'4','15:00-17:00','13:00-15:00');");

//BLOQUE 3 - SECCION 1
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(87222,'111','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(87222,'4','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(87222,'113','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(87224,'4','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES,VIERNES) values"+
                    "(87224,'112','09:00-11:00','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES,MIERCOLES) values"+
                    "(87228,'214','13:00-15:00','13:00-15:00','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(87235,'111','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES) values"+
                    "(87235,'105','11:00-13:00','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES,VIERNES) values"+
                    "(87236,'4','07:00-9:00','07:00-9:00','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(87242,'107','13:00-14:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,JUEVES) values"+
                    "(87242,'111','07:00-9:00','07:00-9:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(89274,'5','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(89274,'112','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(89274,'4','13:00-15:00');");

//BLOQUE 3 - SECCION 2
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(87246,'4','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES) values"+
                    "(87246,'113','17:00-19:00','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(87248,'112','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(87248,'4','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(87248,'105','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(87250,'CC2','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,JUEVES) values"+
                    "(87250,'214','11:00-13:00','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,VIERNES) values"+
                    "(87251,'111','11:00-13:00','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(87251,'106','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES,VIERNES) values"+
                    "(87252,'113','09:00-11:00','09:00-11:00','9:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(87253,'111','13:00-14:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES) values"+
                    "(87253,'113','13:00-15:00','13:00-15:00');");

//BLOQUE 3 - SECCION 3
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(91576,'F402','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(91576,'4','19:00-21:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(91576,'112','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(91577,'113','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(91577,'CC2','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(91577,'105','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,VIERNES) values"+
                    "(91578,'CC2','15:00-17:00','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(91578,'CC3','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,VIERNES) values"+
                    "(91581,'111','15:00-17:00','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(91581,'106','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(91582,'106','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(91582,'6','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(91582,'F402','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(91584,'102','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(91584,'F102','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(91584,'113','14:00-15:00');");

//BLOQUE 5 - SECCION 1
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES) values"+
                    "(91528,'214','09:00-11:00','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(91528,'113','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(91531,'214','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES,VIERNES) values"+
                    "(91531,'113','09:00-11:00','11:00-13:00');");

//BLOQUE 5 - SECCION 2
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES,MIERCOLES) values"+
                    "(91534,'214','11:00-13:00','09:00-11:00','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(91535,'106','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(91535,'111','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(91535,'F402','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES,MIERCOLES) values"+
                    "(91537,'113','07:00-9:00','07:00-09:00','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES) values"+
                    "(91538,'214','13:00-15:00','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(91538,'103','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES,VIERNES) values"+
                    "(91540,'214','15:00-17:00','15:00-17:00','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(91541,'CC1','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(91541,'103','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(91541,'214','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES,VIERNES) values"+
                    "(91542,'4','17:00-19:00','15:00-17:00','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(91545,'F102','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(91545,'113','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(91545,'112','11:00-13:00');");

//BLOQUE 5 - SECCION 1
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,VIERNES) values"+
                    "(97138,'214','07:00-9:00','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(97138,'5','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES,VIERNES) values"+
                    "(97139,'214','07:00-9:00','09:00-11:00','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,VIERNES) values"+
                    "(97140,'107','11:00-13:00','7:00-9:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(97140,'214','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(97141,'F402','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,VIERNES) values"+
                    "(97141,'107','09:00-11:00','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES) values"+
                    "(97142,'5','11:00-13:00','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(97142,'107','11:00-13:00');");

//BLOQUE 7 - SECCION 1
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(96781,'112','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(96781,'113','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(96781,'F102','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES,JUEVES) values"+
                    "(14662,'F402','09:00-11:00','09:00-11:00','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES,VIERNES) values"+
                    "(14664,'F402','11:00-13:00','11:00-13:00','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,VIERNES) values"+
                    "(97025,'5','09:00-11:00','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,JUEVES) values"+
                    "(97027,'5','07:00-09:00','07:00-9:00');");

//BLOQUE 7 - SECCION 2
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(15758,'F402','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(15758,'F101','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(15758,'F102','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(15759,'F402','19:00-21:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(15759,'F101','19:00-21:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(15759,'F102','19:00-21:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(15760,'F101','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(15760,'F402','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(15760,'5','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,JUEVES) values"+
                    "(15757,'5','15:00-17:00','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES) values"+
                    "(13594,'5','13:00-15:00','13:00-15:00');");

//BLOQUE 7 - SECCION 2
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES) values"+
                    "(15941,'107','07:00-09:00','07:00-09:00');");
//ISO
//BLOQUE 1 - SECCION 1 IS

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES,JUEVES) values"+
                    "(73230,'104','08:00-09:00','09:00-11:00','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES) values"+
                    "(73231,'104','07:00-09:00','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(73231,'CC2','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES) values"+
                    "(73272,'104','09:00-11:00','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,JUEVES) values"+
                    "(96703,'104','11:00-13:00','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,JUEVES,VIERNES) values"+
                    "(73274,'104','09:00-11:00','09:00-11:00','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,JUEVES,VIERNES) values"+
                    "(73231,'CC3','11:00-13:00','11:00-13:00','11:00-13:00');");


//BLOQUE1 SECCION2

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,VIERNES) values"+
                    "(73236,'104','14:00-15:00','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(73236,'106','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,JUEVES) values"+
                    "(73237,'104','15:00-17:00','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(73237,'CC2','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES) values"+
                    "(73275,'104','15:00-17:00','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(96708,'112','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(96708,'104','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES,VIERNES) values"+
                    "(73279,'104','17:00-19:00','17:00-19:00','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES,VIERNES) values"+
                    "(88220,'CC3','13:00-15:00','17:00-19:00','15:00-17:00');");


//BLOQUE1 SECCION3

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,JUEVES) values"+
                    "(80555,'106','15:00-17:00','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(76745,'102','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(76745,'104','11:00-13:00');");


//BLOQUE3 SECCION1

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,JUEVES) values"+
                    "(75641,'F403','09:00-11:00','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(75641,'CC3','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(75637,'CC2','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(75637,'103','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(75637,'F403','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(75649,'112','13:00-14:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,JUEVES) values"+
                    "(75649,'F403','11:00-13:00','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES) values"+
                    "(75633,'F403','11:00-13:00','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(75633,'CC2','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(75647,'CC2','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES) values"+
                    "(75647,'F403','07:00-09:00','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(75778,'CC3','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(75778,'F103','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(75778,'F403','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(12665,'104','14:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(12665,'111','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(12665,'F402','13:00-15:00');");


//BLOQUE3 SECCION2

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,JUEVES) values"+
                    "(75642,'F403','13:00-15:00','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(75642,'CC1','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES) values"+
                    "(75640,'F403','15:00-17:00','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(75640,'CC2','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES, MIERCOLES, JUEVES) values"+
                    "(75650,'F403','19:00-21:00','19:00-21:00','19:00-20:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES) values"+
                    "(75635,'F403','15:00-17:00','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(75635,'CC3','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES) values"+
                    "(75648,'F403','17:00-19:00','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(75648,'CC3','17:00-19:00');");

//BLOQUE3 SECCION3

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(95590,'104','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(95590,'CC1','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(95590,'F403','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(95596,'105','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(95596,'CC1','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(95596,'F404','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES) values"+
                    "(95598,'F101','19:00-20:00','19:00-21:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(95598,'F403','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES) values"+
                    "(95599,'104','19:00-21:00','19:00-21:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(95599,'CC3','19:00-21:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(95600,'CC1','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(95600,'CC2','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(95600,'F403','15:00-17:00');");


//BLOQUE5 SECCION1

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES) values"+
                    "(80598,'F103','09:00-11:00','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(80598,'CC2','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES,JUEVES) values"+
                    "(80600,'F103','13:00-14:00','07:00-09:00','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES) values"+
                    "(80602,'F103','11:00-13:00','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(80602,'CC1','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES) values"+
                    "(80604,'F103','07:00-09:00','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(80604,'CC3','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,VIERNES) values"+
                    "(80606,'F103','09:00-11:00','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(80606,'CC1','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(80607,'103','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(80607,'CC2','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(80607,'106','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(80610,'105','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(80610,'CC1','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(80610,'111','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(80612,'CC1','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(80612,'CC2','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(80612,'106','15:00-17:00');");

//BLOQUE5 SECCION2

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES) values"+
                    "(80613,'F103','15:00-17:00','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(80613,'CC1','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,JUEVES,VIERNES) values"+
                    "(80615,'F103','14:00-15:00','13:00-15:00','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,VIERNES) values"+
                    "(80616,'F103','15:00-17:00','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(80616,'CC1','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES) values"+
                    "(80617,'F103','17:00-19:00','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(80617,'CC3','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,JUEVES) values"+
                    "(80622,'F103','13:00-15:00','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(80622,'CC2','17:00-19:00');");


//BLOQUE7 SECCION1

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(83492,'F402','11:00-14:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(83492,'CC4','10:00-12:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,JUEVES) values"+
                    "(12342,'CDS','09:00-11:00','09:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES,VIERNES)  values"+
                    "(83497,'CC4','11:00-13:00','07:00-09:00','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(83501,'112','17:00-20:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(83501,'CC1','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(83503,'112','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(83503,'CC3','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(83503,'113','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(87276,'112','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(87276,'CC4','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(87276,'113','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(12355,'112','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(12355,'111','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(12348,'105','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(12348,'106','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(87275,'105','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(87275,'113','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(87272,'111','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(87272,'F103','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(91336,'F403','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(91336,'113','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(95601,'CIDI','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(95601,'CDS','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(95603,'113','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(95603,'105','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,VIERNES) values"+
                    "(12423,'CDS','07:00-09:00','07:00-10:00');");


//BLOQUE7 SECCION2

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(83504,'105','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(83504,'CC4','16:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,JUEVES) values"+
                    "(12345,'CDS','17:00-21:00','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(83506,'CC4','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(83506,'F103','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(83506,'105','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(83507,'111','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(83507,'105','13:00-15:00');");


            //BLOQUE 1 SECCION 1 TECO

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES) values"+
                    "(73282,'103','11:00-13:00','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(73283,'111','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(73283,'104','13:00-14:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(73283,'106','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(73285,'CC3','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,VIERNES) values"+
                    "(73285,'103','09:00-11:00','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,VIERNES) values"+
                    "(76830,'103','09:00-11:00','07:00-09:00');");


            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES,JUEVES) values"+
                    "(73288,'103','13:00-15:00','11:00-13:00','11:00-13:00');");



//BLOQUE 1 SECCION 2 TECO

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(73292,'CC2','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(73292,'102','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(73294,'103','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(73294,'102','19:00-21:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(73294,'106','19:00-20:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(73295,'104','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(73295,'103','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(73295,'CC2','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,JUEVES) values"+
                    "(73297,'103','17:00-19:00','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES,VIERNES) values"+
                    "(73298,'103','17:00-19:00','15:00-17:00','15:00-17:00');");

//BLOQUE 1 SECCION 3 TECO
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(81891,'111','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(81891,'112','14:00-16:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(81881,'113','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(81881,'106','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(81881,'112','13:00-14:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(81864,'106','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(81864,'113','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(81864,'CC2','13:00-15:00');");


//BLOQUE 3 SECCION 1 TECO
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(75655,'F402','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(75655,'CC2','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(75655,'103','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES) values"+
                    "(75667,'106','09:00-11:00','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(75667,'112','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES,VIERNES) values"+
                    "(75660,'112','09:00-11:00','11:00-12:00','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(75651,'112','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(75651,'103','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(75651,'111','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(75662,'CC3','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(75662,'112','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(75662,'111','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(97579,'112','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES,VIERNES) values"+
                    "(97579,'111','13:00-15:00','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(75776,'113','16:00-18:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(75776,'CC3','17:00-20:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(75774,'106','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(75774,'102','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(75774,'CC2','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(75775,'104','13:00-14:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(75775,'112','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(75775,'105','09:00-11:00');");


//BLOQUE 3 SECCION 2 TECO
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(75657,'112','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(75657,'FTC','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(75657,'CC1','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES) values"+
                    "(75668,'111','17:00-19:00','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(75668,'CC2','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(75661,'F102','13:00-16:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(75661,'111','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MIERCOLES,VIERNES) values"+
                    "(75653,'102','19:00-21:00','19:00-21:00','19:00-21:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(75664,'F102','16:00-18:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(75664,'111','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(75664,'113','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,JUEVES) values"+
                    "(97582,'103','19:00-21:00','19:00-21:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(97582,'112','17:00-19:00');");

//BLOQUE 5 SECCION 1 TECO
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(80630,'103','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(80630,'FTC','08:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(80633,'106','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(80633,'111','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(80634,'103','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES,VIERNES) values"+
                    "(80634,'106','08:00-09:00','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(80637,'CC2','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(80637,'F404','09:00-11:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(80637,'F402','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(80638,'CC3','07:00-09:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(80638,'106','07:00-08:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(80638,'111','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(80640,'CC4','11:00-14:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(80640,'FTC','11:00-13:00');");

//BLOQUE 5 SECCION 2 TECO
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES,VIERNES) values"+
                    "(80675,'FTC','14:00-15:00','13:00-15:00','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(80676,'106','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(80676,'F102','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(80679,'106','18:00-21:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(80679,'105','19:00-21:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(80680,'111','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES,VIERNES) values"+
                    "(80680,'F402','15:00-17:00','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(80685,'FTC','17:00-19:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(80685,'106','17:00-18:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(80685,'105','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(80688,'106','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(80688,'F402','14:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(80688,'CIDI','13:00-15:00');");

//BLOQUE 7 SECCION 1
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES) values"+
                    "(83570,'F102','09:00-11:00','07:00-10:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(83571,'F102','10:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(83571,'CC3','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(83572,'106','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(83572,'F102','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(83573,'105','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(83573,'F102','09:00-11:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES,VIERNES) values"+
                    "(83574,'F102','09:00-11:00','07:00-09:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(87590,'CC1','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(87590,'112','11:00-13:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,JUEVES) values"+
                    "(87608,'105','07:00-09:00','07:00-09:00');");

//BLOQUE 7 SECCION 2
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,MIERCOLES) values"+
                    "(83575,'F102','13:00-15:00','15:00-18:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(83576,'F402','15:00-16:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES) values"+
                    "(83576,'F102','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(83576,'FTC','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(83577,'CC1','13:00-15:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,MIERCOLES) values"+
                    "(83577,'F102','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(87591,'FTC','15:00-17:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(87591,'F102','13:00-15:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,JUEVES) values"+
                    "(85237,'F103','11:00-13:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(85237,'F102','15:00-17:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,MARTES,VIERNES) values"+
                    "(19564,'F402','17:00-19:00','17:00-19:00');");

            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES) values"+
                    "(20795,'F402','16:00-18:00');");
            database.execSQL("insert into Horario(IDNRC,IDSALON,VIERNES) values"+
                    "(20795,'FTC','15:00-17:00');");

//BLOQUE 7 SECCION 3 TECO
            database.execSQL("insert into Horario(IDNRC,IDSALON,LUNES,MARTES) values"+
                    "(98689,'F402','09:00-11:00','07:00-10:00');");


//REDES

            //Redes
              //      __________________

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, VIERNES) values" +
                    "(73238, '102', '07:00-09:00', '07:00-09:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MIERCOLES, VIERNES ) values" +
                    "(73239, '102', '09:00-11:00', '08:00-09:00', '09:00-11:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, MIERCOLES) values" +
                    "(73241, '102','09:00-11:00','07:00-09:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MIERCOLES, VIERNES) values" +
                    "(76731,'102', '11:00-13:00','11:00-13:00','11:00-13:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, VIERNES) values" +
                    "(73248, '102','13:00-15:00','13:00-15:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, MIERCOLES, JUEVES) values" +
                    "(73267, '102','15:00-17:00', '16:00-17:00', '15:00-17:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES, JUEVES) values" +
                    "(73269, '102',	'17:00-19:00', '17:00-19:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON,LUNES,VIERNES) values" +
                    "(73270,'102','17:00-19:00', '17:00-19:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MARTES, VIERNES) values" +
                    "(73271,'102', '15:00-17:00','13:00-15:00','15:00-17:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES) values" +
                    "(75723, '102','09:00-11:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES) values" +
                    "(75733, '102', '17:00-19:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES) values" +
                    "(75733, '103', '19:00-21:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES) values" +
                    "(75733, '104', '11:00-13:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, VIERNES) values" +
                    "(75720, '104', '08:00-09:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES) values" +
                    "(13451, '105', '14:00-15:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES) values" +
                    "(75723, '105', '09:00-11:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, VIERNES) values" +
                    "(75732, '105', '09:00-11:00', '11:00-12:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, JUEVES) values" +
                    "(95821, '105', '09:00-11:00', '11:00-13:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES) values" +
                    "(75720, '105', '07:00-09:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, JUEVES) values" +
                    "(75724, '105', '17:00-19:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES) values" +
                    "(13451, '106', '13:00-15:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, JUEVES) values" +
                    "(80693, 'CIDI', '07:00-09:00','07:00-09:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MARTES, VIERNES) values" +
                    "(83557, 'CIDI', '15:00-17:00','18:00-20:00', '15:00-17:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, VIERNES) values" +
                    "(83555, 'CIDI', '15:00-17:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, MIERCOLES) values" +
                    "(83561, 'CIDI', '15:00-18:00', '19:00-21:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES, VIERNES) values" +
                    "(83562, 'CIDI', '15:00-17:00', '15:00-20:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MIERCOLES) values" +
                    "(83566, 'CIDI', '09:00-11:00', '09:00-11:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, JUEVES) values" +
                    "(19469, 'CIDI', '19:00-21:00', '19:00-21:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, JUEVES) values" +
                    "(87503, 'CIDI', '15:00-17:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON,  MARTES) values" +
                    "(75720, 'CC1', '07:00-09:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES) values" +
                     "(80705, 'CC1', '07:00-09:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, JUEVES) values" +
                      "(73241, 'CC2', '09:00-11:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES) values" +
                       "(73269, 'CC2', '17:00-19:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES) values" +
                       "(13468, 'CC2', '09:00-11:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MARTES) values" +
                       "(76756, 'CC2', '13:00-15:00', '13:00-15:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, JUEVES, VIERNES) values" +
                       "(76733, 'CC2', '19:00-21:00', '19:00-21:00', '19:00-21:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES) values" +
                       "(95897, 'CC2', '19:00-21:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES  ) values" +
                       "(95821, 'CC3', '11:00-13:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES ) values" +
                       "(75723, 'CC3', '15:00-17:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES ) values" +
                       "(75721,'CC3', '17:00-19:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, JUEVES ) values" +
                       "(95897, 'CC3', '19:00-21:00', '19:00-21:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, VIERNES ) values" +
                       "(13451, 'F101', '13:00-15:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES ) values" +
                       "(13460, 'F101', '15:00-17:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES  ) values" +
                       "(13468, 'F101', '11:00-13:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES, JUEVES ) values" +
                       "(98993, 'F101', '13:00-15:00', '13:00-15:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, JUEVES ) values" +
                       "(75731, 'F101', '15:00-17:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES) values" +
                       "(80695, 'F101','13:00-15:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, VIERNES  ) values" +
                       "(80700, 'F101', '11:00-13:00', '11:00-13:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES  ) values" +
                       "(80705, 'F101', '07:00-10:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, VIERNES   ) values" +
                       "(83559, 'F101', '11:00-13:00', '11:00-13:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, JUEVES  ) values" +
                       "(83565, 'F101', '07:00-10:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MARTES, VIERNES) values" +
                       "(83567, 'F101', '11:00-13:00', '09:00-11:00', '09:00-11:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES) values" +
                       "(87503, 'F101', '15:00-17:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, MIERCOLES, VIERNES) values" +
                       "(87504, 'F101', '17:00-19:00', '17:00-19:00', '17:00-19:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, VIERNES) values" +
                       "(87507, 'F101', '07:00-09:00', '07:00-09:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, JUEVES  ) values" +
                        "(13434, 'F104', '15:00-17:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, JUEVES  ) values" +
                        "(75717, 'F104', '07:00-09:00', '07:00-09:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES ) values" +
                        "(75723, 'F104', '15:00-17:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, VIERNES ) values" +
                        "(75731, 'F104', '15:00-17:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MARTES, VIERNES  ) values" +
                        "(75733, 'F104', '12:00-13:00', '9:00-11:00', '11:00-12:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, VIERNES ) values" +
                        "(75720, 'F104', '17:00-18:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MARTES  ) values" +
                        "(75718, 'F104', '13:00-15:00', '13:00-15:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, JUEVES ) values" +
                        "(80695, 'F104', '13:00-15:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, VIERNES ) values" +
                        "(80697, 'F104', '09:00-11:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON,  MARTES, JUEVES) values" +
                        "(13460, 'F104', '15:00-17:00', '15:00-17:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES ) values" +
                        "(75721, 'F104', '17:00-19:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES) values" +
                        "(13434, 'LABRED', '17:00-19:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, VIERNES) values" +
                        "(75717, 'LABRED', '09:00-11:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MIERCOLES) values" +
                        "(75718, 'LABRED', '13:00-15:00')"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, MARTES, MIERCOLES) values" +
                        "(80697, 'LABRED', '09:00-11:00', '09:00-11:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MIERCOLES, VIERNES) values" +
                        "(80707, 'LABRED', '10:00-13:00', '11:00-13:00', '15:00-20:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, JUEVES, VIERNES) values" +
                        "(83555, 'LABRED', '14:00-17:00', '14:00-17:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MARTES) values" +
                        "(83558, 'LABRED', '13:00-15:00', '13:00-15:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON,  LUNES, VIERNES) values" +
                        "(95922, 'LABRED', '07:00-09:00', '07:00-09:00'	)"+";");

            database.execSQL("insert into Horario(IDNRC, IDSALON, LUNES, MIERCOLES, JUEVES) values" +
                        "(87500, 'LABRED', '15:00-17:00', '16:00-17:00', '17:00-19:00'	)"+";");

            Toast.makeText(this,"Descargando Datos...",Toast.LENGTH_SHORT).show();
            database.setTransactionSuccessful();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            database.endTransaction();
        }
    }
}