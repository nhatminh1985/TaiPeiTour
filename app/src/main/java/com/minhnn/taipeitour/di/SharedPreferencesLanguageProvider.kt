package com.minhnn.taipeitour.di

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesLanguageProvider(context: Context) : LanguageProvider {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    override fun getLanguage(): String {
        return sharedPreferences.getString("language", "en") ?: "en"
    }

    fun setLanguage(language: String) {
        sharedPreferences.edit().putString("language", language).apply()
    }
}