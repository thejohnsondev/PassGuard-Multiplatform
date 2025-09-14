package com.thejohnsondev.common.utils

import org.thejohnsondev.common.BuildKonfig

object BuildKonfigProvider {
    fun getAppType(): String = BuildKonfig.APP_TYPE
    fun getAppVersion(): String = BuildKonfig.APP_VERSION
    fun getPosthogApiKey(): String = BuildKonfig.POST_HOG_API_KEY
    fun getPosthogHost(): String = BuildKonfig.POST_HOG_HOST
    fun getSelectVaultTypeEnabled(): Boolean = BuildKonfig.SHOW_VAULT_TYPE_SELECTION
    fun getAppVersionName(): String = BuildKonfig.VERSION_NAME
    fun getLastAppUpdate(): String = BuildKonfig.LAST_COMMIT_TIME
    fun getLastCommitHash(): String = BuildKonfig.LAST_COMMIT_HASH
}