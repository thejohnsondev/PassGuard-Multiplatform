package com.thejohnsondev.database.di

import com.thejohnsondev.database.LocalDataSource
import com.thejohnsondev.database.LocalDataSourceImpl
import com.thejohnsondev.database.sqldelight.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thejohnsondev.vault.database.VaultDatabase

actual val databaseModule = module {
    singleOf(::LocalDataSourceImpl) { bind<LocalDataSource>() }
    single { DatabaseDriverFactory(androidContext()).create() }
    single { VaultDatabase(get()) }
}