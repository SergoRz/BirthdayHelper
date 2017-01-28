package com.example.versus.birthdayhelper;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;


import static android.content.Context.MODE_PRIVATE;


public class Timer extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private Alarma alarma = new Alarma(getActivity());;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        int hora = prefs.getInt("horaMensaje", 00);
        int minutos = prefs.getInt("minutosMensaje", 00);

        return new TimePickerDialog(getActivity(),this, hora, minutos,
                DateFormat.is24HourFormat(getActivity()));
    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("horaMensaje", hourOfDay);
        editor.putInt("minutosMensaje", minute);
        editor.apply();
        alarma.setAlarma(hourOfDay,minute);
    }
}
