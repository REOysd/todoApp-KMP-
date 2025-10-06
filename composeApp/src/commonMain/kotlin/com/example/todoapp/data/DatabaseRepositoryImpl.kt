package com.example.todoapp.data

import com.example.todoapp.domain.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(
    private val todoDao: TodoDao
) : DatabaseRepository {
    override suspend fun insert(item: TodoEntity) {
        todoDao.insert(item)
    }

    override suspend fun delete(item: TodoEntity) {
        todoDao.delete(item)
    }

    override fun getAll(): Flow<List<TodoEntity>> {
        return todoDao.getAll()
    }
}
