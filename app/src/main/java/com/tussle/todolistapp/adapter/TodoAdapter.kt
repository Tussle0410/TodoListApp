package com.tussle.todolistapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tussle.todolistapp.databinding.ListItemTodoBinding
import com.tussle.todolistapp.model.TodoInfo

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var lstTodo : ArrayList<TodoInfo> = ArrayList()
    init {
        val todoItem1 = TodoInfo()
        todoItem1.todoContent = "컴퓨터 사용시간 줄이기"
        todoItem1.todoDate = "2022-06-01 22:23"
        lstTodo.add(todoItem1)

        val todoItem2 = TodoInfo()
        todoItem2.todoContent = "배달 음식 줄이기"
        todoItem2.todoDate = "2022-05-01 22:23"
        lstTodo.add(todoItem2)

        val todoItem3 = TodoInfo()
        todoItem3.todoContent = "늦잠 자지 말기"
        todoItem3.todoDate = "2022-04-01 22:23"
        lstTodo.add(todoItem3)
    }
    class TodoViewHolder(private val binding: ListItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem : TodoInfo){
            binding.tvContent.text = todoItem.todoContent
            binding.tvDate.text = todoItem.todoDate

            binding.btnRemove.setOnClickListener {

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        val binding = ListItemTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        holder.bind(lstTodo[position])
    }

    override fun getItemCount(): Int {
        return lstTodo.size
    }

}