package com.example.taskmanagerassignment.ui.adapters

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagerassignment.R
import com.example.taskmanagerassignment.databinding.ItemTaskBinding
import com.example.taskmanagerassignment.models.Task
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale

class TaskAdapter(
    var context: Context,
    private val onItemClick: (Task) -> Unit,
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = emptyList<Task>()

    fun setTasks(tasks: List<Task>) {
        this.taskList = tasks
        notifyDataSetChanged()
    }

    fun moveTask(fromPosition: Int, toPosition: Int) {
        Collections.swap(taskList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        if (task.isCompleted) {
            holder.binding.tvTitle.paintFlags =
                holder.binding.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.binding.tvTitle.paintFlags =
                holder.binding.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        val priorityColor = when (task.priority) {
            "Low" -> ContextCompat.getColor(holder.itemView.context, R.color.priority_low)
            "Medium" -> ContextCompat.getColor(holder.itemView.context, R.color.priority_medium)
            "High" -> ContextCompat.getColor(holder.itemView.context, R.color.priority_high)
            else -> ContextCompat.getColor(holder.itemView.context, R.color.priority_low) // Default to Low
        }
        holder.binding.llPriority.setBackgroundColor(priorityColor)

        holder.binding.tvTitle.text = task.title
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(Date(task.dueDate))
        holder.binding.tvTime.text = formattedDate

        holder.binding.root.setOnClickListener { onItemClick(task) }

    }

    fun getTaskAt(position: Int): Task {
        return taskList[position]
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}