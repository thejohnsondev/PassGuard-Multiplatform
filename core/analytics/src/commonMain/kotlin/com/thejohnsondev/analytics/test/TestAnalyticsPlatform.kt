package com.thejohnsondev.analytics.test

import com.thejohnsondev.analytics.AnalyticsConfig
import com.thejohnsondev.analytics.AnalyticsPlatform

class TestAnalyticsPlatform: AnalyticsPlatform {
    override fun initPlatform(config: AnalyticsConfig) {
        // No-op
    }

    override fun trackEventPlatform(name: String, props: Map<String, Any>, installId: String?) {
        // No-op
    }

    override fun logCrashPlatform(t: Throwable) {
        // No-op
    }
}