package org.thejohnsondev.vault.di

import android.content.Context
import com.thejohnsondev.analytics.di.AnalyticsDependency
import com.thejohnsondev.analytics.di.AnalyticsModuleProvider
import com.thejohnsondev.platform.di.PlatformDependency
import com.thejohnsondev.platform.di.PlatformModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

actual class KoinInitializer(
    private val context: Context,
    private val platformDependency: PlatformDependency,
    private val analyticsDependency: AnalyticsDependency
) {
    actual fun init() {
        val platformModule = PlatformModuleProvider(platformDependency).generatePlatformModule()
        val analyticsModule = AnalyticsModuleProvider(analyticsDependency).generateAnalyticsModule()
        startKoin {
            androidContext(context)
            androidLogger()
            modules(
                allModules + platformModule + analyticsModule
            )
        }
    }
}