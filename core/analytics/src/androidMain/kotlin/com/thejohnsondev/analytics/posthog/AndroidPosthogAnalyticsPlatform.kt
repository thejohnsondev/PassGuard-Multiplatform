package com.thejohnsondev.analytics.posthog

import android.content.Context
import com.posthog.PostHog
import com.posthog.android.PostHogAndroid
import com.posthog.android.PostHogAndroidConfig
import com.thejohnsondev.analytics.AnalyticsConfig
import com.thejohnsondev.analytics.AnalyticsPlatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AndroidPosthogAnalyticsPlatform(private val context: Context) : AnalyticsPlatform {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun initPlatform(config: AnalyticsConfig) {
        scope.launch {
            val posthogConfig = config as PosthogAnalyticsConfig
            val nativeConfig = PostHogAndroidConfig(
                apiKey = posthogConfig.apiKey,
                host = posthogConfig.host
            )
            PostHogAndroid.setup(context, nativeConfig)
        }
    }

    override fun trackEventPlatform(
        name: String,
        props: Map<String, Any>
    ) {
        scope.launch {
            PostHog.capture(event = name, properties = props)
        }
    }

    override fun logCrashPlatform(t: Throwable) {
        // TODO implement
    }
}