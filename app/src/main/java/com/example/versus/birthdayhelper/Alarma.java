package com.example.versus.birthdayhelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;


public class Alarma extends WakefulBroadcastReceiver {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Alarma ", "disparada");
        Intent service = new Intent(context, NotificacionService.class);
        startWakefulService(context, service);
    }

    public void setAlarma(Context context, int hora, int min) {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarma.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 00);

        Log.d(String.valueOf(hora), String.valueOf(min));
        Log.d("Milis alarma: ", String.valueOf(calendar.getTimeInMillis()));
        Log.d("Milis Actual: ", String.valueOf(System.currentTimeMillis()));

        alarmMgr.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

        /*
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);*/
    }
}
