package com.example.versus.birthdayhelper;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Activity que muestra la informacion de un contacto, permite cambiar su fecha de cumpleaños y el mensaje
 * de felicitacion, asi como acceder al contacto en la aplicacion de Contactos del dispositivo.
 */
public class VistaContacto extends AppCompatActivity {

    private Contacto contacto; //Contacto que se visualiza
    private EditText etNombre; //Nombre del contacto
    private CheckBox cbSMS; //CheckBox donde se indica si se le envia un SMS o no
    private EditText etMensaje; //Mensaje que se le envia
    private EditText etTelf; //Telefono del contacto
    private EditText etFecha; //Fecha de cumpleaños del contacto

    /**
     * Metodo que se ejecuta al iniciar la activity, su funcion es cargar la informacion del contacto
     * seleccionado.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistacontacto);

        //Se asocian los objetos de codigo con los del layout
        etNombre = (EditText) findViewById(R.id.etNombre);
        cbSMS = (CheckBox) findViewById(R.id.cbSMS);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        etTelf = (EditText) findViewById(R.id.etTelf);
        etFecha = (EditText) findViewById(R.id.etFecha);

        //Se recoge el objeto contacto seleccionado a traves del Bundle
        Bundle extras = getIntent().getExtras();
        contacto = extras.getParcelable("contacto");

        //Se establece el nombre, el telefono, la fecha de nacimiento, el mensaje y el tipo de
        //notificacion del contacto en su EditText correspondiente
        etNombre.setText(contacto.getNombre());
        etTelf.setText(contacto.getTelefono());
        etFecha.setText(contacto.getFechaNacimiento());

        if(contacto.getTipoNotif() == 's'){
            cbSMS.setChecked(true);
        }

        etMensaje.setText(contacto.getMensaje());
    }

    /**
     * Metodo que se encarga de abrir el contacto en la aplicacion de contactos del dispositivo
     * @param v Vista
     */
    public void selectContact(View v) {
        //Se obtiene la uri del contacto
        Uri dato = Uri.parse("content://com.android.contacts/contacts/"  + String.valueOf(contacto.getId()));
        Intent intent = new Intent(Intent.ACTION_VIEW); //Se crea el nuevo intent
        intent.setData(dato); //Se interta la uri del contacto
        startActivity(intent);//Se inicia el intent
    }

    /**
     * Metodo que se ejecuta al hacer clic en el boton cambiar fecha
     * Crea un DialogFragment para introducit la nueva fecha de nacimiento
     * @param v Vista
     */
    public void cambiarFecha(View v){
        //Se crea el DialogFragment para seleccionar la fecha
        DialogFragment newFragment = new CalendarPicker();
        //Se muestra el DialogFragment
        newFragment.show(getFragmentManager(), "datePicker");
    }


    /**
     * Metodo que se encarga de actualizar la informacion del contacto en la base de datos
     * @param v Vista
     */
    public void guardarContact(View v){
        //Se instancia la clase ContactosDbHelper y SQLiteDatabase para actualizar la informacion
        ContactosDbHelper usdbh = new ContactosDbHelper(this);
        SQLiteDatabase db = usdbh.getWritableDatabase();

        ContentValues contentValues = new ContentValues(); //Se isntancian unos nuevos valores
        //Se establece la fecha de nacimiento
        contentValues.put(ContactosContract.ContactoEntry.FECHANACIMIENTO, etFecha.getText().toString());

        //Se establece el tipo de notificacion
        String not;
        if(cbSMS.isChecked()){
            not = "s";
        }
        else{
            not = "n";
        }
        contentValues.put(ContactosContract.ContactoEntry.TIPONOTIF, not);

        //Se establece el mensaje de felicitacion
        contentValues.put(ContactosContract.ContactoEntry.MENSAJE, etMensaje.getText().toString());
        //Se actualizan los valores en la base de datos
        db.update(ContactosContract.ContactoEntry.TABLE_NAME, contentValues, ContactosContract.ContactoEntry.ID
                + " = ?", new String[]{String.valueOf(contacto.getId())});
    }



}
