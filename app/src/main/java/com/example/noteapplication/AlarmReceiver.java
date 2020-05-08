package com.example.noteapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static androidx.core.content.ContextCompat.getSystemService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "ALAaaaaaaaaaaaaaaaaaaaaaaaaaRM", Toast.LENGTH_LONG).show();

    }
}
