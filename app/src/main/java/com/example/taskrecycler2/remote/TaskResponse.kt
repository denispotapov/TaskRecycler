package com.example.taskrecycler2.remote

import com.example.taskrecycler2.local.Task

data class TaskResponse(val id: Int?, val title: String?, val completed: String?) {

        fun toEntity() = Task(id ?: -1, title.orEmpty(), completed.orEmpty().toBoolean())

}