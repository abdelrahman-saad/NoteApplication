package com.example.noteapplication;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = "";
        String description = "";
        if(intent != null) {
          title  = intent.getStringExtra("title");
            description  = intent.getStringExtra("description");
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),1,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify note")
                .setSmallIcon(R.drawable.bellicon2)
                .setContentTitle( title)
                .setContentText(description)
                .addAction(R.drawable.bellicon2,"open App",pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
        notificationCompat.notify(200, builder.build());
//        Toast.makeText(context, "ALAaaaaaaaaaaaaaaaaaaaaaaaaaRM", Toast.LENGTH_LONG).show();

    }
}
