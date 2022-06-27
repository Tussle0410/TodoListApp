package com.tussle.todolistapp.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.room.RoomDatabase
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.tussle.todolistapp.adapter.TodoAdapter
import com.tussle.todolistapp.database.TodoDatabase
import com.tussle.todolistapp.databinding.ActivityListMainBinding
import com.tussle.todolistapp.databinding.DialogEditBinding
import com.tussle.todolistapp.model.TodoInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ListMainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityListMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var roomDatabase : TodoDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this){}
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        todoAdapter = TodoAdapter()
        binding.rvTodo.adapter = todoAdapter

        roomDatabase = TodoDatabase.getInstance(applicationContext)!!

        CoroutineScope(Dispatchers.IO).launch {
            val lstTodo = roomDatabase.todoDao().getAllReadDate() as ArrayList<TodoInfo>
            for(todoItem in lstTodo){
                todoAdapter.addListItem(todoItem)
            }
            runOnUiThread {
                todoAdapter.notifyDataSetChanged()
            }
        }

        binding.btnWrite.setOnClickListener {
            val bindingDialog = DialogEditBinding.inflate(LayoutInflater.from(binding.root.context),binding.root,false)
            AlertDialog.Builder(this)
                .setTitle("To-Do 남기기")
                .setView(bindingDialog.root)
                .setPositiveButton("작성완료",DialogInterface.OnClickListener { dialogInterface, i ->
                    val todoItem = TodoInfo()
                    todoItem.todoContent = bindingDialog.etMemo.text.toString()
                    todoItem.todoDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                    todoAdapter.addListItem(todoItem)
                    CoroutineScope(Dispatchers.IO).launch {
                        roomDatabase.todoDao().insertTodoData(todoItem)
                        runOnUiThread {
                            todoAdapter.notifyDataSetChanged()
                        }
                    }
                })
                .setNegativeButton("취소",DialogInterface.OnClickListener { dialogInterface, i ->

                })
                .show()
        }
    }
}