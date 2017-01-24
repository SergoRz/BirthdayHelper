package com.example.versus.birthdayhelper;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by EmilioCB on 24/01/2017.
 */

public class Timer extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        int hora = prefs.getInt("horaMensaje", 00);
        int minutos = prefs.getInt("minutosMensaje", 00);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hora, minutos,
                DateFormat.is24HourFormat(getActivity()));
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        //Se editan las preferencias
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("horaMensaje", hourOfDay);
        editor.putInt("minutosMensaje", minute);
        editor.apply();
    }

}
