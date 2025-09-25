package com.example.todoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(item: TodoEntity)

    @Delete
    suspend fun delete(item: TodoEntity)

    @Query("SELECT * FROM TodoEntity")
    fun getAll(): Flow<List<TodoEntity>>
}