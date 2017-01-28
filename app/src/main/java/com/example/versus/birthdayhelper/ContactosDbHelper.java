package com.example.versus.birthdayhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;


public class ContactosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;//Contexto de acción para el helper.
    public static final String DATABASE_NAME = "miscumples.db";//Nombre del archivo con extensión .db

    //Sentencia SQL que crea la tabla
    String sqlCreate = "CREATE TABLE " + ContactosContract.ContactoEntry.TABLE_NAME + " ("
            + ContactosContract.ContactoEntry.ID + " INTEGER PRIMARY KEY NOT NULL,"
            + ContactosContract.ContactoEntry.TIPONOTIF + " CHAR(1) NOT NULL,"
            + ContactosContract.ContactoEntry.MENSAJE + " VARCHAR(160) NOT NULL,"
            + ContactosContract.ContactoEntry.TELEFONO + " VARCHAR(15) NOT NULL,"
            + ContactosContract.ContactoEntry.FECHANACIMIENTO + " VARCHAR(15) NULL,"
            + ContactosContract.ContactoEntry.NOMBRE + " VARCHAR(128) NOT NULL);";

    /**
     * Constructor de la clase ContactosDbHelper
     * @param context Context de la activity donde es llamado
     */
    public ContactosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Metodo sobrescrito que ejecuta  la sentencia sql de la creacion de la Base de Datos SQLite
     * @param db Objeto de la Base de Datos
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    /**
     * Metodo sobrescrito que se utiliza cuando se actualiza la BD
     * @param db Objeto de la Base de Datos.
     * @param oldVersion Version antigua
     * @param newVersion Version nueva
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contactos");
        db.execSQL(sqlCreate);
    }

    /**
     * Metodo que permite insertar un Contacto en la Base de Datos
     * @param db Objeto de la Base de Datos.
     * @param oContacto Objeto de la clase Contacto.
     */
    public void insert(SQLiteDatabase db, Contacto oContacto) {
        //Sentencia SQL  que inserta un contacto
        String sqlInsert = "INSERT INTO " + ContactosContract.ContactoEntry.TABLE_NAME + " VALUES("
                + oContacto.getId()
                + ",'n', "
                + "'Feliz Cumpleaños!', '"
                + oContacto.getTelefono()
                + "', null, '"
                + oContacto.getNombre() + "')";
        try {
            db.execSQL(sqlInsert);//Se ejecuta la sentencia creada anteriormente
        }catch(SQLiteException e){
            //En el caso de que se exista el contacto (Excepcion al intentar duplicar una PRIMARY KEY)
            //Se actualiza el contacto
            updateContact(db, oContacto);
        }
    }

    /**
     * Metodo que actualiza un contacto de la Base de Datos
     * @param db Objeto de la Base de Datos.
     * @param oContacto Objeto de la clase Contacto.
     */
    public void updateContact(SQLiteDatabase db, Contacto oContacto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactosContract.ContactoEntry.NOMBRE, oContacto.getNombre());
        contentValues.put(ContactosContract.ContactoEntry.TELEFONO, oContacto.getTelefono());
        db.update(ContactosContract.ContactoEntry.TABLE_NAME, contentValues, ContactosContract.ContactoEntry.ID + " = ?", new String[]{String.valueOf(oContacto.getId())});
    }

    /**
     * Metodo que obtiene los contactos de la Base de Datos y los guarda en un ArrayList de Contactos.
     * @param db Objeto de la Base de Datos.
     * @return Devuelve un ArrayList de Contactos.
     */
    public ArrayList<Contacto> cargarContactos(SQLiteDatabase db){
        ArrayList<Contacto> arrayContactos = new ArrayList();

        String[] campos = new String[] {"id", "tipoNotif", "mensaje","telefono", "fechaNacimiento", "nombre"};
        Cursor c = db.query("contactos", campos, null, null, null, null, null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                //Contacto(int id,String nombre, String telefono, String fechaNacimiento, char tipoNotif, String mensaje)
                Contacto contacto = new Contacto(c.getInt(0),c.getString(5), c.getString(3), c.getString(4), c.getString(1).charAt(0), c.getString(2));
                arrayContactos.add(contacto);
            } while(c.moveToNext());
        }
        return arrayContactos;
    }
}
