package com.example.taskrecycler2.remote

import com.example.taskrecycler2.TaskResponse
import retrofit2.Response
import retrofit2.http.GET

interface JsonPlaceHolderApi {

    @GET("todos")
    suspend fun getTasks() : Response<List<TaskResponse>>
}