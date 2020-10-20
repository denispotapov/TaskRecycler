package com.example.taskrecycler2.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskrecycler2.BuildConfig
import com.example.taskrecycler2.R
import com.example.taskrecycler2.ViewModelProviderFactory
import com.example.taskrecycler2.local.Task
import com.example.taskrecycler2.remote.TaskResponse
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.task_item.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    lateinit var taskViewModel: TaskViewModel

    val taskAdapter = TaskAdapter()
    private val remoteTasks: List<TaskResponse> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        taskViewModel = ViewModelProvider(this, providerFactory).get(TaskViewModel::class.java)

        taskViewModel.allTasks.observe(this, Observer {
            taskAdapter.submitList(it)
        })

        taskViewModel.resultString.observe(this) { str ->
            Toast.makeText(this, str, Toast.LENGTH_LONG).show()
        }

        add_task.setOnClickListener {
            taskViewModel.insert(Task("пустое поле", false))
        }

        taskViewModel.requestTask(remoteTasks)

        initRecycler()
        deleteTask()
        saveTask()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all -> {
                edit_task_text.clearFocus()
                    taskViewModel.deleteAllTasks()
                    Toast.makeText(this, "Все задачи удалены", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecycler() {
        recycler_view.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun deleteTask() {
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                edit_task_text.clearFocus()
                taskViewModel.delete(taskAdapter.getTaskAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Задача удалена", Toast.LENGTH_SHORT).show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler_view)
    }

    private fun saveTask() {
        taskAdapter.setOnItemClickListener(object : TaskAdapter.OnItemClickListener {
            override fun onChange(task: Task) {
                edit_task_text.clearFocus()
                taskViewModel.insert(task)
            }

            override fun onCheck(task: Task) {
                taskViewModel.insert(task)
            }
        })
    }
}


