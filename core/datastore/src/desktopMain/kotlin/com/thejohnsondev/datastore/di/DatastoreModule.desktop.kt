package com.thejohnsondev.datastore.di

import com.thejohnsondev.datastore.DataStoreFactory
import com.thejohnsondev.datastore.PreferencesDataStore
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.thejohnsondev.datastore.PreferencesDataStoreImpl

actual val datastoreModule = module {
    single {
        DataStoreFactory().buildDataStore()
    }
    singleOf(::PreferencesDataStoreImpl) { bind<PreferencesDataStore>() }
}