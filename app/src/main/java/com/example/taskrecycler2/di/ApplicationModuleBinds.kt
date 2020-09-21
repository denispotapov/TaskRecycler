package com.example.taskrecycler2.di

import com.example.taskrecycler2.TaskDefaultRepository
import com.example.taskrecycler2.TaskRepository
import com.example.taskrecycler2.local.TaskLocalDataSource
import com.example.taskrecycler2.local.TasksRoomDataSource
import com.example.taskrecycler2.remote.TaskRemoteDataSource
import com.example.taskrecycler2.remote.TaskRetrofitDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class ApplicationModuleBinds {

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