package com.example.taskrecycler2

import com.example.taskrecycler2.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber


class TaskApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}