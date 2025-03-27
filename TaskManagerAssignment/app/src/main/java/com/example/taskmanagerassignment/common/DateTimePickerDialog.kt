package com.example.taskmanagerassignment.common

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import com.example.taskmanagerassignment.MyApp
import com.example.taskmanagerassignment.databinding.DialogDateTimePickerBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateTimePickerDialog(
    context: Context,
    private val onDateTimeSelected: (date: String, time: String) -> Unit
) :
    Dialog(context) {

    private var binding: DialogDateTimePickerBinding =
        DialogDateTimePickerBinding.inflate(layoutInflater)
    private var selectedDate: String = ""
    private var selectedTime: String = ""
    private val primaryColor = MyApp.primaryColor
    init {
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initViews()
    }

    private fun initViews() {
        // Date selection from CalendarView

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val calendar = Calendar.getInstance()

            // Set today's date as default
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            selectedDate = dateFormat.format(calendar.time) // Store today's date initially

            binding.calendarView.setOnDateChangedListener { datePicker, year, month, dayOfMonth ->
                // Set the selected date in the Calendar instance
                calendar.set(year, month, dayOfMonth)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                // Store the selected date in a formatted string
                selectedDate = dateFormat.format(calendar.time)
            }
        }



        // Time selection
        binding.tvSetTime.setOnClickListener {
            showTimePicker()
        }

        // Done button
        binding.tvDone.setOnClickListener {
            onDateTimeSelected(selectedDate, selectedTime)
            dismiss()
        }

        // Cancel button
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(context, { _, h, m ->
            val cal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, h)
                set(Calendar.MINUTE, m)
            }
            selectedTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(cal.time)
            binding.tvSetTime.text = selectedTime
        }, hour, minute, false).show()
    }
}

