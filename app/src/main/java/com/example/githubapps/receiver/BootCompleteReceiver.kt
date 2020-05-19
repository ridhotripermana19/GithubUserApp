package com.example.githubapps.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.githubapps.utils.AlarmUtils
import com.example.githubapps.utils.SharedPreference

class BootCompleteReceiver : BroadcastReceiver() {

    private lateinit var sharedPreference: SharedPreference

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            sharedPreference = SharedPreference(context)
            val timeInMilli = sharedPreference.getValueLong(SharedPreference.SAVED_LONG, 1)
            if (timeInMilli != null) {
                AlarmUtils.setAlarm(context, timeInMilli)
            }
            Toast.makeText(context, "Ready to go", Toast.LENGTH_SHORT).show()
        }
    }
}