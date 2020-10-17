package com.example.taskrecycler2.local

import androidx.lifecycle.LiveData

interface TaskLocalDataSource {

    fun getTasks(): LiveData<List<Task>>

    suspend fun insertAll(tasks: List<Task>)

    suspend fun insert(task: Task)

    suspend fun delete(task: Task)

    suspend fun deleteAllTasks()

}