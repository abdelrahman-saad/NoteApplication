package com.example.noteapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify note")
                .setSmallIcon(R.drawable.bellicon2)
                .setContentTitle(" Wake up BITCH")
                .setContentText("Hallo Brazoooor")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
        notificationCompat.notify(200, builder.build());
        Toast.makeText(context, "ALAaaaaaaaaaaaaaaaaaaaaaaaaaRM", Toast.LENGTH_LONG).show();

    }
}
