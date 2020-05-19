package com.example.githubapps.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.githubapps.receiver.NotificationReceiver

object AlarmUtils {

    fun setAlarm(context: Context, timeOfAlarm: Long) {

        val broadcastIntent = Intent(context, NotificationReceiver::class.java)

        // reqcode = 0 (alarm hanya 1 yaitu RepeatingAlarm)
        val pIntent = PendingIntent.getBroadcast(context, 0, broadcastIntent, 0)

        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (System.currentTimeMillis() < timeOfAlarm) {
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeOfAlarm, AlarmManager.INTERVAL_DAY, pIntent)
        }
    }

    fun cancelAlarm(context: Context) {
        val broadcastIntent = Intent(context, NotificationReceiver::class.java)

        val pIntent = PendingIntent.getBroadcast(context, 0, broadcastIntent, 0)
        pIntent.cancel()

        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmMgr.cancel(pIntent)
    }
}