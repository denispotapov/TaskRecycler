package com.example.taskrecycler2.local

import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {

    fun getTasks(): Flow<List<Task>>

    suspend fun insertAll(tasks: List<Task>)

    suspend fun insert(task: Task)

    suspend fun delete(task: Task)

    suspend fun deleteAllTasks()

}