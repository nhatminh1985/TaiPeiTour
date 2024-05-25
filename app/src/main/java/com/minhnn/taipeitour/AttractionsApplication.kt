package com.minhnn.taipeitour

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AttractionsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "comming_inside_onCreate")
    }

    companion object {
        const val TAG = "AttractionsApplication"
    }
}