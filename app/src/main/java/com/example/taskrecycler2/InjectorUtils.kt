package com.example.taskrecycler2

import android.content.Context
import kotlinx.coroutines.GlobalScope

/*
object InjectorUtils {

    private fun getTaskRepository(context: Context): TaskDefaultRepository {
        return TaskDefaultRepository.getInstance(
            TaskDatabase.(context.applicationContext, GlobalScope).taskDao()
        )
    }

    fun provideTaskViewModelFactory(context: Context): TaskViewModelFactory {
        val repository = getTaskRepository(context)
        return TaskViewModelFactory(repository)
    }

}*/
