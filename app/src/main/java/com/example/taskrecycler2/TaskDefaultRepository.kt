package com.example.taskrecycler2

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.taskrecycler2.local.Task
import com.example.taskrecycler2.local.TaskLocalDataSource
import com.example.taskrecycler2.remote.Result
import com.example.taskrecycler2.remote.TaskRemoteDataSource
import com.example.taskrecycler2.remote.TaskResponse
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
    override fun getTasks(): LiveData<List<Task>> = taskLocalDataSource.getTasks().asLiveData()

    override suspend fun insert(task: Task) =
        withContext(ioDispatcher) {
            taskLocalDataSource.insert(task)
        }

    override suspend fun delete(task: Task) =
        withContext(ioDispatcher) {
            taskLocalDataSource.delete(task)
        }

    override suspend fun deleteAllTasks() =
        withContext(ioDispatcher) {
            taskLocalDataSource.deleteAllTasks()
        }

    override suspend fun requestTasks(remoteTasks: List<TaskResponse>): Result<Unit> =
        withContext(ioDispatcher) {
            when (val getTasksResult = taskRemoteDataSource.getRemoteTasks(remoteTasks)) {
                is Result.Success -> {
                    taskLocalDataSource.insertAll(getTasksResult.data.map { it.toEntity() })
                    Result.Success(Unit)
                }
                is Result.Error -> {
                    Timber.d(getTasksResult.exception)
                    Result.Error(getTasksResult.exception)
                }
            }
        }
}


