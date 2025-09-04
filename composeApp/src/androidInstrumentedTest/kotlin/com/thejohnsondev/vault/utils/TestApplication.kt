package com.thejohnsondev.vault.utils

import android.app.Application
import com.thejohnsondev.analytics.di.AnalyticsModuleProvider
import com.thejohnsondev.analytics.test.DemoAnalyticsDependency
import com.thejohnsondev.platform.di.AndroidPlatformDependency
import com.thejohnsondev.platform.di.PlatformModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.thejohnsondev.vault.di.demoModules

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@TestApplication)
                androidLogger(level = Level.ERROR)
                modules(
                    demoModules + PlatformModuleProvider(
                        AndroidPlatformDependency(applicationContext)
                    ).generatePlatformModule() + AnalyticsModuleProvider(
                        DemoAnalyticsDependency()
                    ).generateAnalyticsModule()
                )
            }
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}