package org.thejohnsondev.vault.di

import android.content.Context
import com.thejohnsondev.platform.di.PlatformDependency
import com.thejohnsondev.platform.di.PlatformModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

actual class KoinInitializer(
    private val context: Context,
    private val platformDependency: PlatformDependency
) {
    actual fun init() {
        startKoin {
            androidContext(context)
            androidLogger()
            modules(
                modules + PlatformModuleProvider(platformDependency).generatePlatformModule()
            )
        }
    }
}