package com.example.versus.birthdayhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by EmilioCB on 21/01/2017.
 */

public class ContactoAdapter extends ArrayAdapter<Contacto> {
    private final Context contexto;
    private final ArrayList<Contacto> array_contactos;

    /**
     * Constructor de la clase
     * @param context Contexto de la Activity
     * @param array_contactos ArrayList de los contactos
     * @param array_contactos
     */
    public ContactoAdapter(Context context, ArrayList<Contacto> array_contactos) {
        super(context, -1, array_contactos);
        this.contexto = context;
        this.array_contactos = array_contactos;
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

        View layoutTitular = inflater.inflate(R.layout.fila, parent, false); //Instancia el layout personalizado(fila) en esta Vista.

        TextView tituloTextView = (TextView) layoutTitular.findViewById(R.id.textViewTitulo); //TextView del titulo, se asocia con el del XML
        TextView subtitulo = (TextView) layoutTitular.findViewById(R.id.textViewSubtitulo); //TextView del subtitulo, se asocia con el del XML

        Titular titularActual = array_titulares.get(position); //Se recorren los titulares

        tituloTextView.setText(titularActual.getTitulo()); //Se recoge el titulo del titular en el que se encuentra
        subtitulo.setText(titularActual.getSubtitulo()); //Se recoge el subtitulo del titular en el que se encuentra

        return layoutTitular;
    }
}
