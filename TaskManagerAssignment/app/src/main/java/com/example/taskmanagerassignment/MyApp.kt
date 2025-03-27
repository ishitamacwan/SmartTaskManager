package com.example.taskmanagerassignment

import android.app.Application
import android.graphics.Color
import androidx.appcompat.app.AppCompatDelegate
import com.example.taskmanagerassignment.preference.PreferenceManager

class MyApp : Application() {
    companion object {
        var primaryColor: Int = Color.parseColor("#6200EE") // Default color
    }

    override fun onCreate() {
        super.onCreate()
        val preferences = PreferenceManager(this)
        primaryColor = preferences.getPrimaryColor()
        val savedTheme = preferences.getThemeMode()
        AppCompatDelegate.setDefaultNightMode(savedTheme)
    }
}