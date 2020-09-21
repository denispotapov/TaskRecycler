package com.example.taskrecycler2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task (var task: String, var complete: Boolean) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(id: Int, task: String, complete: Boolean) : this(task, complete) {
        this.id = id
    }

}

    /*@PrimaryKey(autoGenerate = true)
    var id: Int = 0*/


//indices = [Index(value = ["task"], unique = true)]