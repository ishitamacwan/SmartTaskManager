package com.example.taskmanagerassignment.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String?,
    val priority: String,
    val dueDate: Long,
    val dueTime: Long? = null,  // Store time separately (null means "All Day")
    val isCompleted: Boolean = false
): Parcelable