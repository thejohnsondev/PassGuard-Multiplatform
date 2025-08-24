package com.thejohnsondev.analytics.posthog

import com.thejohnsondev.analytics.AnalyticsConfig
import com.thejohnsondev.analytics.AnalyticsPlatform

expect class PosthogAnalyticsPlatform: AnalyticsPlatform {
    override fun initPlatform(config: AnalyticsConfig)
    override fun trackEventPlatform(name: String, props: Map<String, Any>)
    override fun logCrashPlatform(t: Throwable)
}