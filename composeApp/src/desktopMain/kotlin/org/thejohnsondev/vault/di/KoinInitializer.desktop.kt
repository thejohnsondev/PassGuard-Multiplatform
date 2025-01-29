package org.thejohnsondev.vault.di

import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                modules(modules)
            }
        }
    }
}