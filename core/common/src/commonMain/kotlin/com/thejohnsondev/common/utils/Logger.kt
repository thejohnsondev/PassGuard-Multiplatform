package com.thejohnsondev.common.utils

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

object Logger {

    private var logListener: ((String) -> Unit)? = null

    fun initialize() {
        Napier.base(DebugAntilog())
    }

    fun d(message: String?, tag: String? = null) {
        message?.let {
            Napier.d(message = message, tag = tag)
            logListener?.invoke(message)
        }
    }

    fun e(message: String?, tag: String? = null) {
        message?.let {
            Napier.e(message = message, tag = tag)
            logListener?.invoke(message)
        }
    }

    fun i(message: String?, tag: String? = null) {
        message?.let {
            Napier.i(message = message, tag = tag)
            logListener?.invoke(message)
        }
    }

    fun attachLogListener(listener: (String) -> Unit) {
        logListener = listener
    }

}