package com.example.taskrecycler2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Task::class], version = 1, exportSchema = false)

internal abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    private class TaskDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.taskDao())
                }
            }
        }

        suspend fun populateDatabase(taskDao: TaskDao) {

            taskDao.insert(Task("Задача 1", false))
            taskDao.insert(Task("Задача 2", false))
            taskDao.insert(Task("Задача 3", false))
        }
    }

    companion object {

        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): TaskDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java, "task_database"
                    ).addCallback(TaskDatabaseCallback(scope)).build()
                }
                return INSTANCE!!
            }
        }
    }
}