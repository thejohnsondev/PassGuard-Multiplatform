package com.thejohnsondev.datastore.di

import com.thejohnsondev.datastore.DataStoreFactory
import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.datastore.PreferencesDataStoreImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val datastoreModule = module {
    single {
        DataStoreFactory().buildDataStore()
    }
    singleOf(::PreferencesDataStoreImpl) { bind<PreferencesDataStore>() }
}