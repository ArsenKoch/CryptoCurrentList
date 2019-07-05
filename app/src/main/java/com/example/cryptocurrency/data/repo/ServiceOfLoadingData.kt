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
import com.example.cryptocurrency.R
import com.example.cryptocurrency.api.ApiFactory
import com.example.cryptocurrency.data.pojo.CoinPriceDisplayInfo
import com.example.cryptocurrency.data.pojo.CoinPriceInfo
import com.example.cryptocurrency.presentation.App
import com.example.cryptocurrency.presentation.utils.convertPeriodFromPercentToSeconds
import com.example.cryptocurrency.presentation.utils.getTimeHMSFromTimestamp
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.notify
import java.lang.StringBuilder

class ServiceOfLoadingData : Service() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private var notificationManager: NotificationManager? = null

    private lateinit var db: AppDatabase
    private lateinit var preferences: SharedPreferences
    private lateinit var prefsChangeListener: SharedPreferences.OnSharedPreferenceChangeListener

    private var timeout = 18
    private val handler = Handler()
    private var timer: Runnable? = null

    companion object {
        const val TAG = "ServiceOfLoadingData"
        const val FOREGROUND_SERVICE_ID = 1
        const val NOTIFICATION_CHANNEL_ID = "channel loading data id"
        const val NOTIFICATION_CHANNEL_NAME = "channel loading data name"
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
        }
        db = AppDatabase.getInstance(this)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        prefsChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, _ ->
            prefs?.let {
                if (it.contains(App.KEY_REFRESHING_PERIOD)) {
                    val periodPercent = it.getInt(App.KEY_REFRESHING_PERIOD, 30)
                    timeout = convertPeriodFromPercentToSeconds(periodPercent)
                    notificationBuilder.setContentTitle(String.format(getString(R.string.period_of_refreshing_label), timeout))
                    notificationManager?.notify(FOREGROUND_SERVICE_ID, notificationBuilder.build())
                }
            }
        }
        preferences = getSharedPreferences(App.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        preferences.registerOnSharedPreferenceChangeListener(prefsChangeListener)
        if (preferences.contains(App.KEY_REFRESHING_PERIOD)) {
            val periodPercent = preferences.getInt(App.KEY_REFRESHING_PERIOD, 30)
            timeout = convertPeriodFromPercentToSeconds(periodPercent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timer?.let {
            handler.removeCallbacks(it)
        }
        timer = object : Runnable {
            override fun run() {
                loadData()
                handler.postDelayed(this, timeout * 1000L)
                Log.d("RefreshingTest", "Refresh")
            }
        }
        timer?.let { handler.post(it) }
        notificationBuilder.setContentText("fsdfs00")
        notificationBuilder.setContentTitle(String.format(getString(R.string.period_of_refreshing_label), timeout))
        notificationBuilder.setSmallIcon(android.R.drawable.sym_def_app_icon)
        startForeground(FOREGROUND_SERVICE_ID, notificationBuilder.build())
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        clearResources()
        super.onDestroy()
    }

    fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo(20)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                db.coinInfoDao().insertCoins(it.listOfCoins.map { it.coinInfo })
                val symbols = db.coinInfoDao().getAllCoinsNames()
                loadPriceList(symbols)
            },{
                Log.d(TAG, it.message ?: "Unknown error")
            })
        compositeDisposable.add(disposable)
    }

    private fun loadPriceList(symbols: List<String?>) {
        val symbolsBuilder = StringBuilder()
        for (i in 0 until symbols.size) {
            symbolsBuilder.append(symbols[i])
            if (i != symbols.size - 1) {
                symbolsBuilder.append(",")
            }
        }
        val disposable = ApiFactory.apiService.getFullPriceList(symbolsBuilder.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                val listOfDisplayPriceInfo = mutableListOf<CoinPriceDisplayInfo>()
                val coinPriceDisplayInfoJSONObject = it.coinPriceDisplayInfoJSONObject
                coinPriceDisplayInfoJSONObject?.apply {
                    for (key in keySet()) {
                        val infoJsonObject = getAsJsonObject(key)
                        for (currency in infoJsonObject.keySet()) {
                            val priceInfo = Gson().fromJson(infoJsonObject.getAsJsonObject(currency), CoinPriceDisplayInfo::class.java)
                            listOfDisplayPriceInfo.add(priceInfo)
                        }
                    }
                }
                db.coinPriceDataToDisplayDao().insertPriceListToDisplay(listOfDisplayPriceInfo)
                val listOfFullPriceInfo = mutableListOf<CoinPriceInfo>()
                val coinPriceInfoJSONObject = it.coinPriceInfoJSONObject
                coinPriceInfoJSONObject?.apply {
                    for (key in keySet()) {
                        val infoJsonObject = getAsJsonObject(key)
                        for (currency in infoJsonObject.keySet()) {
                            val priceInfo = Gson().fromJson(infoJsonObject.getAsJsonObject(currency), CoinPriceInfo::class.java)
                            listOfFullPriceInfo.add(priceInfo)
                        }
                    }
                }
                db.coinPriceInfoDao().insertFullPriceList(listOfFullPriceInfo)
                notificationBuilder.setContentText(String.format(getString(R.string.last_update_label), getTimeHMSFromTimestamp(System.currentTimeMillis(), true)))
                notificationManager?.notify(FOREGROUND_SERVICE_ID, notificationBuilder.build())
            }, {
                Log.d(TAG, it.message ?: "Unknown error")
            })
        compositeDisposable.add(disposable)
    }

    private fun clearResources() {
        timer?.let {
            handler.removeCallbacks(it)
        }
        preferences.unregisterOnSharedPreferenceChangeListener(prefsChangeListener)
        compositeDisposable.dispose()
    }

}