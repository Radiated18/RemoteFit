package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.screens.navigation.AppNavigation
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.utils.AlarmReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProjectApp()
                }
            }
        }

        // Programar las alarmas
        scheduleWeeklyNotifications(this)
    }

    @Composable
    fun ProjectApp() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavigation()
        }
    }

    private fun scheduleWeeklyNotifications(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()

        // Calcular el día de la semana actual
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        // Ajustar el calendario al próximo lunes
        val daysUntilMonday = (Calendar.MONDAY - dayOfWeek + 7) % 7
        calendar.add(Calendar.DAY_OF_YEAR, daysUntilMonday)

        // Programar las alarmas para cada jornada
        for (hour in intArrayOf(8, 14, 19)) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                hour,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Configurar el calendario para la hora específica
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            // Programar la alarma
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY * 7, // Repetir cada 7 días
                pendingIntent
            )
        }
    }

}