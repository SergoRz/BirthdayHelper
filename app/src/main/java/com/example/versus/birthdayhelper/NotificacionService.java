package com.example.versus.birthdayhelper;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import java.util.Date;

public class NotificacionService extends IntentService {
    public NotificacionService() {
        super("Notificacion");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Servicio ", "iniciado");
        sendNotification();
        Alarma.completeWakefulIntent(intent);
    }

    // ENVIA UNA NOTIFICACION DE PRUEBA
    private void sendNotification() {
        Contacto oContactoE = new Contacto(10, "Emilio", "628232938", "25/01/2017", 'n', "Hola Emilio, Feliz Cumpleaños!");
        Contacto oContactoS = new Contacto(10, "Sergio", "665463077", "25/01/2017", 'n', "Hola Sergio, Feliz Cumpleaños!");

        Date horaActual = new Date();
        String hora=horaActual.getHours()+ " " +horaActual.getMinutes();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(android.R.drawable.btn_star);
        mBuilder.setLargeIcon((((BitmapDrawable) getResources().getDrawable(R.drawable.notif)).getBitmap()));
        mBuilder.setContentTitle("BirthDay Helper");
        mBuilder.setContentText(hora);

        NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notif.notify(100, mBuilder.build());

        //sendSMS(oContactoE);
    }

    public void sendSMS(Contacto oContacto) {
        Log.d("SMS","enviado");
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(oContacto.getTelefono(),null,oContacto.getMensaje(),null,null);
    }
}

