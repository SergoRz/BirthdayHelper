package com.example.versus.birthdayhelper;

import android.widget.Toast;

/**
 * Created by Versus on 23/01/2017.
 */

public class MyReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        Toast.makeText(context, "CUMPLEAÑOS DE ALGUIEN", Toast.LENGTH_LONG).show();
    }
}
