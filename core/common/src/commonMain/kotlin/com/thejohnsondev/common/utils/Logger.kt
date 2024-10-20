package com.thejohnsondev.common.utils

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

object Logger {

    fun initialize() {
        Napier.base(DebugAntilog())
    }

    fun d(message: String?, tag: String? = null) {
        message?.let {
            Napier.d(message = message, tag = tag)
        }
    }

    fun e(message: String?, tag: String? = null) {
        message?.let {
            Napier.e(message = message, tag = tag)
        }
    }

    fun i(message: String?, tag: String? = null) {
        message?.let {
            Napier.i(message = message, tag = tag)
        }
    }

}