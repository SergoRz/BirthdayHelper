package com.example.versus.birthdayhelper;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;


public class Alarma extends BroadcastReceiver {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Context mContext;

    public Alarma(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification();
    }

    public void setAlarma(int hora, int min) {
        alarmMgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, Alarma.class);
        alarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 00);

        alarmMgr.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    // ENVIA UNA NOTIFICACION DE PRUEBA
    private void sendNotification() {
        Contacto oContactoE = new Contacto(10, "Emilio", "628232938", "25/01/2017", 'n', "Hola Emilio, Feliz Cumpleaños!");
        Contacto oContactoS = new Contacto(10, "Sergio", "665463077", "25/01/2017", 'n', "Hola Sergio, Feliz Cumpleaños!");

        Date horaActual = new Date();
        String hora=horaActual.getHours()+ " " +horaActual.getMinutes();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(android.R.drawable.btn_star);
        mBuilder.setLargeIcon((((BitmapDrawable) mContext.getResources().getDrawable(R.drawable.notif)).getBitmap()));
        mBuilder.setContentTitle("BirthDay Helper");
        mBuilder.setContentText(hora);

        NotificationManager notif = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notif.notify(100, mBuilder.build());

        sendSMS(oContactoE);
    }

    public void sendSMS(Contacto oContacto) {
        Log.d("SMS","enviado");
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(oContacto.getTelefono(),null,oContacto.getMensaje(),null,null);
    }
}
