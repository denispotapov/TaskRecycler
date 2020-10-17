package com.example.taskrecycler2

import androidx.lifecycle.LiveData
import com.example.taskrecycler2.local.Task
import com.example.taskrecycler2.remote.TaskResponse

interface TaskRepository {

    fun getTasks(): LiveData<List<Task>>

    suspend fun requestTasks(remoteTasks: List<TaskResponse>)

    suspend fun insert(task: Task)

    suspend fun delete(task: Task)

    suspend fun deleteAllTasks()
}