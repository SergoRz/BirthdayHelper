package com.example.versus.birthdayhelper;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 */
public class Alarma extends BroadcastReceiver {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Context mContext;
    private ContactosDbHelper cdbh = new ContactosDbHelper(mContext); //Clase que se encarga de la base de datos
    private SQLiteDatabase db; //Base de datos de los contactos

    public Alarma(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            comprobarNotificaciones();
        }
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

    private void comprobarNotificaciones() {

        ArrayList<Contacto> contactos;
        ArrayList<Contacto> cumplen = new ArrayList<>();

        Calendar fecha = Calendar.getInstance();
        String mes = String.valueOf(fecha.get(Calendar.MONTH));
        String dia = String.valueOf(fecha.get(Calendar.DAY_OF_MONTH));

        db = cdbh.getWritableDatabase(); //Se obtiene la base de datos de la clase ContactosDbHelper
        contactos = ContactosDbHelper.cargarContactos(db);

        for(Contacto contacto : contactos){
            String fechaContacto = contacto.getFechaNacimiento();
            String tokens[] = fechaContacto.split("/");

            if(tokens[0].equals(dia) && tokens[1].equals(mes)) cumplen.add(contacto);
        }

        if(cumplen.size() > 0){
            sendNotificacion();

            for(Contacto contacto : cumplen){
                sendSMS(contacto);
            }
        }
    }

    public void sendSMS(Contacto oContacto) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(oContacto.getTelefono(),null,oContacto.getMensaje(),null,null);
    }

    public void sendNotificacion(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(android.R.drawable.btn_star);
        mBuilder.setLargeIcon((((BitmapDrawable) mContext.getResources().getDrawable(R.drawable.notif)).getBitmap()));
        mBuilder.setContentTitle("BirthDay Helper");
        mBuilder.setContentText("Hoy es el cumpleaños de alguien.");

        NotificationManager notif = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notif.notify(100, mBuilder.build());
    }
}
