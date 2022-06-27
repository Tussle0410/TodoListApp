package com.tussle.todolistapp.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.tussle.todolistapp.database.TodoDatabase
import com.tussle.todolistapp.databinding.DialogEditBinding
import com.tussle.todolistapp.databinding.ListItemTodoBinding
import com.tussle.todolistapp.model.TodoInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var lstTodo : ArrayList<TodoInfo> = ArrayList()
    private lateinit var roomDatabase : TodoDatabase
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
                        CoroutineScope(Dispatchers.IO).launch {
                            val innerLstTodo = roomDatabase.todoDao().getAllReadDate()
                            for(item in innerLstTodo){
                                if(item.todoContent == todoItem.todoContent && item.todoDate == todoItem.todoDate){
                                    roomDatabase.todoDao().deleteTodoData(item)
                                }
                            }
                            lstTodo.remove(todoItem)
                            (binding.root.context as Activity).runOnUiThread {
                                notifyDataSetChanged()
                                Toast.makeText(binding.root.context,"제거되었습니다.",Toast.LENGTH_SHORT).show()
                            }
                        }
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
                    .setPositiveButton("수정완료",DialogInterface.OnClickListener { dialogInterface, i ->
                        CoroutineScope(Dispatchers.IO).launch {
                            val innerLstTodo = roomDatabase.todoDao().getAllReadDate()
                            for(item in innerLstTodo){
                                if (item.todoContent == todoItem.todoContent && item.todoDate == todoItem.todoDate){
                                    item.todoContent = bindingDialog.etMemo.text.toString()
                                    item.todoDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                                    roomDatabase.todoDao().updateTodoData(item)
                                }
                            }
                        }
                        todoItem.todoContent = bindingDialog.etMemo.text.toString()
                        todoItem.todoDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                        lstTodo.set(adapterPosition,todoItem)
                        (binding.root.context as Activity).runOnUiThread{
                                notifyDataSetChanged()
                        }
                    })
                    .setNegativeButton("취소",DialogInterface.OnClickListener { dialogInterface, i ->

                    })
                    .show()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        val binding = ListItemTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        roomDatabase = TodoDatabase.getInstance(binding.root.context)!!
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        holder.bind(lstTodo[position])
    }

    override fun getItemCount(): Int {
        return lstTodo.size
    }

}