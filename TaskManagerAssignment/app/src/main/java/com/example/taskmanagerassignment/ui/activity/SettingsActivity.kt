package com.example.taskmanagerassignment.ui.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagerassignment.MyApp
import com.example.taskmanagerassignment.ui.adapters.ColorAdapter
import com.example.taskmanagerassignment.databinding.ActivitySettingsBinding
import com.example.taskmanagerassignment.preference.PreferenceManager

class SettingsActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var preferenceManager: PreferenceManager
    private val colors = listOf(
        Color.parseColor("#6200EE"), // Default Purple
        Color.parseColor("#FF5722"), // Deep Orange
        Color.parseColor("#4CAF50"), // Green
        Color.parseColor("#2196F3"), // Blue
        Color.parseColor("#FF9800")  // Orange
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)
        binding.ivBack.setOnClickListener {
            finish()
        }
        val savedTheme = preferenceManager.getThemeMode()
        Log.e("TAG", "onCreate theme: $savedTheme")
        // Set selected RadioButton based on saved preference
        when (savedTheme) {
            AppCompatDelegate.MODE_NIGHT_NO -> {
                binding.rbLight.isChecked = true
                binding.rbDark.isChecked = false
            }

            AppCompatDelegate.MODE_NIGHT_YES -> {
                binding.rbDark.isChecked = true
                binding.rbLight.isChecked = false
            }
        }

        // Click Listeners for Light and Dark Mode
        binding.llLightMode.setOnClickListener {
            setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        binding.llDarkMode.setOnClickListener {
            setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        // RadioButton Click Listeners - Ensuring only one is selected at a time
        binding.rbLight.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.rbDark.isChecked = false
                setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.rbDark.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.rbLight.isChecked = false
                setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
        binding.rvColorPalette.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvColorPalette.adapter = ColorAdapter(colors) { selectedColor ->

            savePrimaryColor(selectedColor)
        }
    }

    private fun savePrimaryColor(color: Int) {
        val preferences = PreferenceManager(this)
        preferences.savePrimaryColor(color)

        MyApp.primaryColor = color
        window.statusBarColor = color
        binding.root.backgroundTintList = ColorStateList.valueOf(color)

        finish()
    }

    override fun onResume() {
        super.onResume()
        binding.toolbarSettings.setBackgroundDrawable(ColorDrawable(MyApp.primaryColor))

    }

    private fun setThemeMode(mode: Int) {
        preferenceManager.saveThemeMode(mode) // Save user preference
        AppCompatDelegate.setDefaultNightMode(mode)
        finish()
    }
}

