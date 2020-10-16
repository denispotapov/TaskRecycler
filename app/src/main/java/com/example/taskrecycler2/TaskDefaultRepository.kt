package com.example.taskrecycler2

import androidx.lifecycle.LiveData
import com.example.taskrecycler2.local.TaskLocalDataSource
import com.example.taskrecycler2.remote.Result
import com.example.taskrecycler2.remote.TaskRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TaskDefaultRepository @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource,
    private val taskRemoteDataSource: TaskRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : TaskRepository {

    override fun getTasks(): LiveData<List<Task>> = taskLocalDataSource.getTasks()

    override suspend fun insert(task: Task) {
        taskLocalDataSource.insert(task)
    }

    override suspend fun delete(task: Task) {
        taskLocalDataSource.delete(task)
    }

    override suspend fun deleteAllTasks() {
        taskLocalDataSource.deleteAllTasks()
    }

    override suspend fun requestTasks(remoteTasks: List<TaskResponse>)= withContext(ioDispatcher) {
        when(val getTasksResult = taskRemoteDataSource.getRemoteTasks(remoteTasks)) {
            is Result.Success -> {
                taskLocalDataSource.insertAll(getTasksResult.data.map { it.toEntity() })
            }
            is Result.Error -> {
                Timber.d(getTasksResult.exception)
            }
        }
    }
}