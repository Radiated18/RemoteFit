package com.example.myapplication.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.R
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Verificar si la notificación es para el día actual
        val calendar = Calendar.getInstance()
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            val message = when (calendar.get(Calendar.HOUR_OF_DAY)) {
                8 -> "Buenos días. Es hora de tu notificación de la mañana."
                14 -> "¡Hola! Es hora de tu notificación de la tarde."
                19 -> "¡Buenas noches! Es hora de tu notificación de la noche."
                else -> return
            }

            // Mostrar la notificación
            showNotification(context, message)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun showNotification(context: Context?, message: String) {
        val channelId = "notification_channel"
        val notificationId = 1

        val builder = NotificationCompat.Builder(context!!, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Recordatorio semanal")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)

        // Crear el canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones semanales",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(notificationId, builder.build())

    }
}
