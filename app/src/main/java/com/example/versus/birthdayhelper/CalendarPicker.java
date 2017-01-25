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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        fecha = (EditText) getActivity().findViewById(R.id.etFecha);
        int year, month, day;

        if (fecha.getText().toString().equals("")) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

        } else {
            String[] fechas = fecha.getText().toString().split("/");

            year = Integer.parseInt(fechas[2]);
            month = Integer.parseInt(fechas[1]) - 1;
            day = Integer.parseInt(fechas[0]);
        }

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        fecha = (EditText) getActivity().findViewById(R.id.etFecha);
        fecha.setText(day + "/" + (month + 1) + "/" + year);
    }
}