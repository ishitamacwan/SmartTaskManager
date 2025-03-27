package com.example.taskmanagerassignment.ui.fragments

import SwipeToManageTaskCallback
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagerassignment.MyApp
import com.example.taskmanagerassignment.R
import com.example.taskmanagerassignment.ui.activity.TaskDetailActivity
import com.example.taskmanagerassignment.ui.adapters.TaskAdapter
import com.example.taskmanagerassignment.common.SortType
import com.example.taskmanagerassignment.common.TaskMotivationalMessages
import com.example.taskmanagerassignment.databinding.FragmentMyTasksBinding
import com.example.taskmanagerassignment.models.Task
import com.example.taskmanagerassignment.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar

class MyTasksFragment : Fragment(), SortBottomSheetFragment.SortSelectionListener {
    private lateinit var binding: FragmentMyTasksBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var taskAdapter: TaskAdapter
    private var currentSortType = SortType.DUEDATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyTasksBinding.inflate(inflater, container, false)

        taskAdapter = TaskAdapter(
            requireContext(),
            onItemClick = { selectedTask ->
                val intent = Intent(requireContext(), TaskDetailActivity::class.java)
                intent.putExtra("TASK", selectedTask)
                startActivity(intent)
            }
        )
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTasks.adapter = taskAdapter

        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            Log.e("TAG", "onCreateView taskss: $tasks")
            if (tasks.isNullOrEmpty()) {
                val motivationalMessage = TaskMotivationalMessages.getMotivationalMessage()

                // For specific empty state
                val specificEmptyStateMessage = TaskMotivationalMessages.getEmptyStateMessage(
                    TaskMotivationalMessages.EmptyStateType.NO_TASKS
                )

                binding.clNoTask.visibility = View.VISIBLE
                binding.tvNoTask.text = specificEmptyStateMessage
                binding.tvNoTaskDes.text = motivationalMessage
                binding.recyclerViewTasks.visibility = View.GONE
                binding.taskProgressView.visibility = View.GONE
            } else {
                binding.clNoTask.visibility = View.GONE
                binding.recyclerViewTasks.visibility = View.VISIBLE
                binding.taskProgressView.visibility = View.VISIBLE
                // Calculate completed percentage
                val totalTasks = tasks.size
                val completedTasks = tasks.count { it.isCompleted }
                val progress =
                    if (totalTasks > 0) (completedTasks.toFloat() / totalTasks.toFloat()) * 100 else 0f

                binding.taskProgressView.setProgress(progress)
                taskAdapter.setTasks(applySorting(tasks, currentSortType))
            }

        }
        taskViewModel.sortType.observe(viewLifecycleOwner) { sortType ->
            currentSortType = sortType
            taskViewModel.allTasks.value?.let { tasks ->
                taskAdapter.setTasks(applySorting(tasks, sortType))
            }
        }
        setupSwipeToManageTask()
        return binding.root
    }

    override fun onSortSelected(sortType: SortType) {
        currentSortType = sortType
        taskViewModel.allTasks.value?.let { tasks ->
            taskAdapter.setTasks(applySorting(tasks, sortType))
        }
    }

    private fun applySorting(tasks: List<Task>, sortType: SortType): List<Task> {
        return when (sortType) {
            SortType.DUEDATE -> tasks.sortedBy { it.dueDate }
            SortType.PRIORITY -> tasks.sortedWith(compareBy {
                when (it.priority) {
                    "High" -> 1
                    "Medium" -> 2
                    "Low" -> 3
                    else -> 4
                }
            })

            SortType.ALPHABETICAL -> tasks.sortedBy { it.title }
        }
    }

    private fun setupSwipeToManageTask() {
        val swipeToManageCallback = SwipeToManageTaskCallback(
            requireContext(),
            onToggleComplete = { position, _ ->
                val task = taskAdapter.getTaskAt(position)
                taskViewModel.updateTaskStatus(task.id, !task.isCompleted) // Mark as completed
            },
            onDelete = { position ->

                val deletedTask = taskAdapter.getTaskAt(position)

                // Remove from DB
                taskViewModel.deleteTask(deletedTask)

                // Show Undo Snackbar
                Snackbar.make(binding.recyclerViewTasks, "Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        taskViewModel.insertTask(deletedTask) // Restore task
                    }.show()
            },
            onMove = { fromPosition, toPosition ->
                taskAdapter.moveTask(fromPosition, toPosition)
            }
        )

        ItemTouchHelper(swipeToManageCallback).attachToRecyclerView(binding.recyclerViewTasks)


    }

    override fun onResume() {
        super.onResume()
        binding.taskProgressView.progressPaint.color = MyApp.primaryColor
        binding.taskProgressView.textPaint.color = resources.getColor(R.color.black)
        binding.taskProgressView.descriptionPaint.color = resources.getColor(R.color.black)
    }
}
