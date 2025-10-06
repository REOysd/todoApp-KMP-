package com.example.todoapp.domain

import com.example.todoapp.data.TodoEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun insert(item: TodoEntity)
    suspend fun delete(item: TodoEntity)
    fun getAll(): Flow<List<TodoEntity>>
}