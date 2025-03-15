package com.thejohnsondev.sync.di

import com.thejohnsondev.sync.SyncManager
import org.koin.dsl.module

val syncModule = module {
    single { SyncManager(get(), get(), get(), get()) }
}