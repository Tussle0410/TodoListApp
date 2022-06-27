package com.tussle.todolistapp.database

import androidx.room.*
import com.tussle.todolistapp.model.TodoInfo

@Dao
interface TodoDao {
    @Insert
    fun insertTodoData(todoInfo : TodoInfo)

    @Update
    fun updateTodoData(todoInfo: TodoInfo)

    @Delete
    fun deleteTodoData(todoInfo: TodoInfo)

    @Query("SELECT * FROM TodoInfo ORDER BY todoDate")
    fun getAllReadDate() : List<TodoInfo>


}