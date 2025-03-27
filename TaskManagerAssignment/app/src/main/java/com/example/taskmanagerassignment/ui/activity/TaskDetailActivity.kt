package com.example.taskmanagerassignment.ui.activity

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.taskmanagerassignment.MyApp
import com.example.taskmanagerassignment.R
import com.example.taskmanagerassignment.databinding.ActivityTaskDetailBinding
import com.example.taskmanagerassignment.models.Task
import com.example.taskmanagerassignment.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityTaskDetailBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private var task: Task? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        task = intent.getParcelableExtra<Task>("TASK")
        task?.let { it ->
            binding.tvTitleValue.text = it.title
            binding.tvDesValue.text = it.description
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

            val date = dateFormat.format(Date(it.dueDate))
            val time = it.dueTime?.let { it1 -> Date(it1) }?.let { it2 -> timeFormat.format(it2) }
            binding.tvDateValue.text = date
            binding.tvTimeValue.text = time
            updateButtonText(it.isCompleted)
            updateChips(it.priority)
            updateProgress(it.isCompleted)
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnCompleted.setOnClickListener {
            task?.let { currentTask ->
                val newStatus = !currentTask.isCompleted // Toggle status
                taskViewModel.updateTaskStatus(currentTask.id, newStatus) // Update in DB

                // Update UI immediately
                task = currentTask.copy(isCompleted = newStatus) // Reassign updated task
                updateButtonText(newStatus) // Update button text
                updateProgress(newStatus)
            }
        }
        binding.ivMenu.setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.task_menu) // Create task_menu.xml in res/menu
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_delete -> {
                        task?.let { showDeleteConfirmationDialog(it) } // Call delete function
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private fun showDeleteConfirmationDialog(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete") { _, _ ->
                taskViewModel.deleteTask(task)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateButtonText(isCompleted: Boolean) {
        binding.btnCompleted.text = if (isCompleted) {
            "Mark Uncompleted"
        } else {
            "Mark Completed"
        }
    }

    private fun updateChips(priority: String) {
        when (priority) {
            "Low" -> {
                binding.chipPriority.text = "Low Priority"
                binding.chipPriority.setChipBackgroundColorResource(R.color.light_green)
                binding.chipPriority.setChipStrokeColorResource(R.color.dark_green)
                binding.chipPriority.setTextColor(ContextCompat.getColor(this, R.color.dark_green))
            }

            "Medium" -> {
                binding.chipPriority.text = "Medium Priority"
                binding.chipPriority.setChipBackgroundColorResource(R.color.light_yellow)
                binding.chipPriority.setChipStrokeColorResource(R.color.dark_yellow)
                binding.chipPriority.setTextColor(ContextCompat.getColor(this, R.color.dark_yellow))

            }

            "High" -> {
                binding.chipPriority.text = "High Priority"
                binding.chipPriority.setChipBackgroundColorResource(R.color.light_red)
                binding.chipPriority.setChipStrokeColorResource(R.color.dark_red)
                binding.chipPriority.setTextColor(ContextCompat.getColor(this, R.color.dark_red))
            }
        }
    }

    fun updateProgress(progress: Boolean) {
        if (!progress) {
            binding.chipProgress.text = "Pending"
            binding.chipProgress.setChipBackgroundColorResource(R.color.light_purple)
            binding.chipProgress.setChipStrokeColorResource(R.color.dark_purple)
            binding.chipProgress.setTextColor(ContextCompat.getColor(this, R.color.dark_purple))

        } else {
            binding.chipProgress.text = "Completed"
            binding.chipProgress.setChipBackgroundColorResource(R.color.light_purple)
            binding.chipProgress.setChipStrokeColorResource(R.color.dark_purple)
            binding.chipProgress.setTextColor(ContextCompat.getColor(this, R.color.dark_purple))
        }
    }

    override fun onResume() {
        super.onResume()
        binding.toolbarDetail.setBackgroundDrawable(ColorDrawable(MyApp.primaryColor))
        binding.btnCompleted.backgroundTintList = ColorStateList.valueOf(MyApp.primaryColor)

    }
}