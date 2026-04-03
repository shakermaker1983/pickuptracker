package com.pickuptracker

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PickupPrefs(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "pickup_prefs"
        private const val KEY_COUNT = "pickup_count"
        private const val KEY_DATE = "pickup_date"
        private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getCount(): Int = prefs.getInt(KEY_COUNT, 0)

    fun increment() {
        checkAndResetIfNewDay()
        val current = prefs.getInt(KEY_COUNT, 0)
        prefs.edit().putInt(KEY_COUNT, current + 1).apply()
    }

    fun checkAndResetIfNewDay() {
        val today = DATE_FORMAT.format(Date())
        val savedDate = prefs.getString(KEY_DATE, "")
        if (savedDate != today) {
            prefs.edit()
                .putInt(KEY_COUNT, 0)
                .putString(KEY_DATE, today)
                .apply()
        }
    }
}
