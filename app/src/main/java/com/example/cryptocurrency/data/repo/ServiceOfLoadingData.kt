package com.example.cryptocurrency.data.repo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.cryptocurrency.presentation.App
import io.reactivex.disposables.CompositeDisposable

class ServiceOfLoadingData : Service() {

    private lateinit var repository: Repository
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var preferences: SharedPreferences

    private val prefsChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { p0, p1 ->
        p0?.let {
            if (it.contains(App.KEY_REFRESHING_PERIOD)) {
                val periodPercent = it.getInt(App.KEY_REFRESHING_PERIOD, 30)
                var periodSeconds = (0.6 * periodPercent).toInt()
                if (periodSeconds > 60) periodSeconds = 60
                if (periodSeconds < 1) { periodSeconds = 1 }
                timeout = periodSeconds
            }
        }
    }

    private var timeout = 18
    private val handler = Handler()
    private var timer: Runnable? = null

    companion object {
        const val FOREGROUND_SERVICE_ID = 1
        const val NOTIFICATION_CHANNEL_ID = "channel loading data id"
        const val NOTIFICATION_CHANNEL_NAME = "channel loading data name"
    }

    override fun onCreate() {
        super.onCreate()
        repository = Repository(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
        }
        notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        preferences = getSharedPreferences(App.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        preferences.registerOnSharedPreferenceChangeListener(prefsChangeListener)
        if (preferences.contains(App.KEY_REFRESHING_PERIOD)) {
            timeout = preferences.getInt(App.KEY_REFRESHING_PERIOD, 18)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timer?.let {
            handler.removeCallbacks(it)
        }
        timer = object : Runnable {
            override fun run() {
                repository.loadData()
                handler.postDelayed(this, timeout * 1000L)
                Log.d("RefreshingTest", "Refresh")
            }
        }
        timer?.let { handler.post(it) }
        notificationBuilder.setContentText("fsdfs00")
        notificationBuilder.setContentTitle("fsdf")
        notificationBuilder.setSmallIcon(android.R.drawable.sym_def_app_icon)
        startForeground(FOREGROUND_SERVICE_ID, notificationBuilder.build())
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        timer?.let {
            handler.removeCallbacks(it)
        }
        repository.clearResources()
        preferences.unregisterOnSharedPreferenceChangeListener(prefsChangeListener)
        super.onDestroy()
    }
}