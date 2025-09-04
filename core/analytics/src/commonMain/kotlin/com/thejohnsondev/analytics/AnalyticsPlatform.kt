package com.thejohnsondev.analytics

interface AnalyticsPlatform {
    fun initPlatform(config: AnalyticsConfig)
    fun trackEventPlatform(name: String, props: Map<String, Any>, installId: String? = null)
    fun logCrashPlatform(t: Throwable)
}