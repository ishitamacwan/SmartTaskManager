package com.example.taskmanagerassignment.preference

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatDelegate

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "TaskManager"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_PRIMARY_COLOR = "primary_color"  // Add this line


    }

    // Save Theme Mode
    fun saveThemeMode(mode: Int) {
        sharedPreferences.edit().putInt(KEY_THEME_MODE, mode).apply()
    }

    // Get Saved Theme Mode (Default: Light Mode)
    fun getThemeMode(): Int {
        return sharedPreferences.getInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_NO)
    }

    //  Save Primary Color
    fun savePrimaryColor(color: Int) {
        sharedPreferences.edit().putInt(KEY_PRIMARY_COLOR, color).apply()
    }

    //  Get Primary Color (Default: Blue)
    fun getPrimaryColor(): Int {
        return sharedPreferences.getInt(KEY_PRIMARY_COLOR, Color.parseColor("#6200EE"))
    }
}
