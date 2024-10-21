package com.example.damienliscio_comp304lab2_ex1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

enum class TaskStatus {
    Incomplete, InProgress, Complete
}
data class TaskData(
    var taskTitle : String,
    var taskDueDate : String,
    var taskStatus: TaskStatus = TaskStatus.Incomplete
)

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskData>>(emptyList())
    val tasks: StateFlow<List<TaskData>> = _tasks

    fun addTask(taskTitle: String, taskDueDate: String, taskStatus: TaskStatus) {
        val newTask = TaskData(taskTitle = taskTitle, taskDueDate = taskDueDate, taskStatus = taskStatus)
        _tasks.update { currentTasks -> currentTasks + newTask }
    }

    fun updateTaskStatus(task: TaskData, newStatus: TaskStatus) {
        _tasks.update { tasks ->
            tasks.map { currentTask ->
                if (currentTask.taskTitle == task.taskTitle) {
                    currentTask.copy(taskStatus = newStatus)
                } else {
                    currentTask
                }
            }
        }
    }

    fun editTask(task: TaskData, newTitle: String, newDueDate: String) {
        _tasks.value = _tasks.value.map { t ->
            if (t == task) {
                t.copy(taskTitle = newTitle, taskDueDate = newDueDate)
            } else {
                t
            }
        }
    }

    fun loadSampleTasks() {
        addTask(
            taskTitle = "Call Parents",
            taskDueDate = "2024-10-10",
            taskStatus = TaskStatus.Complete
        )
        addTask(
            taskTitle = "Renew Car Insurance",
            taskDueDate = "2024-10-30",
            taskStatus = TaskStatus.Incomplete
        )
        addTask(
            taskTitle = "Clean the House",
            taskDueDate = "2024-10-12",
            taskStatus = TaskStatus.InProgress
        )
    }
}



