package com.example.todoapp.presentation.todoDetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.todoapp.domain.DatabaseRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.koinInject

data class TodoDetailScreen(
    val todoId: Long
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val repository = koinInject<DatabaseRepository>()
        val screenModel = rememberScreenModel { TodoDetailScreenModel(repository) }
        val navigator = LocalNavigator.currentOrThrow
        val todo by screenModel.todo.collectAsState()
        val isDeleted by screenModel.isDeleted.collectAsState()
        var showDeleteDialog by remember { mutableStateOf(false) }

        LaunchedEffect(todoId) {
            screenModel.loadTodo(todoId)
        }

        LaunchedEffect(isDeleted) {
            if (isDeleted) {
                navigator.pop()
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("タスク詳細") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "戻る"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "削除",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { paddingValues ->
            todo?.let { currentTodo ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "タイトル",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = currentTodo.title,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }

                    if (currentTodo.description.isNotBlank()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "説明",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = currentTodo.description,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "作成日時",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = formatDateTime(currentTodo.createdTime),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            } ?: Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("削除確認") },
                    text = { Text("このタスクを削除しますか？") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                screenModel.deleteTodo()
                                showDeleteDialog = false
                            }
                        ) {
                            Text("削除", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("キャンセル")
                        }
                    }
                )
            }
        }
    }

    private fun formatDateTime(timestamp: Long): String {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${dateTime.year}年${dateTime.monthNumber}月${dateTime.dayOfMonth}日 " +
                "${dateTime.hour.toString().padStart(2, '0')}:${dateTime.minute.toString().padStart(2, '0')}"
    }
}
