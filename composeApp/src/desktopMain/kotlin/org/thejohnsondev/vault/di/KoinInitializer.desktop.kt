package org.thejohnsondev.vault.di

import com.thejohnsondev.analytics.di.AnalyticsDependency
import com.thejohnsondev.analytics.di.AnalyticsModuleProvider
import com.thejohnsondev.platform.di.PlatformDependency
import com.thejohnsondev.platform.di.PlatformModuleProvider
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

actual class KoinInitializer(
    private val platformDependency: PlatformDependency,
    private val analyticsDependency: AnalyticsDependency
) {
    actual fun init() {
        if (GlobalContext.getOrNull() == null) {
            val platformModule = PlatformModuleProvider(platformDependency).generatePlatformModule()
            val analyticsModule = AnalyticsModuleProvider(analyticsDependency).generateAnalyticsModule()
            startKoin {
                modules(
                    allModules + platformModule + analyticsModule
                )
            }
        }
    }
}