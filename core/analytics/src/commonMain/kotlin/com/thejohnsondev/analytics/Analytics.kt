package com.thejohnsondev.analytics

object Analytics {

    private lateinit var platform: AnalyticsPlatform

    fun init(config: AnalyticsConfig, platform: AnalyticsPlatform) {
        this.platform = platform
        platform.initPlatform(config)
    }

    fun trackScreen(name: String, props: Map<String, Any> = emptyMap()) {
        platform.trackEventPlatform(name, props.toMutableMap().apply {
            put("screen_name", name)
        })
    }

    fun trackEvent(name: String, props: Map<String, Any> = emptyMap()) =
        platform.trackEventPlatform(name, props)

    fun logCrash(t: Throwable) = platform.logCrashPlatform(t)

}