package org.thejohnsondev.vault

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.thejohnsondev.vault.di.KoinInitializer

fun main() = application {
    KoinInitializer().init()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Vault Multiplatform",
    ) {
        Root()
    }
}