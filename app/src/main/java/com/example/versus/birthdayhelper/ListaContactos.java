package com.example.versus.birthdayhelper;

import android.Manifest;
import android.app.ListActivity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaContactos extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    ArrayList<Contacto> arrayContactos = new ArrayList();
    SQLiteDatabase db;
    ContactosDbHelper usdbh;
    ListView lvContactos;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        lvContactos = (ListView) findViewById(android.R.id.list);
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

    /*
    private void obtenerContactos(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        else{
            String[] proyeccion = new String[] {ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

            String selectionClause = ContactsContract.Data.MIMETYPE + "='" +
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                    + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";

            String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";

            Cursor mCursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, sortOrder);

            while(mCursor.moveToNext()){
                Log.d("Datos",Integer.valueOf(Data._ID) + "  " + ContactsContract.Data.DISPLAY_NAME + "  " +Phone.NUMBER);
                int id = Integer.valueOf(Data._ID);
                String nombre = ContactsContract.Data.DISPLAY_NAME;
                String numero = Phone.NUMBER;
                Contacto contacto = new Contacto(id, nombre, numero, null, '\u0000', null);
                usdbh.insert(db, contacto);
            }
        }
    }*/

    public void obtenerContactos() {
        String[] projeccion = new String[]{ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE};
        String selectionClause = ContactsContract.Data.MIMETYPE + "='" +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";
        String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";

        Cursor c = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                projeccion,
                selectionClause,
                null,
                sortOrder);
        while (c.moveToNext()) {
            Log.d("Datos", "Identificador: " + c.getString(0) + " Nombre: " + c.getString(1) + " Número: " + c.getString(2) + " Tipo: " + c.getString(3));
            Contacto contacto = new Contacto(Integer.valueOf(c.getString(0)), c.getString(1), c.getString(2), null, '\u0000', null);
            usdbh.insert(db, contacto);
        }
        c.close();
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                obtenerContactos();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

