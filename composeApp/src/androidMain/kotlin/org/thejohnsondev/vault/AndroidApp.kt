package org.thejohnsondev.vault

import android.app.Application
import com.thejohnsondev.analytics.Analytics
import com.thejohnsondev.analytics.posthog.PosthogAnalyticsConfig
import com.thejohnsondev.analytics.posthog.PosthogAnalyticsPlatform
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.platform.di.AndroidPlatformDependency
import org.thejohnsondev.vault.di.KoinInitializer

class AndroidApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initAnalytics()
    }

    private fun initKoin() {
        val platformDependency = AndroidPlatformDependency(this)
        KoinInitializer(
            context = applicationContext,
            platformDependency = platformDependency
        ).init()
    }

    private fun initAnalytics() {
        val config = PosthogAnalyticsConfig(
            apiKey = BuildKonfigProvider.getPosthogApiKey(),
            host = BuildKonfigProvider.getPosthogHost()
        )
        val platform = PosthogAnalyticsPlatform(this)
        Analytics.init(config, platform)
    }

}