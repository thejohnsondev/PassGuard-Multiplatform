package org.thejohnsondev.vault.di

import com.thejohnsondev.data.di.authDataModule
import com.thejohnsondev.database.di.databaseModule
import com.thejohnsondev.datastore.di.datastoreModule
import com.thejohnsondev.di.vaultDataModule
import com.thejohnsondev.domain.di.authDomainModule
import com.thejohnsondev.domain.di.vaultDomainModule
import com.thejohnsondev.network.di.networkModule
import com.thejohnsondev.presentation.di.authPresentationModule
import com.thejohnsondev.presentation.di.settingsPresentationModule
import com.thejohnsondev.presentation.di.vaultPresentationModule
import com.thejohnsondev.ui.di.uiModule

expect class KoinInitializer {
    fun init()
}

val modules = listOf(
    appModule,
    datastoreModule,
    databaseModule,
    networkModule,
    uiModule,
    authPresentationModule,
    authDomainModule,
    authDataModule,
    settingsPresentationModule,
    vaultDataModule,
    vaultDomainModule,
    vaultPresentationModule
)