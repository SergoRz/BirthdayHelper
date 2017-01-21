package com.example.versus.birthdayhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Date;

public class ContactosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;//Contexto de acción para el helper.
    public static final String DATABASE_NAME = "miscumples.db";//Nombre del archivo con extensión .db

    String sqlCreate = "CREATE TABLE " + ContactosContract.ContactoEntry.TABLE_NAME + " ("
            + ContactosContract.ContactoEntry._ID + " INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT,"
            + ContactosContract.ContactoEntry.TIPONOTIF + " CHAR(1) NOT NULL,"
            + ContactosContract.ContactoEntry.MENSAJE + " VARCHAR(160) NOT NULL,"
            + ContactosContract.ContactoEntry.TELEFONO + " VARCHAR(15) NOT NULL,"
            + ContactosContract.ContactoEntry.FECHANACIMIENTO + " VARCHAR(15) NULL,"
            + ContactosContract.ContactoEntry.NOMBRE + " VARCHAR(128) NOT NULL)";

    public ContactosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS contactos");

        db.execSQL(sqlCreate);
    }

    public void insert(SQLiteDatabase db, Contacto oContacto){
        String sqlInsert = "INSERT INTO " + ContactosContract.ContactoEntry.TABLE_NAME + "VALUES("
                + ", n, " +
                "'Feliz Cumpleaños!',"
                + oContacto.getTelefono() + ", ,"
                + oContacto.getNombre() + ");";

        db.execSQL(sqlInsert);
    }

    public ArrayList<Contacto> cargarContactos(SQLiteDatabase db){
        ArrayList<Contacto> arrayContactos = new ArrayList();

        String[] campos = new String[] {"tipoNotif", "mensaje","telefono", "fechaNacimiento", "nombre"};
        Cursor c = db.query("Usuarios", campos, "null", null, null, null, null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                Contacto contacto = new Contacto(c.getString(1), c.getLong(2), c.getString(3), c.getString(4).charAt(0), c.getString(5));
                arrayContactos.add(contacto);
            } while(c.moveToNext());
        }
        return arrayContactos;
    }
}
