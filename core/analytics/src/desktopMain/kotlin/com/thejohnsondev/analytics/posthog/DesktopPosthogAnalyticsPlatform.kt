package com.thejohnsondev.analytics.posthog

import com.posthog.java.PostHog
import com.thejohnsondev.analytics.AnalyticsConfig
import com.thejohnsondev.analytics.AnalyticsPlatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DesktopPosthogAnalyticsPlatform : AnalyticsPlatform {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun initPlatform(config: AnalyticsConfig) {
        scope.launch {
            val posthogConfig = config as PosthogAnalyticsConfig
            PostHog.Builder(posthogConfig.apiKey).host(posthogConfig.host).build()
        }
    }

    override fun trackEventPlatform(
        name: String,
        props: Map<String, Any>
    ) {

        // TODO implement
    }

    override fun logCrashPlatform(t: Throwable) {
        // TODO implement
    }
}