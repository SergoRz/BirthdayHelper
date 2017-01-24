package com.example.versus.birthdayhelper;

import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Versus on 23/01/2017.
 */

public class MyReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ListaContactos.getContext())
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle("BirthDay Helper")
                        .setContentText("Hoy es el cumpleaños de alguien");
    }
}
