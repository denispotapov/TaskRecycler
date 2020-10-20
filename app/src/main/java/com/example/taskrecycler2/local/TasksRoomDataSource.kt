package com.example.taskrecycler2.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TasksRoomDataSource @Inject constructor(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher
) : TaskLocalDataSource {

    override fun getTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    override suspend fun insertAll(tasks: List<Task>) = withContext(ioDispatcher) {
        taskDao.insertAll(tasks)
    }

    override suspend fun insert(task: Task) = withContext(ioDispatcher) {
        taskDao.insert(task)
    }

    override suspend fun delete(task: Task) = withContext(ioDispatcher) {
        taskDao.delete(task)
    }

    override suspend fun deleteAllTasks() = withContext(ioDispatcher) {
        taskDao.deleteAllTasks()
    }

}