package com.thejohnsondev.analytics.posthog

import cocoapods.PostHog.PostHogConfig
import cocoapods.PostHog.PostHogSDK
import com.thejohnsondev.analytics.AnalyticsConfig
import com.thejohnsondev.analytics.AnalyticsPlatform
import kotlinx.cinterop.ExperimentalForeignApi

actual class PosthogAnalyticsPlatform : AnalyticsPlatform {
    @OptIn(ExperimentalForeignApi::class)
    actual override fun initPlatform(config: AnalyticsConfig) {
        val posthogConfig = config as PosthogAnalyticsConfig
        val nativeConfig = PostHogConfig(apiKey = posthogConfig.apiKey, host = posthogConfig.host)
        PostHogSDK.shared().setup(nativeConfig)
    }

    actual override fun trackEventPlatform(
        name: String,
        props: Map<String, Any>
    ) {
        // TODO implement
    }

    actual override fun logCrashPlatform(t: Throwable) {
        // TODO implement
    }
}