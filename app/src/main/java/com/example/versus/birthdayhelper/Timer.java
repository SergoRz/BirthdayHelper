package com.example.versus.birthdayhelper;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.format.DateFormat;
import android.widget.TimePicker;


import static android.content.Context.MODE_PRIVATE;

/**
 * DataPickerDialog que se utiliza a la hora de querer cambiar la hora de notificacion
 * Nos permite establecer una nueva hora de notificacion
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class Timer extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    /**
     * Metodo que se ejecuta al inicial el Dialog, carga la hora de notificacion guardada
     * en las preferencias.
     * @param savedInstanceState
     * @return Devuelve el dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Se accede a las preferencias
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        //Se carga la hora y los minutos de la notificacion
        int hora = prefs.getInt("horaMensaje", 00);
        int minutos = prefs.getInt("minutosMensaje", 00);

        //Crea una nueva instancia de TimePickerDialog y la devuelve
        return new TimePickerDialog(getActivity(),this, hora, minutos,
                DateFormat.is24HourFormat(getActivity()));
    }


    /**
     * Metodo que se ejecuta al hacer clic en el boton OK del Dialog
     * Establece la nueva hora escogido y la guarda en las preferencias
     * @param view
     * @param hourOfDay
     * @param minute
     */
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Se accede a las preferencias
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        //Se accede al editor de las preferencias
        SharedPreferences.Editor editor = prefs.edit();

        //Se guardan la hora y los minutos
        editor.putInt("horaMensaje", hourOfDay);
        editor.putInt("minutosMensaje", minute);
        editor.apply(); //Se aplican los cambios

        //Se actualiza la hora de la alarma
        Alarma alarma = new Alarma(getActivity());
        alarma.setAlarma(hourOfDay,minute);
    }
}
