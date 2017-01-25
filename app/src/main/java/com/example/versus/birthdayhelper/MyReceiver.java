package com.example.versus.birthdayhelper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
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
        mBuilder.setSmallIcon(android.R.drawable.btn_star);
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
        PendingIntent _pendingIntent;
        Intent _intent = new Intent();
        _intent.setClass(context, this.getClass());
        _intent.putExtra("test","test");
        _pendingIntent = PendingIntent.getBroadcast(context, 0, _intent, 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(oContacto.getTelefono(), null, oContacto.getMensaje(), _pendingIntent, null);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                sendSMS();
            } else {
                Toast.makeText(this, "Hasta que no aceptes los permisos no podremos mostrar los contactos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
