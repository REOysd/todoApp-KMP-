package com.example.todoapp.helper

import com.example.todoapp.domain.DatabaseRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DatabaseRepositoryHelper : KoinComponent {
    private val databaseRepository: DatabaseRepository by inject()
    fun getDatabaseRepository(): DatabaseRepository = databaseRepository
}