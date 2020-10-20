package com.example.taskrecycler2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskrecycler2.TaskRepository
import com.example.taskrecycler2.local.Task
import com.example.taskrecycler2.remote.Result
import com.example.taskrecycler2.remote.TaskResponse
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {

    val resultString = MutableLiveData("")

    val allTasks: LiveData<List<Task>> = taskRepository.getTasks()

    fun insert(task: Task) = viewModelScope.launch {
        taskRepository.insert(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        taskRepository.delete(task)
    }

    fun deleteAllTasks() = viewModelScope.launch {
        taskRepository.deleteAllTasks()
    }

    fun requestTask(remoteTasks: List<TaskResponse>) = viewModelScope.launch {
        when (val requestTasks = taskRepository.requestTasks(remoteTasks)) {
            is Result.Success -> {
                resultString.value = "Задачи загружены"
            }
            is Result.Error -> {
                Timber.d(requestTasks.exception)
                resultString.value = requestTasks.exception.toString()
            }
        }
    }
}
