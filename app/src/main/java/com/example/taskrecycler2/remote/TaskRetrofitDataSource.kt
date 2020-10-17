package com.example.taskrecycler2.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
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
                   Result.Error(Exception())
                }

            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}


