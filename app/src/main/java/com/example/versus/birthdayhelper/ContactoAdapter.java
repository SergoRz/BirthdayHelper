package com.example.versus.birthdayhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by EmilioCB on 21/01/2017.
 */

public class ContactoAdapter extends ArrayAdapter<Contacto> {

    private final Context contexto;
    private final ArrayList<Contacto> arrayContactos;

    /**
     * Constructor de la clase
     * @param context Contexto de la Activity
     * @param arrayContactos ArrayList de los contactos
     */
    public ContactoAdapter(Context context, ArrayList<Contacto> arrayContactos) {
        super(context, R.layout.tuplacontacto, arrayContactos);
        this.contexto = context;
        this.arrayContactos = arrayContactos;
    }

    /**
     * Metodo que se encarga de darle una disposicion de layout a la view.
     * @param position Posicion del ArrayList en la que se encuentra
     * @param convertView
     * @param parent
     * @return Devuelve la disposicion de la view
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Se crea el LayoutInflater

        View layoutContacto = inflater.inflate(R.layout.tuplacontacto, parent, false); //Instancia el layout personalizado(fila) en esta Vista.

        ImageView ivFoto = (ImageView) layoutContacto.findViewById(R.id.imageViewFoto);
        TextView tvNombre = (TextView) layoutContacto.findViewById(R.id.textViewNombre); //TextView del titulo, se asocia con el del XML
        TextView tvTelefono = (TextView) layoutContacto.findViewById(R.id.textViewNumero); //TextView del subtitulo, se asocia con el del XML
        TextView tvAviso = (TextView) layoutContacto.findViewById(R.id.textViewAviso);

        Contacto contactoActual = arrayContactos.get(position); //Se recorren los titulares

        tvNombre.setText(contactoActual.getNombre()); //Se recoge el titulo del titular en el que se encuentra
        tvTelefono.setText(String.valueOf(contactoActual.getTelefono())); //Se recoge el subtitulo del titular en el que se encuentra

        if(contactoActual.getTipoNotif() == 'n') tvAviso.setText("Aviso: Solo notificación");
        else tvAviso.setText("Aviso: Enviar SMS");

        return layoutContacto;
    }
}
