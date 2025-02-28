package com.thejohnsondev.platform.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.PersistableBundle

class AndroidClipboardUtils(
    private val context: Context
): ClipboardUtils {

    private val clipboard : ClipboardManager by lazy {
        context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    }

    @SuppressLint("ServiceCast")
    override fun copyToClipboard(text: String) {
        clipboard.setPrimaryClip(ClipData.newPlainText(text, text))
    }

    override fun copyToClipboardSensitive(text: String) {
        clipboard.setPrimaryClip(ClipData.newPlainText(text, text).apply {
            description.extras = PersistableBundle().apply {
                putBoolean("android.content.extra.IS_SENSITIVE", true)
            }
        })
    }
}