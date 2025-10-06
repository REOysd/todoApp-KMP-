package com.example.todoapp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.todoapp.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}