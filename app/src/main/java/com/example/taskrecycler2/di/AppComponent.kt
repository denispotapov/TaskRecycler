package com.example.taskrecycler2.di

import android.app.Application
import com.example.taskrecycler2.TaskApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuildersModule::class, ApplicationModule::class, ApplicationModuleBinds::class, ViewModelFactoryModule::class])

interface AppComponent : AndroidInjector<TaskApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}