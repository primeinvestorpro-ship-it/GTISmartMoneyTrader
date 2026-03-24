package com.gtismartmoneytrader

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GTIApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val gtiChannel = NotificationChannel(
                CHANNEL_GTI_ALERTS,
                "GTI Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for Smart Money detection"
                enableVibration(true)
            }

            val signalChannel = NotificationChannel(
                CHANNEL_SIGNALS,
                "Trading Signals",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "BUY CALL and BUY PUT signal notifications"
                enableVibration(true)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(gtiChannel)
            notificationManager.createNotificationChannel(signalChannel)
        }
    }

    companion object {
        const val CHANNEL_GTI_ALERTS = "gti_alerts"
        const val CHANNEL_SIGNALS = "trading_signals"
    }
}
