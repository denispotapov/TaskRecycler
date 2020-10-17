package com.example.taskrecycler2.di

import android.app.Application
import android.content.Context
import com.example.taskrecycler2.TaskDefaultRepository
import com.example.taskrecycler2.TaskRepository
import com.example.taskrecycler2.local.TaskLocalDataSource
import com.example.taskrecycler2.local.TasksRoomDataSource
import com.example.taskrecycler2.remote.TaskRemoteDataSource
import com.example.taskrecycler2.remote.TaskRetrofitDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
abstract class ApplicationModuleBinds {

    @Singleton
    @Binds
    @AppContext
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun bindTaskLocalDataSource(
        tasksRoomDataSource: TasksRoomDataSource
    ): TaskLocalDataSource

    @Binds
    abstract fun bindTaskRemoteDataSource(
        taskRetrofitDataSource: TaskRetrofitDataSource
    ): TaskRemoteDataSource

    @Binds
    abstract fun bindTaskRepository(
        taskDefaultRepository: TaskDefaultRepository
    ): TaskRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppContext
