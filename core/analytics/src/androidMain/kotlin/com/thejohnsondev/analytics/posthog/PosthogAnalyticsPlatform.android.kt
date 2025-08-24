package com.thejohnsondev.analytics.posthog

import android.content.Context
import com.posthog.PostHog
import com.posthog.android.PostHogAndroid
import com.posthog.android.PostHogAndroidConfig
import com.thejohnsondev.analytics.AnalyticsConfig
import com.thejohnsondev.analytics.AnalyticsPlatform

actual class PosthogAnalyticsPlatform(private val context: Context) : AnalyticsPlatform {
    actual override fun initPlatform(config: AnalyticsConfig) {
        val posthogConfig = config as PosthogAnalyticsConfig
        val nativeConfig = PostHogAndroidConfig(
            apiKey = posthogConfig.apiKey,
            host = posthogConfig.host
        )
        PostHogAndroid.setup(context, nativeConfig)
    }

    actual override fun trackEventPlatform(
        name: String,
        props: Map<String, Any>
    ) {
        PostHog.capture(event = name, properties = props)
    }

    actual override fun logCrashPlatform(t: Throwable) {
        // TODO implement
    }
}