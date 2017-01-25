package com.example.versus.birthdayhelper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.graphics.drawable.BitmapDrawable;

import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;


import java.util.Date;

import static android.app.PendingIntent.getBroadcast;


public class MyReceiver extends BroadcastReceiver {

    private Contacto oContacto;
    private Context mContext;
    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        Contacto oContactoE = new Contacto(10, "Emilio", "628232938", "25/01/2017", 'n', "Hola Emilio, Feliz Cumpleaños!");
        Contacto oContactoS = new Contacto(10, "Sergio", "665463077", "25/01/2017", 'n', "Hola Sergio, Feliz Cumpleaños!");
        oContacto = oContactoS;
        mContext = context;

        Log.d("Alarma:", " funciona");
        int notifid = 1;
        Date horaActual = new Date();
        String hora=horaActual.getHours()+ " " +horaActual.getMinutes();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(android.R.drawable.btn_star);
        mBuilder.setLargeIcon((((BitmapDrawable) mContext.getResources().getDrawable(R.drawable.notif)).getBitmap()));
        mBuilder.setContentTitle("BirthDay Helper");
        mBuilder.setContentText(hora);

        NotificationManager notif = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notif.notify(notifid, mBuilder.build());

        sendSMS();
    }

    public void sendSMS() {
        PendingIntent _pendingIntent;
        Intent _intent = new Intent();
        _intent.setClass(mContext, this.getClass());
        _intent.putExtra("test", "test");
        _pendingIntent = getBroadcast(mContext, 0, _intent, 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(oContacto.getTelefono(), null, oContacto.getMensaje(), _pendingIntent, null);
    }
}
