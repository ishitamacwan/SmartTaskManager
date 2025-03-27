package com.example.taskmanagerassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerassignment.common.SortType
import com.example.taskmanagerassignment.database.TaskDatabase
import com.example.taskmanagerassignment.models.Task
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = TaskDatabase.getDatabase(application).taskDao()

    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()
    val completedTasks: LiveData<List<Task>> = taskDao.getCompletedTasks()
    val pendingTasks: LiveData<List<Task>> = taskDao.getPendingTasks()
    private val _sortType = MutableLiveData<SortType>(SortType.DUEDATE)
    val sortType: LiveData<SortType> get() = _sortType

    fun addTask(task: Task) {
        viewModelScope.launch {
            taskDao.insertTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }

    fun insertTask(task: Task) = viewModelScope.launch {
        taskDao.insertTask(task)
    }

    fun updateTaskStatus(id: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            taskDao.updateTaskStatus(id, isCompleted)
        }
    }

    fun updateSortType(sortType: SortType) {
        _sortType.value = sortType
    }
}
