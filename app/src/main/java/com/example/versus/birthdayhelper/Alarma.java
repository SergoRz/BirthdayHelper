package com.example.versus.birthdayhelper;

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
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Clase que se encarga de establecer la Alarma, de las notificaciones y de enviar los SMS
 * de los cumpleañeros. Extiende de BroadcastReceiver.
 */
public class Alarma extends BroadcastReceiver {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Context mContext;
    private ContactosDbHelper cdbh = new ContactosDbHelper(mContext); //Clase que se encarga de la base de datos
    private SQLiteDatabase db; //Base de datos de los contactos

    /**
     * Constructor de la clase Alarma
     * @param mContext contexto desde donde se llama
     */
    public Alarma(Context mContext){
        this.mContext = mContext;
    }

    /**
     * Método que se ejecuta cuando la clase Alarma recibe el intent que lanza en el método
     * setAlarma()
     * @param context contexto desde donde se llama
     * @param intent intent que lo ha lanzado
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            comprobarNotificaciones();
        }
    }

    /**
     * Método que se encarga de establecer la alarma y ponerla a escuchar
     * @param hora hora a la que se lanza
     * @param min minuto en le que se lanza
     */
    public void setAlarma(int hora, int min) {
        alarmMgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);//Se crea el AlarmManager
        Intent intent = new Intent(mContext, Alarma.class);//Se crea un intent que llame a esta misma clase
        //Se crea un pending intent que llame a esta clase con el intent creado anteriormente
        alarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        //Se crea un objeto tipo Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        //Se le asigna la hora, el minuto y los segundos
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 00);

        //Se establece la alarma con a la hora especificada y co intervalo diario
        alarmMgr.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    /**
     * Método que se encarga de comprobar si algun contacto cumple los años en el dia actual
     * si es asi envia la notificacion y envia sms
     */
    private void comprobarNotificaciones() {
        //Array list para los contacos y para los cumpleañeros
        ArrayList<Contacto> contactos;
        ArrayList<Contacto> cumplen = new ArrayList<>();

        //Se crea un Calendar para obtener el dia y el mes actuales
        Calendar fecha = Calendar.getInstance();
        String mes = String.valueOf(fecha.get(Calendar.MONTH));
        String dia = String.valueOf(fecha.get(Calendar.DAY_OF_MONTH));

        //Se obtiene la base de datos de la clase ContactosDbHelper
        db = cdbh.getWritableDatabase();
        //Se cargan los contactos de la BD
        contactos = ContactosDbHelper.cargarContactos(db);

        //Se recorren los contactos y se comprueba cuales cumplen años en el dia actual
        for(Contacto contacto : contactos){
            //Se obtienen el dia y el mes de la fecha de nacimiento de los contactos
            String fechaContacto = contacto.getFechaNacimiento();
            String tokens[] = fechaContacto.split("/");

            //Si el mes y la fecha son iguales se añade el contacto al ArrayList de cumpleñeros
            if(tokens[0].equals(dia) && tokens[1].equals(mes)) cumplen.add(contacto);
        }

        //Si hay cumpleañeros se envia la notificacion y se les envia el SMS a cada uno,
        //si esta activada esa opcion para ese contacto
        if(cumplen.size() > 0){
            sendNotificacion();

            for(Contacto contacto : cumplen){
                if (contacto.getTipoNotif() != 'n') sendSMS(contacto);
            }
        }
    }

    /**
     * Método que se encarga de enviar un SMS a un contacto
     * @param oContacto contacto al que se le envia el SMS
     */
    public void sendSMS(Contacto oContacto) {
        //Se envia un SMS al contacto recibido por parametro
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(oContacto.getTelefono(),null,oContacto.getMensaje(),null,null);
    }

    /**
     * Método que se encarga de enviar una notificacion
     */
    public void sendNotificacion(){
        //Se envia la notificacion, informando de que hoy es el cumpleaños de alguien
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(android.R.drawable.btn_star);
        mBuilder.setLargeIcon((((BitmapDrawable) mContext.getResources().getDrawable(R.drawable.notif)).getBitmap()));
        mBuilder.setContentTitle("BirthDay Helper");
        mBuilder.setContentText("Hoy es el cumpleaños de alguien.");

        NotificationManager notif = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notif.notify(100, mBuilder.build());
    }
}
