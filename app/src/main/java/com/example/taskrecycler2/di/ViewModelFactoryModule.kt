package com.example.taskrecycler2.di

import androidx.lifecycle.ViewModelProvider
import com.example.taskrecycler2.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(modelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}
