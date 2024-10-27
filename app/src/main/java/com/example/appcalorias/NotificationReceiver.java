package com.example.appcalorias;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Aquí defines el mensaje para cada tipo de notificación
        String message = intent.getStringExtra("message");
        Log.d("NotificationReceiver", "Notificación recibida: " + message); // Agrega este log
        sendNotification(context, "Recordatorio de Comida", message);
    }

    private void sendNotification(Context context, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "meal_reminder_channel")
                .setSmallIcon(R.drawable.ic_notifications) // Asegúrate de tener un icono para la notificación
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }
}
