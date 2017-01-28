package com.example.versus.birthdayhelper;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public class ListaContactos extends AppCompatActivity {

    //Alarma alarma = new Alarma(this);
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int PERMISSIONS_REQUEST_SEND_SMS = 100;
    private static final int PERMISSIONS_REQUEST_WAKE_LOCK = 100;
    ArrayList<Contacto> arrayContactos; //ArrayList de contactos
    static SQLiteDatabase db; //Base de datos de los contactos
    ContactosDbHelper usdbh; //Clase que se encarga de la base de datos
    ListView lvContactos; //ListView de la lista de contactos
    ContactoAdapter adaptador; //Adaptador del ListView

    /**
     * Metodo que se ejecuta al iniciar la Activity
     * Carga los contactos del telefono en la base de datos y despues carga el ListView de contactos desde la base
     * de datos.
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        lvContactos = (ListView) findViewById(R.id.lvContactos); //Se enlaza el objeto ListView con el del layout
        usdbh = new ContactosDbHelper(this); //Se instancia la clase y se le pasa el contacto

        db = usdbh.getWritableDatabase(); //Se obtiene la base de datos de la clase ContactosDbHelper

        obtenerContactos(); //Se obtienen los contactos del telefono
        arrayContactos = new ArrayList<Contacto>(); //Se instancia el ArrayList de contactos
        //Se rellena el ArrayList de contactos con los contactos de la base de datos
        arrayContactos = usdbh.cargarContactos(db);

        adaptador = new ContactoAdapter(this, arrayContactos); //Constructor del adaptador de la lista

        lvContactos.setAdapter(adaptador); //Se le asigna el adaptador a la lista
        //Listener para cuando se haga click sobre un contacto de la lista
        lvContactos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            //Cuando se haga click en un contacto de la lista..
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id){
               verContacto(position); //Se abre un intent con los detalles del contacto
            }
        });

        pedirPermisos();
        //alarma.setAlarma(14, 02); //Se establece la alarma a las 00:00
    }

    /**
     * Metodo que se encarga de cargar los contactos del telefono en la base de datos SQLite.
     * Para ello es necesario pedir permisos al usuario en tiempo de ejecucion.
     * Una vez dados los permisos se crea un cursor, el cual va a recorrer los contactos, y los va
     * a introducir en la base de datos,
     */
    private void obtenerContactos(){
        // Si no hay permiso de leer los contactos..
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //Se espera el callback del metodo onRequestPermissionsResult(int, String[], int[])
        }
        else{ //Si hay permiso para leer los contactos...

            //Permite seleccionar en un Array los atributos que queremos recibir de cada contacto, para ello se utiliza la clase ContactsContract
            String[] projeccion = new String[]{ContactsContract.Data.CONTACT_ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

            //Condicion de busqueda
            String selectionClause = ContactsContract.Data.MIMETYPE + "='" +
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                    + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";

            //Permite ordenar los contactos por orden ascendente
            String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";

            //Se crea el cursor que va a recorrer los contactos
            Cursor c = getContentResolver().query(ContactsContract.Data.CONTENT_URI, projeccion, selectionClause, null, sortOrder);

            while (c.moveToNext()) { //Mientras haya contactos...
                //Se crea un nuevo contactos con los valores recogidos
                Contacto contacto = new Contacto(Integer.valueOf(c.getString(0)), c.getString(1), c.getString(2), null, '\u0000', null);

                usdbh.insert(db, contacto); //Se inserta el contacto en la base de datos
            }
            c.close(); //Se cierra el cursor
        }
    }

    /**
     * Metodo que se ejecuta cuando hacemos clic en uno de los contactos de la lista
     * El metodo nos lleva a otra Activity (VistaContacto) donde se ven los detalles del contacto
     * @param position Posicion del contacto en el ListView
     */
    public void verContacto(int position){
        // Intent hacia la nueva Activity
        Intent intent = new Intent(this, VistaContacto.class);
        //Seleccionamos el contacto que elegimos
        Contacto contacto = arrayContactos.get(position);
        //Pasamos el contacto a la nueva activity
        intent.putExtra("contacto", contacto);
        //Se inicia la activity
        startActivity(intent);
    }


    /**
     * Metodo que se encarga de abrir el Dialog para cambiar la hora
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void cambiarHora(){
        DialogFragment newFragment = new Timer(); //Se crea el DialogFragment
        newFragment.show(getFragmentManager(),"TimePicker"); //Se muesrta el DialogFragment
    }

    /**
     * Metodo que se ejecuta cuando es necesario dar permisos a la aplicacion.
     * Se ejecuta para dar permisos de lectura de contactos.
     * @param requestCode Codigo del permiso que se solicita
     * @param permissions Permisos solicitados
     * @param grantResults Resultado que se obtiene por parte del usuario
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) { //Si se solicita leer los contactos..
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { //Si el usuario permite la lectura de contactos..
                obtenerContactos(); //Se obtienen los contactos
            } else { //Si no..
                //Se informa al usuario
                Toast.makeText(this, "Hasta que no aceptes los permisos no podremos mostrar los contactos", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == PERMISSIONS_REQUEST_SEND_SMS) { //Si se solicita mandar mensajes...
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Hasta que no aceptes los permisos no podremos enviar SMS automaticamente", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Metodo sobrescrito que carga el menu de la parte superior de la pantalla
     * Carga el layout que se ha descrito en la carpeta menu, dentro de la carpeta res
     * Incorpora un solo item, que se depliega y ofrece un menu con la opcion de cambiar la hora
     * de notificacion.
     * @param menu Menu de la actionbar.
     * @return Devuelve true o false si se ha ejecutado bien la operacion
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflamos el menú; añadimos los items al action bar.
        MenuInflater infladorMenu = getMenuInflater();
        infladorMenu.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Metodo que se realiza al hacer clic sobre una de las opciones del menu del action bar
     * La unica opcion posible es la de cambiar la hora, al hacer clic en ella se ejecuta
     * el metodo cambiarHora
     * @param item Item sobre el que se hace clic
     * @return Devuelve true o false si se ha ejecutado bien la operacion
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings: //Si coincide con el boton de "Configurar Felicitaciones"..
                cambiarHora(); //Se abre el dialog para cambiar la hora
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void pedirPermisos(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SEND_SMS);
            //Se espera el callback del metodo onRequestPermissionsResult(int, String[], int[])
        }
    }
}

