package com.example.helloalarm

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId

class MyAlarm(private val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    private var alarmPendingIntent: PendingIntent? = null

    @RequiresApi(Build.VERSION_CODES.S)
    fun schedule(message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
            !alarmManager.canScheduleExactAlarms()) {
            Toast.makeText(context, "Can't schedule alarm", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", message)
        }
        val delay = LocalDateTime.now().plusSeconds(9).atZone(ZoneId.systemDefault()).toEpochSecond()*1000L
        alarmPendingIntent = PendingIntent.getBroadcast(context, 100, intent,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)
        alarmManager.setExactAndAllowWhileIdle(
            RTC_WAKEUP,
            delay,
            alarmPendingIntent!!
        )
        Log.e("++++", "++++ SENT ALARM delay $delay")
    }

    fun cancel() {
        alarmPendingIntent?.let {
            alarmManager.cancel(it)
        }
    }
}