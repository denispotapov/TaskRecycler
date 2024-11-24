package com.example.taskrecycler2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskrecycler2.databinding.TaskItemBinding
import com.example.taskrecycler2.local.Task


class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskHolder>(TaskDiffCallback()) {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       return TaskHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val current = getItem(position)

        holder.taskText.setText(current.task)
        holder.completeTask.isChecked = current.complete
    }

    fun getTaskAt(position: Int): Task {
        return getItem(position)
    }

    inner class TaskHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val taskText: EditText = binding.editTaskText
        val completeTask: CheckBox = binding.taskComplete

        init {
            taskText.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    val task = getItem(adapterPosition)
                    task.task = binding.editTaskText.text.toString()
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