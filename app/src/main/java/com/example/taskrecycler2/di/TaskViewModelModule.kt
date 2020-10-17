package com.example.taskrecycler2.di

import androidx.lifecycle.ViewModel
import com.example.taskrecycler2.ui.TaskViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TaskViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModel::class)
    abstract fun bindTaskViewModel(viewModel: TaskViewModel): ViewModel

}