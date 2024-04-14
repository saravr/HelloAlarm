package com.example.helloalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: "NOT-SET"
        Toast.makeText(context!!, "Received alarm $message", Toast.LENGTH_LONG).show()
        Log.e("++++", "++++ RECEIVED ALARM $message")
    }
}
