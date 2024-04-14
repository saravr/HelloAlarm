package com.example.helloalarm

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import androidx.annotation.RequiresApi

class MyForegroundService : Service() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        // Start the service as foreground
        startForeground(FOREGROUND_NOTIFICATION_ID, createNotification())

        // Perform your task here
        // For example, play audio
        // Note: This is a simplified example
        scheduleAlarm()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the service
        stopForeground(true)
        stopSelf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification {
        // Create a notification for the foreground service
        // You can customize this notification as needed
        return Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Running...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun scheduleAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this, MyForegroundService::class.java).let { intent ->
            PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val triggerTimeMillis = SystemClock.elapsedRealtime() + 15000L
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTimeMillis, alarmIntent)
    }

    companion object {
        private const val FOREGROUND_NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "ForegroundServiceChannel"
    }
}
