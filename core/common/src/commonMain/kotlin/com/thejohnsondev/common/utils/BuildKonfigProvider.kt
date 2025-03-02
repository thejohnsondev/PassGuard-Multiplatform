package com.thejohnsondev.common.utils

import org.thejohnsondev.common.BuildKonfig

object BuildKonfigProvider {
    fun getAppType(): String = BuildKonfig.APP_TYPE
}