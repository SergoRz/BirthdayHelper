package com.example.versus.birthdayhelper;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {

        Log.d("Alarma:", " funciona");
        int notifid = 1;
        Date horaActual=new Date();
        String hora=horaActual.getHours()+ " " +horaActual.getMinutes();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(android.R.drawable.stat_sys_warning);
        mBuilder.setLargeIcon((((BitmapDrawable) context.getResources().getDrawable(R.drawable.notif)).getBitmap()));
        mBuilder.setContentTitle("BirthDay Helper");
        mBuilder.setContentText(hora);


        NotificationManager notif = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notif.notify(notifid, mBuilder.build());

        Contacto oContactoE = new Contacto(10, "Emilio", "628232938", "25/01/2017", 'n', "Hola Emilio, esto funciona dpm, Feliz Cumpleaños!");
        Contacto oContactoS = new Contacto(10, "Sergio", "665463077", "25/01/2017", 'n', "Hola Sergio, esto funciona dpm, Feliz Cumpleaños!");
        sendSMS(context, oContactoS);

    }

    public void sendSMS(android.content.Context context, Contacto oContacto) {
        /*
        Uri uri = Uri.parse("smsto:" + oContacto.getTelefono());
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", oContacto.getMensaje());
        context.startActivity(it);*/

        /*
        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address",oContacto.getTelefono());
        smsIntent.putExtra("sms_body",oContacto.getMensaje());
        context.startActivity(smsIntent);*/
    }

}
