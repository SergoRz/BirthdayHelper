package com.example.versus.birthdayhelper;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Versus on 23/01/2017.
 */

public class MyReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        Toast.makeText(context, "CUMPLEAÑOS DE ALGUIEN", Toast.LENGTH_LONG).show();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ListaContactos.this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle("BirthDay Helper")
                        .setContentText("Hoy es el cumpleaños de alguien");
    }
}
