package org.thejohnsondev.vault.di

import android.content.Context
import com.thejohnsondev.presentation.di.authPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

actual class KoinInitializer(
    private val context: Context
) {
    actual fun init() {
        startKoin {
            androidContext(context)
            androidLogger()
            modules(
                appModule,
                authPresentationModule
            )
        }
    }
}