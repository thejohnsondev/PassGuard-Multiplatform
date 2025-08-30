package com.thejohnsondev.analytics.posthog

import com.thejohnsondev.analytics.AnalyticsConfig

data class PosthogAnalyticsConfig(
    val apiKey: String,
    val host: String,
    val enableCrashlytics: Boolean = true
): AnalyticsConfig