package com.example.taskrecycler2


import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TaskViewModel @ViewModelInject constructor(private val defaultRepository: TaskDefaultRepository) :
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
