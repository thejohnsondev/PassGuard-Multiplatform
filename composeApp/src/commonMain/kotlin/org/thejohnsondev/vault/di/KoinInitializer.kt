package org.thejohnsondev.vault.di

import com.thejohnsondev.common.di.commonModule
import com.thejohnsondev.data.di.authDataModule
import com.thejohnsondev.data.di.settingsDataModule
import com.thejohnsondev.database.di.databaseModule
import com.thejohnsondev.datastore.di.datastoreModule
import com.thejohnsondev.data.di.vaultDataModule
import com.thejohnsondev.domain.di.authDomainModule
import com.thejohnsondev.domain.di.settingsDomainModule
import com.thejohnsondev.domain.di.vaultDomainModule
import com.thejohnsondev.network.di.networkModule
import com.thejohnsondev.presentation.di.authPresentationModule
import com.thejohnsondev.presentation.di.settingsPresentationModule
import com.thejohnsondev.presentation.di.vaultPresentationModule
import com.thejohnsondev.sync.di.syncModule
import com.thejohnsondev.ui.di.uiModule

expect class KoinInitializer {
    fun init()
}

val modules = listOf(
    appModule,
    commonModule,
    datastoreModule,
    databaseModule,
    networkModule,
    syncModule,
    uiModule,
    authPresentationModule,
    authDomainModule,
    authDataModule,
    settingsDataModule,
    settingsDomainModule,
    settingsPresentationModule,
    vaultDataModule,
    vaultDomainModule,
    vaultPresentationModule
)