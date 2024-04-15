package com.example.helloalarm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class TestService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        Log.e(TAG, "++++ SVC: onBind")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "++++ SVC: onStartCommand instance $this")
        if (intent?.action == "TEST_ACTION") {
            val data = intent.extras?.getString("FOO")
            Log.e(TAG, "++++ SVC: onStartCommand GOT TEST ACTION data $data")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        private const val TAG = "TestService"
    }
}