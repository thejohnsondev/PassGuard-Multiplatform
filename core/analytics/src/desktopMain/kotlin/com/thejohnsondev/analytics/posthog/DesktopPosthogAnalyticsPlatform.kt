package com.thejohnsondev.analytics.posthog

import com.posthog.java.PostHog
import com.thejohnsondev.analytics.AnalyticsConfig
import com.thejohnsondev.analytics.AnalyticsPlatform
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DesktopPosthogAnalyticsPlatform : AnalyticsPlatform {

    private var postHog: PostHog? = null

    override fun initPlatform(config: AnalyticsConfig) {
        val posthogConfig = config as PosthogAnalyticsConfig
        postHog = PostHog.Builder(posthogConfig.apiKey).host(posthogConfig.host).build()
    }

    override fun trackEventPlatform(
        name: String,
        props: Map<String, Any>,
        installId: String?
    ) {
        postHog?.capture(installId, name, props)
    }

    override fun logCrashPlatform(t: Throwable) {
        // TODO implement
    }
}