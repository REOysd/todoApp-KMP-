package com.example.todoapp.presentation.todoDetail

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.todoapp.data.TodoEntity
import com.example.todoapp.domain.DatabaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoDetailScreenModel(
    private val repository: DatabaseRepository,
) : ScreenModel {

    private val _todo = MutableStateFlow<TodoEntity?>(null)
    val todo: StateFlow<TodoEntity?> = _todo.asStateFlow()

    private val _isDeleted = MutableStateFlow(false)
    val isDeleted: StateFlow<Boolean> = _isDeleted.asStateFlow()

    fun loadTodo(todoId: Long) {
        screenModelScope.launch {
            repository.getAll().collect { todoList ->
                _todo.value = todoList.find { it.id == todoId }
            }
        }
    }

    fun deleteTodo() {
        screenModelScope.launch {
            _todo.value?.let { currentTodo ->
                repository.delete(currentTodo)
                _isDeleted.value = true
            }
        }
    }

    fun updateTodo(title: String, description: String) {
        if (title.isBlank()) return

        screenModelScope.launch {
            _todo.value?.let { currentTodo ->
                repository.delete(currentTodo)
                repository.insert(
                    currentTodo.copy(
                        title = title,
                        description = description
                    )
                )
            }
        }
    }
}
