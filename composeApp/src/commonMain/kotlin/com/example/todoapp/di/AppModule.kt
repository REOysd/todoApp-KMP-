package com.example.todoapp.di

import com.example.todoapp.data.AppDatabase
import com.example.todoapp.data.DatabaseRepositoryImpl
import com.example.todoapp.data.getRoomDatabase
import com.example.todoapp.domain.DatabaseRepository
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            platformModule(),
            appModule
        )
    }

val appModule = module {
    single { getRoomDatabase(get()) }
    single { get<AppDatabase>().getDao() }
    singleOf(::DatabaseRepositoryImpl).bind<DatabaseRepository>()
}
