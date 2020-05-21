package com.example.taskrecycler2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_item.view.*


class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskHolder>(TaskDiffCallback()) {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskHolder {
        return TaskHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val current = getItem(position)

        holder.taskText.setText(current.task)
        holder.completeTask.isChecked = current.complete
    }

    fun getTaskAt(position: Int): Task {
        return getItem(position)
    }

    inner class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskText: EditText = itemView.edit_task_text
        val completeTask: CheckBox = itemView.task_complete

        init {
            taskText.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    val task = getItem(adapterPosition)
                    task.task = taskText.text.toString()
                    listener?.onChange(task)

                }
            }
            completeTask.setOnCheckedChangeListener { buttonView, isChecked ->
                val task = getItem(adapterPosition)
                task.complete = isChecked
                listener?.onCheck(task)
            }
        }
    }

    interface OnItemClickListener {
        fun onChange(task: Task)
        fun onCheck(task: Task)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.task == newItem.task && oldItem.complete == newItem.complete
    }
}