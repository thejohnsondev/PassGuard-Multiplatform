package com.thejohnsondev.database.di

import com.thejohnsondev.database.FakeLocalDataSource
import com.thejohnsondev.database.LocalDataSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    singleOf(::FakeLocalDataSource) { bind<LocalDataSource>() }
}