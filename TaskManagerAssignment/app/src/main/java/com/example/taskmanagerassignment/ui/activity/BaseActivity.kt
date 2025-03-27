package com.example.taskmanagerassignment.ui.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.taskmanagerassignment.MyApp
import com.example.taskmanagerassignment.preference.PreferenceManager

open class BaseActivity : AppCompatActivity() {
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        preferenceManager = PreferenceManager(this)
        AppCompatDelegate.setDefaultNightMode(preferenceManager.getThemeMode())

        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        MyApp.primaryColor = preferenceManager.getPrimaryColor()
        applyPrimaryColor()
    }
    private fun applyPrimaryColor() {
        window.statusBarColor = MyApp.primaryColor
        supportActionBar?.setBackgroundDrawable(ColorDrawable(MyApp.primaryColor))
    }
}
