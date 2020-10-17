package com.example.taskrecycler2.di

import com.example.taskrecycler2.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [TaskViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity
}