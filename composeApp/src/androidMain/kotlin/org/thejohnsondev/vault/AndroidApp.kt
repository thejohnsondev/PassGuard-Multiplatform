package org.thejohnsondev.vault

import android.app.Application
import com.thejohnsondev.analytics.di.AnalyticsDependency
import com.thejohnsondev.analytics.di.AndroidAnalyticsDependency
import com.thejohnsondev.analytics.test.DemoAnalyticsDependency
import com.thejohnsondev.common.AppType
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.platform.di.AndroidPlatformDependency
import com.thejohnsondev.platform.di.PlatformDependency
import org.thejohnsondev.vault.di.KoinInitializer

class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val platformDependency = getPlatformDependency()
        val analyticsDependency = getAnalyticsDependency()
        KoinInitializer(
            context = applicationContext,
            platformDependency = platformDependency,
            analyticsDependency = analyticsDependency
        ).init()
    }

    private fun getPlatformDependency(): PlatformDependency {
        return AndroidPlatformDependency(this)
    }

    private fun getAnalyticsDependency(): AnalyticsDependency {
        return when (AppType.from(BuildKonfigProvider.getAppType())) {
            AppType.DEMO -> {
                DemoAnalyticsDependency()
            }

            AppType.DEV,
            AppType.PROD -> {
                AndroidAnalyticsDependency(this)
            }
        }
    }

}