package org.thejohnsondev.vault.di

import com.thejohnsondev.common.di.commonModule
import com.thejohnsondev.data.di.authDataModule
import com.thejohnsondev.data.di.settingsDataModule
import com.thejohnsondev.data.di.toolsDataModule
import com.thejohnsondev.data.di.vaultDataModule
import com.thejohnsondev.database.di.databaseModule
import com.thejohnsondev.datastore.di.datastoreModule
import com.thejohnsondev.domain.di.authDomainModule
import com.thejohnsondev.domain.di.settingsDomainModule
import com.thejohnsondev.domain.di.toolsDomainModule
import com.thejohnsondev.domain.di.vaultDomainModule
import com.thejohnsondev.localization.di.appLocaleManagerModule
import com.thejohnsondev.localization.di.localizationModule
import com.thejohnsondev.network.di.apiModule
import com.thejohnsondev.network.di.demoApiModule
import com.thejohnsondev.network.di.networkModule
import com.thejohnsondev.presentation.di.authPresentationModule
import com.thejohnsondev.presentation.di.settingsPresentationModule
import com.thejohnsondev.presentation.di.toolsPresentationModule
import com.thejohnsondev.presentation.di.vaultPresentationModule
import com.thejohnsondev.sync.di.syncModule
import com.thejohnsondev.ui.di.uiModule
import com.thejosnsondev.biometric.di.biometricModule

expect class KoinInitializer {
    fun init()
}

private val commonModulesList = listOf(
    appModule,
    commonModule,
    datastoreModule,
    databaseModule,
    appLocaleManagerModule,
    localizationModule,
    networkModule,
    syncModule,
    uiModule,
    biometricModule,
    authPresentationModule,
    authDomainModule,
    authDataModule,
    settingsDataModule,
    settingsDomainModule,
    settingsPresentationModule,
    toolsDataModule,
    toolsDomainModule,
    toolsPresentationModule,
    vaultDataModule,
    vaultDomainModule,
    vaultPresentationModule
)

val allModules = listOf(
    *commonModulesList.toTypedArray(),
    apiModule
)

val demoModules = listOf(
    *commonModulesList.toTypedArray(),
    demoApiModule
)