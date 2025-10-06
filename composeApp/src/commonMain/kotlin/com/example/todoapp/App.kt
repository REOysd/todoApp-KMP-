package com.example.todoapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.todoapp.presentation.todoList.TodoListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(TodoListScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}