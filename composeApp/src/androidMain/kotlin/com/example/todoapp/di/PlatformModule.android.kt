package com.example.todoapp.di

import androidx.room.RoomDatabase
import com.example.todoapp.data.AppDatabase
import com.example.todoapp.data.databaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        databaseBuilder(get())
    }
}
