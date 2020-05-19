package com.example.githubapps.view.reminder

import android.app.TimePickerDialog
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapps.R
import com.example.githubapps.receiver.BootCompleteReceiver
import com.example.githubapps.utils.AlarmUtils
import com.example.githubapps.utils.SharedPreference
import com.example.githubapps.utils.SharedPreference.Companion.SAVED_LONG
import com.example.githubapps.utils.SharedPreference.Companion.SAVED_TEXT
import kotlinx.android.synthetic.main.activity_reminder.*
import java.text.SimpleDateFormat
import java.util.*

class Reminder : AppCompatActivity(), View.OnClickListener {

    private lateinit var sharedPreference : SharedPreference
    var timeInMilliSeconds: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        setTitle(R.string.reminder)

        val receiver = ComponentName(applicationContext, BootCompleteReceiver::class.java)

        applicationContext.packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)

        sharedPreference = SharedPreference(this)
        btn_setAlarm.setOnClickListener(this)
        btn_cancelAlarm.setOnClickListener(this)
        startTimeText.setOnClickListener(this)

        if (sharedPreference.getValueString(SAVED_TEXT) != null) {
            startTimeText.text = sharedPreference.getValueString(SAVED_TEXT)
            btn_setAlarm.isEnabled = false
            note.text = this.getString(R.string.alamisset)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.startTimeText -> {
                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)
                val timePickerDialog = TimePickerDialog(this,
                    TimePickerDialog.OnTimeSetListener {_, hourOfDay, minuteOfHour ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minuteOfHour)
                        calendar.set(Calendar.SECOND, 0)
                        val formattedTime = String.format("%02d:%02d", hourOfDay, minuteOfHour)
                        startTimeText.text = formattedTime
                        sharedPreference.saveString(SAVED_TEXT, formattedTime)
                        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
                        val formattedDate = sdf.format(calendar.time)
                        val date = sdf.parse(formattedDate)
                        if (date != null)
                            timeInMilliSeconds = date.time
                    }, hour, minute, true)
                timePickerDialog.show()
            }

            R.id.btn_setAlarm -> {
                if (timeInMilliSeconds.toInt() != 0) {
                    sharedPreference.saveLong(SAVED_LONG, timeInMilliSeconds)
                    AlarmUtils.setAlarm(this, timeInMilliSeconds)
                    note.text = this.getString(R.string.alamisset)
                    btn_setAlarm.isEnabled = false
                } else
                    Toast.makeText(this, this.getString(R.string.enterTimeWarning), Toast.LENGTH_SHORT).show()
            }

            R.id.btn_cancelAlarm -> {
                sharedPreference.clearSharedPreference()
                AlarmUtils.cancelAlarm(this)
                startTimeText.text = this.getString(R.string.enterTime)
                note.text = this.getString(R.string.note)
                btn_setAlarm.isEnabled = true
            }
        }
    }
}
