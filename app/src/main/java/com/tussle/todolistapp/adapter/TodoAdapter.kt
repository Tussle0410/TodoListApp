package com.tussle.todolistapp.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.tussle.todolistapp.databinding.DialogEditBinding
import com.tussle.todolistapp.databinding.ListItemTodoBinding
import com.tussle.todolistapp.model.TodoInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
    fun addListItem(todoItem : TodoInfo){
        lstTodo.add(0,todoItem)
    }
    inner class TodoViewHolder(private val binding: ListItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem : TodoInfo){
            binding.tvContent.text = todoItem.todoContent
            binding.tvDate.text = todoItem.todoDate

            binding.btnRemove.setOnClickListener {
                AlertDialog.Builder(binding.root.context)
                    .setTitle("[주의]")
                    .setMessage("제거하시면 데이터는 복구되지 않습니다!\n정말 제거하시겠습니까?")
                    .setPositiveButton("제거",DialogInterface.OnClickListener { dialogInterface, i ->
                        lstTodo.remove(todoItem)
                        notifyDataSetChanged()
                        Toast.makeText(binding.root.context,"제거되었습니다.",Toast.LENGTH_SHORT).show()
                    })
                    .setNegativeButton("취소",DialogInterface.OnClickListener { dialogInterface, i ->

                    }).show()
            }
            binding.root.setOnClickListener {
                val bindingDialog = DialogEditBinding.inflate(LayoutInflater.from(binding.root.context),binding.root,false)
                bindingDialog.etMemo.setText(todoItem.todoContent)
                AlertDialog.Builder(binding.root.context)
                    .setTitle("To-Do 남기기")
                    .setView(bindingDialog.root)
                    .setPositiveButton("작성완료",DialogInterface.OnClickListener { dialogInterface, i ->
                        val todoItem = TodoInfo()
                        todoItem.todoContent = bindingDialog.etMemo.text.toString()
                        todoItem.todoDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                        lstTodo.set(adapterPosition,todoItem)
                        notifyDataSetChanged()
                    })
                    .setNegativeButton("취소",DialogInterface.OnClickListener { dialogInterface, i ->

                    })
                    .show()
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