package com.example.behealthy;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

class Notification_receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String medicinename=intent.getStringExtra("medicinename");
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background).setContentTitle("Be Healthy").setContentText("Its time to take "+medicinename).setAutoCancel(true);
        notificationManager.notify(2,builder.build());
    }
}
