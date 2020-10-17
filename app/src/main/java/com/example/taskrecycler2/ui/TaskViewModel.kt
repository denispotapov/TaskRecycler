package com.example.taskrecycler2.ui

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskrecycler2.local.Task
import com.example.taskrecycler2.TaskDefaultRepository
import com.example.taskrecycler2.remote.TaskResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class TaskViewModel @Inject constructor(private val defaultRepository: TaskDefaultRepository) :
    ViewModel() {

    val allTasks: LiveData<List<Task>> = defaultRepository.getTasks()

    fun insert(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        defaultRepository.insert(task)
    }

    fun delete(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        defaultRepository.delete(task)
    }

    fun deleteAllTasks() = viewModelScope.launch(Dispatchers.IO) {
        defaultRepository.deleteAllTasks()
    }

    fun requestTask(remoteTasks: List<TaskResponse>) = viewModelScope.launch(Dispatchers.IO) {
        defaultRepository.requestTasks(remoteTasks)

    }
}
