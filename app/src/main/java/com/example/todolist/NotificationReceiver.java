package com.example.todolist;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context, intent.getStringExtra("taskName"));
    }

    private void showNotification(Context context, String taskName) {
        // #### Create a notification channel with an specific channel id
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String id = "notification_channel";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(id, "notif_ch", importance);
        mChannel.enableLights(true);
        mNotificationManager.createNotificationChannel(mChannel);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "notification_channel")
                        .setSmallIcon(R.drawable.ic_baseline_calendar_today_24)
                        .setContentTitle("Task \"" + taskName + "\" deleted")
                        .setContentText("Task \"" + taskName + "\" was successfully deleted!");

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}

