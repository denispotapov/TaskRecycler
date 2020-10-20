package com.example.taskrecycler2.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.taskrecycler2.local.Task
import com.example.taskrecycler2.local.TaskDao
import com.example.taskrecycler2.local.TaskDatabase
import com.example.taskrecycler2.remote.JsonPlaceHolderApi
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao =
        taskDatabase.taskDao()

    @Singleton
    @Provides
    fun provideDatabase(@AppContext context: Context): TaskDatabase {
        var INSTANCE: TaskDatabase? = null

        INSTANCE = INSTANCE ?: Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java, "task_database"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.taskDao())
                    }
                }
            }

            suspend fun populateDatabase(taskDao: TaskDao) {
                taskDao.insert(Task(201, "Задача 1", false))
                taskDao.insert(Task(202, "Задача 2", false))
                taskDao.insert(Task(203, "Задача 3", false))
            }
        }).build()
        return INSTANCE
    }

    @Singleton
    @Provides
    fun provideJsonPlaceHolderApi(): JsonPlaceHolderApi = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JsonPlaceHolderApi::class.java)
}

