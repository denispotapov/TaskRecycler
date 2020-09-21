package com.example.taskrecycler2

data class TaskResponse(val id: Int?, val title: String?, val completed: String?) {

        fun toEntity() = Task(id ?: -1, title.orEmpty(), completed.orEmpty().toBoolean())

}