package com.example.taskrecycler2.remote

import com.example.taskrecycler2.TaskResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRetrofitDataSource @Inject constructor(
    private val jsonPlaceHolderApi: JsonPlaceHolderApi,
    private val ioDispatcher: CoroutineDispatcher
) : TaskRemoteDataSource {
    override suspend fun getRemoteTasks(remoteTasks: List<TaskResponse>): Result<List<TaskResponse>> =
        withContext(ioDispatcher) {
            try {
                val response = jsonPlaceHolderApi.getTasks()
                val taskResponseList = response.body()
                return@withContext if (response.isSuccessful && !taskResponseList.isNullOrEmpty()) {
                    Result.Success(taskResponseList)
                } else {
                    Result.Error(
                        CustomException(message = "Не удалось получить задачи")
                    )
                }
            } catch (t: Throwable) {
                return@withContext Result.Error(CustomException(cause = t))
            }
        }
}


