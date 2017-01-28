package com.example.versus.birthdayhelper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;


public class CalendarPicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText fecha;

    /**
     * Metodo que se ejecuta al inicial el Dialog, trata de cargar la fecha de naciemito del contaco,
     * si no tiene una fecha determinada, selecciona la fecha actual.
     * @param savedInstanceState
     * @return Devuelve el dialog
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Se asocia la fecha con la fecha de cumpleaños de la activity
        fecha = (EditText) getActivity().findViewById(R.id.etFecha);
        int year, month, day;

        if (fecha.getText().toString().equals("")) { //Si el contacto no tiene una fecha asignada..
            final Calendar c = Calendar.getInstance(); //Se instancia un Calendar
            //Se le dan los valores de la fecha actual
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

        } else { //Si tiene una fecha asignada..
            //Se divide la fecha del contacto
            String[] fechas = fecha.getText().toString().split("/");
            //Se carga la fecha del contacto
            year = Integer.parseInt(fechas[2]);
            month = Integer.parseInt(fechas[1]) - 1;
            day = Integer.parseInt(fechas[0]);
        }

        //Crea una nueva instancia de DataPickerDialog y la devuelve
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * Metodo que se ejecuta al hacer clic en el boton OK del Dialog
     * Establece la fecha escogida a la fecha de cumpleaños del contacto
     * @param view Vista
     * @param year Año escogido
     * @param month Mes escogido
     * @param day Dia escogido
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Se asocia la fecha con la fecha de cumpleaños de la activity
        fecha = (EditText) getActivity().findViewById(R.id.etFecha);
        //Se carga la gehca en el TextBox de la fecha de nacimiento
        fecha.setText(day + "/" + (month + 1) + "/" + year);
    }
}