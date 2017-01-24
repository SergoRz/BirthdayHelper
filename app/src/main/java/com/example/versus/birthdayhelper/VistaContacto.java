package com.example.versus.birthdayhelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class VistaContacto extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private Contacto contacto;
    private EditText etNombre;
    private CheckBox cbSMS;
    private EditText etMensaje;
    private EditText etTelf;
    private Uri contactUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistacontacto);

        etNombre = (EditText) findViewById(R.id.etNombre);
        cbSMS = (CheckBox) findViewById(R.id.cbSMS);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        etTelf = (EditText) findViewById(R.id.etTelf);

        Bundle extras = getIntent().getExtras();
        contacto = extras.getParcelable("contacto");

        etNombre.setText(contacto.getNombre());
        etTelf.setText(contacto.getTelefono());

        if(contacto.getTipoNotif() == 's'){
            cbSMS.setChecked(true);
        }

        etMensaje.setText(contacto.getMensaje());
    }


    public void selectContact(View v) {
        //String uri = "content://com.android.contacts/contacts" + etNombre.getText().toString();
        //LA CONTACT_ID NO SE CORRESPONDE CON _ID, EN LA URI CREO QUE BUSCA POR CONTACT_ID
        Uri dato = Uri.parse("content://com.android.contacts/contacts/"  + String.valueOf(contacto.getId()));
        Log.d("Contacto a buscar: ",String.valueOf(contacto.getId()));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(dato);
        startActivity(intent);
    }


}
