package com.tussle.todolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tussle.todolistapp.adapter.TodoAdapter
import com.tussle.todolistapp.databinding.ActivityListMainBinding

class ListMainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityListMainBinding
    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoAdapter = TodoAdapter()
        binding.rvTodo.adapter = todoAdapter
    }
}