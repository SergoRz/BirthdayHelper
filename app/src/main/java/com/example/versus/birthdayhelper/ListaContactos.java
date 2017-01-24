package com.example.versus.birthdayhelper;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public class ListaContactos extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    ArrayList<Contacto> arrayContactos;
    SQLiteDatabase db;
    ContactosDbHelper usdbh;
    TextView tvBusqueda;
    ListView lvContactos;
    ContactoAdapter adaptador;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        lvContactos = (ListView) findViewById(R.id.lvContactos);
        lvContactos.setTextFilterEnabled(true);

        tvBusqueda = (TextView) findViewById(R.id.tvBusqueda);

        usdbh = new ContactosDbHelper(this);
        db = usdbh.getWritableDatabase();

        obtenerContactos();
        arrayContactos = new ArrayList();
        arrayContactos = usdbh.cargarContactos(db);




        adaptador = new ContactoAdapter(this, arrayContactos); //Constructor del adaptador de la lista

        lvContactos.setAdapter(adaptador); //Se le asigna el adaptador a la lista
        for(int i = 0; i < arrayContactos.size(); i++){
            Log.d("Contacto", arrayContactos.get(i).toString());
        }
        lvContactos.setOnItemClickListener(new AdapterView.OnItemClickListener(){ //Listener para cuando se haga click sobre un item de la lista
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id){ //Cuando se haga click en un item de la lista..
               verContacto(position);
            }
        });

        tvBusqueda.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                adaptador.getFilter().filter(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }


    private void obtenerContactos(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        else{
           String[] projeccion = new String[]{ContactsContract.Data.CONTACT_ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE};
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
            Contacto contacto = new Contacto(Integer.valueOf(c.getString(0)), c.getString(1), c.getString(2), null, '\u0000', null);
            usdbh.insert(db, contacto);
        }
        c.close();
        }
    }

    public void verContacto(int position){
        Intent intent = new Intent(this, VistaContacto.class);
        Contacto contacto = arrayContactos.get(position);
        intent.putExtra("contacto", contacto);
        startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                obtenerContactos();
            } else {
                Toast.makeText(this, "Hasta que no aceptes los permisos no podremos mostrar los contactos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflamos el menú; añadimos los items al action bar si éste está presente.
        MenuInflater infladorMenu = getMenuInflater();
        infladorMenu.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void cargarPreferencias(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        String horaMensaje = prefs.getString("horaMensaje", "00:00");
    }

    public void cambiarHora(){
        DialogFragment newFragment = new Timer();
        newFragment.show(getFragmentManager(),"TimePicker");
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                cambiarHora();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

