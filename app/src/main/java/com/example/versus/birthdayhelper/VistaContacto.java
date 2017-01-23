package com.example.versus.birthdayhelper;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class VistaContacto extends AppCompatActivity {

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
        contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, etNombre.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, contactUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
