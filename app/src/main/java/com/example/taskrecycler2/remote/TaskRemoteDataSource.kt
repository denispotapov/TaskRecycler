package com.example.taskrecycler2.remote

import com.example.taskrecycler2.TaskResponse

interface TaskRemoteDataSource {

    suspend fun getRemoteTasks(remoteTasks: List<TaskResponse>): Result<List<TaskResponse>>
}