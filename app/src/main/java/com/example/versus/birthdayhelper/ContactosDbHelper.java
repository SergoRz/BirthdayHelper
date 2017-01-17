package com.example.versus.birthdayhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;//Contexto de acción para el helper.
    public static final String DATABASE_NAME = "miscumples.db";//Nombre del archivo con extensión .db

    public ContactosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + ContactosContract.ContactoEntry.TABLE_NAME + " ("
                + ContactosContract.ContactoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ContactosContract.ContactoEntry.ID + " INTEGER NOT NULL,"
                + ContactosContract.ContactoEntry.TIPONOTIF + " CHAR(1) NOT NULL,"
                + ContactosContract.ContactoEntry.MENSAJE + " VARCHAR(160) NOT NULL,"
                + ContactosContract.ContactoEntry.TELEFONO + " VARCHAR(15) NOT NULL,"
                + ContactosContract.ContactoEntry.FECHANACIMIENTO + " VARCHAR(15) NOT NULL,"
                + ContactosContract.ContactoEntry.NOMBRE + " VARCHAR(128),"
                + "UNIQUE (" + ContactosContract.ContactoEntry.ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Usuarios");

        sqLiteDatabase.execSQL("CREATE TABLE " + ContactosContract.ContactoEntry.TABLE_NAME + " ("
                + ContactosContract.ContactoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ContactosContract.ContactoEntry.ID + " INTEGER NOT NULL,"
                + ContactosContract.ContactoEntry.TIPONOTIF + " CHAR(1) NOT NULL,"
                + ContactosContract.ContactoEntry.MENSAJE + " VARCHAR(160) NOT NULL,"
                + ContactosContract.ContactoEntry.TELEFONO + " VARCHAR(15) NOT NULL,"
                + ContactosContract.ContactoEntry.FECHANACIMIENTO + " VARCHAR(15) NOT NULL,"
                + ContactosContract.ContactoEntry.NOMBRE + " VARCHAR(128),"
                + "UNIQUE (" + ContactosContract.ContactoEntry.ID + "))");
    }
}
