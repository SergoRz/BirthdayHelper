package com.example.versus.birthdayhelper;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class VistaContacto extends AppCompatActivity {

    private Contacto contacto;
    private EditText etNombre;
    private CheckBox cbSMS;
    private EditText etMensaje;
    private EditText etTelf;
    private EditText etFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistacontacto);

        etNombre = (EditText) findViewById(R.id.etNombre);
        cbSMS = (CheckBox) findViewById(R.id.cbSMS);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        etTelf = (EditText) findViewById(R.id.etTelf);
        etFecha = (EditText) findViewById(R.id.etFecha);

        Bundle extras = getIntent().getExtras();
        contacto = extras.getParcelable("contacto");

        etNombre.setText(contacto.getNombre());
        etTelf.setText(contacto.getTelefono());
        etFecha.setText(contacto.getFechaNacimiento());

        if(contacto.getTipoNotif() == 's'){
            cbSMS.setChecked(true);
        }

        etMensaje.setText(contacto.getMensaje());
    }

    public void selectContact(View v) {
        Uri dato = Uri.parse("content://com.android.contacts/contacts/"  + String.valueOf(contacto.getId()));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(dato);
        startActivity(intent);
    }

    public void cambiarFecha(View v){
        DialogFragment newFragment = new CalendarPicker();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    public void guardarContact(View v){
        ContactosDbHelper usdbh = new ContactosDbHelper(this);
        SQLiteDatabase db = usdbh.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactosContract.ContactoEntry.FECHANACIMIENTO, etFecha.getText().toString());

        String not;
        if(cbSMS.isChecked()){
            not = "s";
        }
        else{
            not = "n";
        }
        contentValues.put(ContactosContract.ContactoEntry.MENSAJE, etMensaje.getText().toString());

        contentValues.put(ContactosContract.ContactoEntry.TIPONOTIF, not);
        db.update(ContactosContract.ContactoEntry.TABLE_NAME, contentValues, ContactosContract.ContactoEntry.ID + " = ?", new String[]{String.valueOf(contacto.getId())});
    }



}
