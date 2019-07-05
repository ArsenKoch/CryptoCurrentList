package com.example.cryptocurrency.presentation

import android.app.Application
import android.content.Intent
import android.os.Handler
import androidx.core.content.ContextCompat
import com.example.cryptocurrency.data.repo.ServiceOfLoadingData
import com.facebook.stetho.Stetho

class App: Application() {

    companion object {
        const val SHARED_PREFS_NAME = "main prefs"
        const val KEY_REFRESHING_PERIOD = "refreshing period"
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

}