package com.thejohnsondev.datastore.di

import com.thejohnsondev.datastore.DataStoreFactory
import org.koin.dsl.module

actual val datastoreModule = module {
    single {
        DataStoreFactory().buildDataStore()
    }
}