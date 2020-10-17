package com.example.taskrecycler2

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.taskrecycler2.di.AppContext
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
    private val ioDispatcher: CoroutineDispatcher,
    @AppContext
    private val context: Context
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

    override suspend fun requestTasks(remoteTasks: List<TaskResponse>): Unit =
        withContext(ioDispatcher) {
            when (val getTasksResult = taskRemoteDataSource.getRemoteTasks(remoteTasks)) {
                is Result.Success -> {
                    Handler(Looper.getMainLooper()).post(Runnable {
                        Toast.makeText(context.applicationContext, "Задачи загружены", Toast.LENGTH_LONG)
                            .show()
                    })
                    taskLocalDataSource.insertAll(getTasksResult.data.map { it.toEntity() })
                }
                is Result.Error -> {
                    Timber.d(getTasksResult.exception)
                    Handler(Looper.getMainLooper()).post(Runnable {
                        Toast.makeText(context.applicationContext, "Нет подключения к сети", Toast.LENGTH_LONG)
                            .show()
                    })
                }
            }
        }
}


