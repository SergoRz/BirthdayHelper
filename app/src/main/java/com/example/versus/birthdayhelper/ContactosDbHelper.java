package com.example.versus.birthdayhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Versus on 17/01/2017.
 */

public class ContactosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;//Contexto de acción para el helper.
    public static final String DATABASE_NAME = "miscumples.db";//Nombre del archivo con extensión .db

    public ContactosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + ContactosEntry.TABLE_NAME + " ("
                + ContactosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ContactosEntry.ID + " TEXT NOT NULL,"
                + ContactosEntry.NAME + " TEXT NOT NULL,"
                + ContactosEntry.SPECIALTY + " TEXT NOT NULL,"
                + ContactosEntry.PHONE_NUMBER + " TEXT NOT NULL,"
                + ContactosEntry.BIO + " TEXT NOT NULL,"
                + ContactosEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + ContactosEntry.ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
