package com.gtismartmoneytrader.data.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.gtismartmoneytrader.GTIApplication
import com.gtismartmoneytrader.R
import com.gtismartmoneytrader.presentation.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GTIFCMService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        val data = remoteMessage.data
        val type = data["type"] ?: return
        
        when (type) {
            "gti_alert" -> showGTIAlert(data)
            "signal" -> showSignalNotification(data)
            "straddle_alert" -> showStraddleAlert(data)
            "conflict_alert" -> showConflictAlert(data)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send token to backend for push notification targeting
    }

    private fun showGTIAlert(data: Map<String, String>) {
        val title = data["title"] ?: "Smart Money Detected"
        val body = data["body"] ?: "Yellow candle formation detected"
        
        showNotification(
            channelId = GTIApplication.CHANNEL_GTI_ALERTS,
            notificationId = GTI_ALERT_NOTIFICATION_ID,
            title = title,
            body = body
        )
    }

    private fun showSignalNotification(data: Map<String, String>) {
        val title = data["title"] ?: "Signal"
        val body = data["body"] ?: "Trading signal generated"
        val signalId = data["signalId"] ?: ""
        
        showNotification(
            channelId = GTIApplication.CHANNEL_SIGNALS,
            notificationId = SIGNAL_NOTIFICATION_ID + signalId.hashCode(),
            title = title,
            body = body
        )
    }

    private fun showNotification(
        channelId: String,
        notificationId: Int,
        title: String,
        body: String
    ) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationId, notification)
    }

    companion object {
        private const val GTI_ALERT_NOTIFICATION_ID = 1001
        private const val SIGNAL_NOTIFICATION_ID = 2001
        private const val STRADDLE_NOTIFICATION_ID = 3001
        private const val CONFLICT_NOTIFICATION_ID = 4001
    }

    private fun showStraddleAlert(data: Map<String, String>) {
        showNotification(
            channelId = GTIApplication.CHANNEL_GTI_ALERTS,
            notificationId = STRADDLE_NOTIFICATION_ID,
            title = data["title"] ?: "📊 Straddle Alert",
            body = data["body"] ?: "High Probability Short Straddle detected"
        )
    }

    private fun showConflictAlert(data: Map<String, String>) {
        showNotification(
            channelId = GTIApplication.CHANNEL_GTI_ALERTS,
            notificationId = CONFLICT_NOTIFICATION_ID,
            title = data["title"] ?: "⚠️ Conflict Alert",
            body = data["body"] ?: "Conflicting signals – Avoid trading"
        )
    }
}

