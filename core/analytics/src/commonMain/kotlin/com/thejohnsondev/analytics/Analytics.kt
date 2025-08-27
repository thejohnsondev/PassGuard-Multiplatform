package com.thejohnsondev.analytics

private const val SCREEN_NAME = "screen_name"
private const val INSTALL_ID = "distinct_id"
private const val USER_ID = "person_id"
private const val APP_THEME = "app_theme"
private const val VAULT_TYPE = "vault_type"
private const val IS_VAULT_INITIALIZED = "is_vault_initialized"

object Analytics {

    private lateinit var platform: AnalyticsPlatform
    private var installId: String? = null
    private var userId: String? = null
    private var appTheme: String? = null
    private var vaultType: String? = null
    private var isVaultInitialized: Boolean? = null

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

    fun setInstallId(id: String?) {
        installId = id
    }

    fun setUserId(id: String?) {
        userId = id
    }

    fun setAppTheme(theme: String) {
        appTheme = theme
    }

    fun setVaultType(type: String?) {
        vaultType = type
    }

    fun setVaultInitialized(initialized: Boolean) {
        isVaultInitialized = initialized
    }

    private fun Map<String, Any>.applyScreenName(name: String): Map<String, Any> {
        val mutableMap = this.toMutableMap()
        mutableMap[SCREEN_NAME] = name
        return mutableMap
    }

    private fun Map<String, Any>.applyCommonProps(): Map<String, Any> {
        val mutableMap = this.toMutableMap()
        mutableMap[INSTALL_ID] = installId ?: "undefined"
        mutableMap[USER_ID] = userId ?: "undefined"
        mutableMap[APP_THEME] = appTheme ?: "undefined"
        mutableMap[VAULT_TYPE] = vaultType ?: "undefined"
        mutableMap[IS_VAULT_INITIALIZED] = isVaultInitialized ?: false
        return mutableMap
    }

}