package com.gtismartmoneytrader.data.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.gtismartmoneytrader.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class GTIMarketDataService : Service() {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    private var webSocket: WebSocket? = null
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    private val marketDataListeners = mutableListOf<MarketDataListener>()

    interface MarketDataListener {
        fun onMarketDataUpdate(symbol: String, price: Double, change: Double, volume: Long)
        fun onConnectionStatusChanged(connected: Boolean)
        fun onError(error: String)
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startDataStream()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        webSocket?.close(1000, "Service destroyed")
        serviceScope.cancel()
    }

    private fun startDataStream() {
        // In production, connect to actual broker WebSocket
        // For demo, we'll simulate data updates
        serviceScope.launch {
            while (isActive) {
                // Simulate market data updates
                simulateMarketData()
                delay(1000) // Update every second
            }
        }
    }

    private suspend fun simulateMarketData() {
        // This would be replaced with actual WebSocket connection to broker API
        // For demonstration, we'll generate mock data
        val basePrice = 22400.0 + (Math.random() - 0.5) * 50
        val change = (Math.random() - 0.5) * 100
        val volume = (Math.random() * 1000000).toLong()
        
        notifyListeners("NIFTY", basePrice, change, volume)
    }

    private fun notifyListeners(symbol: String, price: Double, change: Double, volume: Long) {
        marketDataListeners.forEach { listener ->
            listener.onMarketDataUpdate(symbol, price, change, volume)
        }
    }

    fun addListener(listener: MarketDataListener) {
        marketDataListeners.add(listener)
    }

    fun removeListener(listener: MarketDataListener) {
        marketDataListeners.remove(listener)
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, "gti_service")
            .setContentTitle("GTI Smart Money Trader")
            .setContentText("Monitoring market data...")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    companion object {
        private const val NOTIFICATION_ID = 100
        private const val WEBSOCKET_URL = "wss://api.gtismartmoneytrader.com/ws/market"
    }
}
