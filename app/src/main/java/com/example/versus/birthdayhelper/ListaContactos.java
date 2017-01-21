package com.example.versus.birthdayhelper;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class ListaContactos extends ListActivity{

    ArrayList<Contacto> arrayContactos = new ArrayList();
    SQLiteDatabase db;
    ContactosDbHelper usdbh;
    ListView lvContactos;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listacontactos);

        lvContactos = (ListView) findViewById(R.id.lvContactos);
        usdbh = new ContactosDbHelper(this);
        db = usdbh.getWritableDatabase();

        if(db != null){
            Log.d("BD","Se ha abierto correctamente");
        } else{
            Log.d("BD","No se ha abierto correctamente");
        }
        obtenerContactos();
        arrayContactos = usdbh.cargarContactos(db);

        ContactoAdapter adaptador = new ContactoAdapter(this, arrayContactos); //Constructor del adaptador de la lista

        lvContactos.setAdapter(adaptador); //Se le asigna el adaptador a la lista
    }

    private void obtenerContactos(){
        String[] proyeccion = new String[] {ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        String selectionClause = ContactsContract.Data.MIMETYPE + "='" +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";

        String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";

        Cursor mCursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, //URI de contenido para los contactos
                proyeccion,
                selectionClause,
                null,
                sortOrder);

        while(mCursor.moveToNext()){
            Contacto contacto = new Contacto(mCursor.getString(1), mCursor.getLong(2), null, '\u0000', null);
            usdbh.insert(db, contacto);
        }
    }
}

