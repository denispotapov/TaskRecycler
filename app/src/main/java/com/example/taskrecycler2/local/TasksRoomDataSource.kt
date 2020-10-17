package com.example.taskrecycler2.local

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TasksRoomDataSource @Inject constructor(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher
) : TaskLocalDataSource {

    override fun getTasks(): LiveData<List<Task>> = taskDao.getAllTasks()

    override suspend fun insertAll(tasks: List<Task>) = withContext(ioDispatcher) {
        taskDao.insertAll(tasks)
    }

    override suspend fun insert(task: Task) = taskDao.insert(task)

    override suspend fun delete(task: Task) = taskDao.delete(task)

    override suspend fun deleteAllTasks() = taskDao.deleteAllTasks()

}