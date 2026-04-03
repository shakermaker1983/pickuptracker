package com.pickuptracker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvPickupCount: TextView
    private lateinit var tvMessage: TextView
    private lateinit var btnStartService: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPickupCount = findViewById(R.id.tvPickupCount)
        tvMessage = findViewById(R.id.tvMessage)
        btnStartService = findViewById(R.id.btnStartService)

        btnStartService.setOnClickListener {
            startPickupService()
        }

        startPickupService()
    }

    override fun onResume() {
        super.onResume()
        updateCount()
    }

    private fun startPickupService() {
        val intent = Intent(this, PickupService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        btnStartService.text = getString(R.string.tracking_active)
        btnStartService.isEnabled = false
    }

    private fun updateCount() {
        val prefs = PickupPrefs(this)
        prefs.checkAndResetIfNewDay()
        val count = prefs.getCount()
        tvPickupCount.text = count.toString()
        tvMessage = findViewById(R.id.tvMessage)
        tvMessage.text = when {
            count == 0 -> getString(R.string.message_zero)
            count < 30 -> getString(R.string.message_low)
            count < 60 -> getString(R.string.message_medium)
            else -> getString(R.string.message_high)
        }
    }
}
