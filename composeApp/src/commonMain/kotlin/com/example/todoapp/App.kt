package com.example.todoapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.todoapp.presentation.TodoScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        TodoScreen()
    }
}