package com.thejohnsondev.analytics

private const val SCREEN_NAME = "screen_name"
private const val INSTALL_ID = "distinct_id"
private const val USER_ID = "person_id"
private const val APP_THEME = "app_theme"
private const val VAULT_TYPE = "vault_type"

object Analytics {

    private lateinit var platform: AnalyticsPlatform
    private var installId: String? = null
    private var userId: String? = null
    private var appTheme: String? = null
    private var vaultType: String? = null

    fun init(config: AnalyticsConfig, platform: AnalyticsPlatform) {
        this.platform = platform
        platform.initPlatform(config)
    }

    fun trackScreen(name: String, props: Map<String, Any> = emptyMap()) {
        platform.trackEventPlatform(
            name = name,
            props = props
                .applyScreenName(name)
                .applyCommonProps()
        )
    }

    fun trackEvent(name: String, props: Map<String, Any> = emptyMap()) =
        platform.trackEventPlatform(name = name, props = props.applyCommonProps())


    fun logCrash(t: Throwable) = platform.logCrashPlatform(t)

    fun setInstallId(id: String) {
        installId = id
    }

    fun setUserId(id: String) {
        userId = id
    }

    fun setAppTheme(theme: String) {
        appTheme = theme
    }

    fun setVaultType(type: String) {
        vaultType = type
    }

    private fun Map<String, Any>.applyScreenName(name: String): Map<String, Any> {
        val mutableMap = this.toMutableMap()
        mutableMap[SCREEN_NAME] = name
        return mutableMap
    }

    private fun Map<String, Any>.applyCommonProps(): Map<String, Any> {
        val mutableMap = this.toMutableMap()
        installId?.let { mutableMap[INSTALL_ID] = it }
        userId?.let { mutableMap[USER_ID] = it }
        appTheme?.let { mutableMap[APP_THEME] = it }
        vaultType?.let { mutableMap[VAULT_TYPE] = it }
        return mutableMap
    }

}