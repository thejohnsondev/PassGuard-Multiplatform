package org.thejohnsondev.vault.di

import com.thejohnsondev.data.di.authDataModule
import com.thejohnsondev.domain.di.authDomainModule
import com.thejohnsondev.presentation.di.authPresentationModule
import com.thejohnsondev.ui.di.uiModule

expect class KoinInitializer {
    fun init()
}

val modules = listOf(
    appModule,
    authPresentationModule,
    authDomainModule,
    authDataModule,
    uiModule
)