package com.example.taskrecycler2

import android.content.Context
import kotlinx.coroutines.GlobalScope

object InjectorUtils {

    private fun getTaskRepository(context: Context): TaskRepository {
        return TaskRepository.getInstance(
            TaskDatabase.getInstance(context.applicationContext, GlobalScope).taskDao()
        )
    }

    fun provideTaskViewModelFactory(context: Context): TaskViewModelFactory {
        val repository = getTaskRepository(context)
        return TaskViewModelFactory(repository)
    }

}