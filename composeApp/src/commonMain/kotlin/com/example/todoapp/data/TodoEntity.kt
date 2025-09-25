package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    @OptIn(kotlin.time.ExperimentalTime::class)
    val createdTime: Long = Clock.System.now().toEpochMilliseconds()
)