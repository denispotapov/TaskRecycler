package com.example.taskrecycler2.remote

interface TaskRemoteDataSource {

    suspend fun getRemoteTasks(remoteTasks: List<TaskResponse>): Result<List<TaskResponse>>
}