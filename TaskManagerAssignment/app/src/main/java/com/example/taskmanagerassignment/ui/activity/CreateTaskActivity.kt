package com.example.taskmanagerassignment.ui.activity

import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.viewModels
import com.example.taskmanagerassignment.common.DateTimePickerDialog
import com.example.taskmanagerassignment.MyApp
import com.example.taskmanagerassignment.R
import com.example.taskmanagerassignment.common.CommonClass.hideKeyboard
import com.example.taskmanagerassignment.databinding.ActivityCreateTaskBinding
import com.example.taskmanagerassignment.models.Task
import com.example.taskmanagerassignment.viewmodel.TaskViewModel
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateTaskActivity : BaseActivity() {
    lateinit var binding: ActivityCreateTaskBinding
    private var selectedDate: Long = System.currentTimeMillis()
    private val taskViewModel: TaskViewModel by viewModels()
    var selectedPriority = "Low" // Default selection
    private var selectedTime: Long? = System.currentTimeMillis()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        binding.tvDateValue.text =
            "${dateFormat.format(calendar.time)} , ${timeFormat.format(calendar.time)}"

        binding.tvDateValue.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentDate = dateFormat.format(calendar.time)  // Get current date
            val currentTime = timeFormat.format(calendar.time)  // Get current time

            DateTimePickerDialog(this) { selectedDate, selectedTime ->
                val finalDate = selectedDate ?: currentDate  // If date not selected, use current date
                val finalTime = selectedTime ?: currentTime  // If time not selected, use current time
                binding.tvDateValue.text = "$finalDate , $finalTime"
            }.show()
        }


        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.root.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                hideKeyboard(v)
                binding.root.requestFocus() // Remove focus from EditText
                v.performClick() // Ensure accessibility event is triggered
            }
            false
        }

        // Add Task Button
        binding.btnAddTask.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (title.isNotEmpty() && selectedPriority.isNotEmpty()) {
                val task = Task(
                    title = title.capitalize(Locale.ROOT),
                    description = description,
                    priority = selectedPriority,
                    dueDate = selectedDate,
                    dueTime = selectedTime
                )
                Log.d("TAG", "onCreate added: $task")
                taskViewModel.addTask(task)
                finish()
            } else {
                binding.etTitle.error = "Title is required"
            }
        }

        binding.chipGroupPriority.setOnCheckedChangeListener { group, checkedId ->
            val isDarkMode =
                (group.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as Chip
                if (chip.id == checkedId) {
                    chip.setTextColor(if (isDarkMode) Color.BLACK else Color.WHITE) // Adjust based on theme
                } else {
                    chip.setTextColor(if (isDarkMode) Color.WHITE else Color.BLACK) // Adjust based on theme
                }
            }
            selectedPriority = when (checkedId) {
                R.id.chipLow -> "Low"
                R.id.chipMedium -> "Medium"
                R.id.chipHigh -> "High"
                else -> "Low" // Default case
            }

        }
    }

    override fun onResume() {
        super.onResume()
        binding.toolbarAdd.setBackgroundDrawable(ColorDrawable(MyApp.primaryColor))
        binding.btnAddTask.backgroundTintList = ColorStateList.valueOf(MyApp.primaryColor)
    }

}