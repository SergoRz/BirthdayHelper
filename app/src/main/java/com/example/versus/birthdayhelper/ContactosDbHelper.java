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

    String sqlCreate = "CREATE TABLE " + ContactosContract.ContactoEntry.TABLE_NAME + " ("
            + ContactosContract.ContactoEntry.ID + " INTEGER PRIMARY KEY NOT NULL,"
            + ContactosContract.ContactoEntry.TIPONOTIF + " CHAR(1) NOT NULL,"
            + ContactosContract.ContactoEntry.MENSAJE + " VARCHAR(160) NOT NULL,"
            + ContactosContract.ContactoEntry.TELEFONO + " VARCHAR(15) NOT NULL,"
            + ContactosContract.ContactoEntry.FECHANACIMIENTO + " VARCHAR(15) NULL,"
            + ContactosContract.ContactoEntry.NOMBRE + " VARCHAR(128) NOT NULL);";

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

    public void insert(SQLiteDatabase db, Contacto oContacto) {
        String sqlInsert = "INSERT INTO " + ContactosContract.ContactoEntry.TABLE_NAME + " VALUES("
                + oContacto.getId()
                + ",'n', "
                + "'Feliz Cumpleaños!', '"
                + oContacto.getTelefono()
                + "', null, '"
                + oContacto.getNombre() + "')";
        try {
            db.execSQL(sqlInsert);
        }catch(SQLiteException e){
            updateContact(db, oContacto);
        }
    }

    public void updateContact(SQLiteDatabase db, Contacto oContacto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactosContract.ContactoEntry.NOMBRE, oContacto.getNombre());
        contentValues.put(ContactosContract.ContactoEntry.TELEFONO, oContacto.getTelefono());
        db.update(ContactosContract.ContactoEntry.TABLE_NAME, contentValues, ContactosContract.ContactoEntry.ID + " = ?", new String[]{String.valueOf(oContacto.getId())});
    }

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
