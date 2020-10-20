package com.example.taskrecycler2

import com.example.taskrecycler2.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class TaskApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}