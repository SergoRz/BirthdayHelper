package com.example.versus.birthdayhelper;

import android.provider.BaseColumns;

public class ContactosContract {

    public static abstract class ContactoEntry implements BaseColumns {
        public static final String TABLE_NAME ="contactos";
        public static final String ID = "id";
        public static final String TIPONOTIF = "tipoNotif";
        public static final String MENSAJE = "mensaje";
        public static final String TELEFONO = "telefono";
        public static final String FECHANACIMIENTO = "fechaNacimiento";
        public static final String NOMBRE = "nombre";

    }

}
