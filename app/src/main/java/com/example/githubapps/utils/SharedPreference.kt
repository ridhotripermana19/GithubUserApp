package com.example.githubapps.utils

import android.content.Context
import androidx.core.content.edit

class SharedPreference(context: Context) {

    companion object {
        const val PREFERENCE_NAME = "SharedPreference"

        // Details & Fragment
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_KEY = "extra_key"

        // Reminder
        const val SAVED_TEXT = "saved_text"
        const val SAVED_LONG = "saved_long"
    }

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun saveString(KEY_NAME: String, value: String) {
        preference.edit {
            putString(KEY_NAME, value)
            apply()
        }
    }

    fun getValueString(KEY_NAME: String): String? {
        return preference.getString(KEY_NAME, null)
    }

    fun saveLong(KEY_NAME: String, value: Long) {
        preference.edit {
            putLong(KEY_NAME, value)
            apply()
        }
    }

    fun getValueLong(KEY_NAME: String, l: Long): Long? {
        return preference.getLong(KEY_NAME, 0)
    }

    fun clearSharedPreference() {
        preference.edit{
            clear()
            apply()
        }
    }
}