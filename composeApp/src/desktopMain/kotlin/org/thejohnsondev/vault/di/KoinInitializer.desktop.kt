package org.thejohnsondev.vault.di

import com.thejohnsondev.presentation.di.authPresentationModule
import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(appModule, authPresentationModule)
        }
    }
}