package com.example.versus.birthdayhelper;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class NotificacionService extends IntentService {
    public NotificacionService() {
        super("Notificacion");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification();
    }

    // Post a notification indicating whether a doodle was found.
    private void sendNotification() {
        Contacto oContactoE = new Contacto(10, "Emilio", "628232938", "25/01/2017", 'n', "Hola Emilio, Feliz Cumpleaños!");
        Contacto oContactoS = new Contacto(10, "Sergio", "665463077", "25/01/2017", 'n', "Hola Sergio, Feliz Cumpleaños!");

        Log.d("Alarma:", " funciona");
        Date horaActual = new Date();
        String hora=horaActual.getHours()+ " " +horaActual.getMinutes();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(android.R.drawable.btn_star);
        mBuilder.setLargeIcon((((BitmapDrawable) getResources().getDrawable(R.drawable.notif)).getBitmap()));
        mBuilder.setContentTitle("BirthDay Helper");
        mBuilder.setContentText(hora);

        NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notif.notify(100, mBuilder.build());

        sendSMS(oContactoS);
    }

    public void sendSMS(Contacto oContacto) {
        Intent smsIntent = new Intent(android.content.Intent.ACTION_SEND);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address",oContacto.getTelefono());
        smsIntent.putExtra("sms_body",oContacto.getMensaje());
        startActivity(smsIntent);
    }
}

