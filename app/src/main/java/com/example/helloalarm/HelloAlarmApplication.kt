package com.example.helloalarm

import android.app.Application
import android.content.Intent

class HelloAlarmApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val serviceIntent = Intent(this, TestService::class.java)
        startService(serviceIntent)
    }
}