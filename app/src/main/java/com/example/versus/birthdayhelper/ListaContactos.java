package com.example.versus.birthdayhelper;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * Created by EmilioCB on 18/01/2017.
 */

public class ListaContactos extends ListActivity{

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listacontactos);


    }

    private void obtenerContactos(){
        String[] proyeccion = new String[] {ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER, Phone.TYPE};

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
    }
}
