package com.example.versus.birthdayhelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.*;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



public class ContactoAdapter extends ArrayAdapter<Contacto> implements Filterable {

    private final Context contexto;
    private ArrayList<Contacto> arrayContactos;
    private ArrayList<Contacto> arrayContactosFiltrado;

    /**
     * Constructor de la clase
     * @param context Contexto de la Activity
     * @param arrayContactos ArrayList de los contactos
     */
    public ContactoAdapter(Context context, ArrayList<Contacto> arrayContactos) {
        super(context, R.layout.tuplacontacto, arrayContactos);
        this.contexto = context;
        this.arrayContactos = arrayContactos;
        this.arrayContactosFiltrado = arrayContactos;
    }

    /**
     * Metodo que se encarga de darle una disposicion de layout a la view.
     * @param position Posicion del ArrayList en la que se encuentra
     * @param convertView
     * @param parent
     * @return Devuelve la disposicion de la view
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View layoutContacto = null;

        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Se crea el LayoutInflater

        layoutContacto = inflater.inflate(R.layout.tuplacontacto, parent, false); //Instancia el layout personalizado(fila) en esta Vista.
        ImageView ivFoto = (ImageView) layoutContacto.findViewById(R.id.imageViewFoto);
        TextView tvNombre = (TextView) layoutContacto.findViewById(R.id.textViewNombre); //TextView del titulo, se asocia con el del XML
        TextView tvTelefono = (TextView) layoutContacto.findViewById(R.id.textViewNumero); //TextView del subtitulo, se asocia con el del XML
        TextView tvAviso = (TextView) layoutContacto.findViewById(R.id.textViewAviso);

        Contacto contactoActual = arrayContactosFiltrado.get(position); //Se recorren los titulares

        tvNombre.setText(contactoActual.getNombre()); //Se recoge el titulo del titular en el que se encuentra
        tvTelefono.setText(String.valueOf(contactoActual.getTelefono())); //Se recoge el subtitulo del titular en el que se encuentra

        if (contactoActual.getTipoNotif() == 'n') tvAviso.setText("Aviso: Solo notificación");
        else tvAviso.setText("Aviso: Enviar SMS");

        return layoutContacto;
    }


    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if(constraint == null || arrayContactos == null) {
                filterResults.values = arrayContactos;
                filterResults.count = arrayContactos.size();
            } else{
                ArrayList<Contacto> tempList = new ArrayList<>();

                for(int i = 0; i < arrayContactos.size(); i++){
                    if(arrayContactos.get(i).getNombre().contains(constraint)) {
                        Contacto item = arrayContactos.get(i);
                        tempList.add(item);
                    }
                }

                filterResults.values = tempList;
                filterResults.count = tempList.size();
            }
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayContactosFiltrado = (ArrayList<Contacto>) results.values;

            notifyDataSetChanged();
        }
    };

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }
}
