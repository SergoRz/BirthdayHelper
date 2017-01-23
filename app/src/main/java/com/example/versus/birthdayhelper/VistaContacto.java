package com.example.versus.birthdayhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

public class VistaContacto extends AppCompatActivity {

    private Contacto contacto;
    private EditText etNombre;
    private CheckBox cbSMS;
    private EditText etMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistacontacto);

        etNombre = (EditText) findViewById(R.id.etNombre);
        cbSMS = (CheckBox) findViewById(R.id.cbSMS);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        
        Bundle extras = getIntent().getExtras();
        contacto = extras.getParcelable("contacto");


        etNombre.setText(contacto.getNombre());

        if(contacto.getTipoNotif() == 's'){
            cbSMS.setChecked(true);
        }

        etMensaje.setText(contacto.getMensaje());

    }
}
