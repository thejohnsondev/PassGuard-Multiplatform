package org.thejohnsondev.vault.di

import com.thejohnsondev.platform.di.PlatformDependency
import com.thejohnsondev.platform.di.PlatformModuleProvider
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

actual class KoinInitializer(
    private val platformDependency: PlatformDependency
) {
    actual fun init() {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                modules(
                    modules + PlatformModuleProvider(platformDependency).generatePlatformModule()
                )
            }
        }
    }
}