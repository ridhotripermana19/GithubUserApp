package com.example.githubapps.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.githubapps.R
import com.example.githubapps.utils.AlarmUtils
import com.example.githubapps.utils.SharedPreference
import com.example.githubapps.view.search.SearchUser

class NotificationReceiver : BroadcastReceiver() {

    private lateinit var sharedPreference: SharedPreference

    override fun onReceive(context: Context, intent: Intent?) {
        showNotification(context)

        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            Log.d("onBoot", "Success Booting Bro!")
            sharedPreference = SharedPreference(context)
            val timeInMilli = sharedPreference.getValueLong(SharedPreference.SAVED_LONG, 1)
            if (timeInMilli != null)
                AlarmUtils.setAlarm(context, timeInMilli)
        }
    }

    private fun showNotification(context: Context) {
        val someIntent = Intent(context, SearchUser::class.java)
        val pIntent = PendingIntent.getActivity(context, 0, someIntent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.reminder)
            val descriptionText = context.getString(R.string.alarmMessage)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("AlarmId", name, importance)
            mChannel.description = descriptionText
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        val mBuilder = NotificationCompat.Builder(context, "AlarmId")
            .setSmallIcon(R.drawable.ic_notifications_24dp)
            .setContentTitle(context.getString(R.string.reminder))
            .setContentText(context.getString(R.string.alarmMessage))
            .setAutoCancel(true)
            .setContentIntent(pIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = System.currentTimeMillis() / 1000
        nm.notify(id.toInt(), mBuilder.build())
    }
}