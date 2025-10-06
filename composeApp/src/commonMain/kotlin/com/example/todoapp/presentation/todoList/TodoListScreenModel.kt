package com.example.todoapp.presentation.todoList

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.todoapp.data.TodoEntity
import com.example.todoapp.domain.DatabaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoListScreenModel(
    private val repository: DatabaseRepository
) : ScreenModel {

    private val _todos = MutableStateFlow<List<TodoEntity>>(emptyList())
    val todos: StateFlow<List<TodoEntity>> = _todos.asStateFlow()

    init {
        loadTodos()
    }

    private fun loadTodos() {
        screenModelScope.launch {
            repository.getAll().collect { todoList ->
                _todos.value = todoList
            }
        }
    }

    fun addTodo(title: String, description: String) {
        if (title.isBlank()) return

        screenModelScope.launch {
            val newTodo = TodoEntity(
                title = title,
                description = description
            )
            repository.insert(newTodo)
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        screenModelScope.launch {
            repository.delete(todo)
        }
    }
}
