package com.example.versus.birthdayhelper;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContactosDbHelper usdbh = new ContactosDbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if(db != null){
            Log.d("BD","Se ha abierto correctamente");
        } else{
            Log.d("BD","No se ha abierto correctamente");
        }
    }
}
