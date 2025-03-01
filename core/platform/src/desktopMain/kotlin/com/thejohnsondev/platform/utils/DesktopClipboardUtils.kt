package com.thejohnsondev.platform.utils

import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection

class DesktopClipboardUtils: ClipboardUtils {
    private val clipboard: Clipboard by lazy {
        Toolkit.getDefaultToolkit().systemClipboard
    }

    override fun copyToClipboard(text: String) {
        val selection = StringSelection(text)
        clipboard.setContents(selection, selection)    }

    override fun copyToClipboardSensitive(text: String) {
        copyToClipboard(text)
    }
}