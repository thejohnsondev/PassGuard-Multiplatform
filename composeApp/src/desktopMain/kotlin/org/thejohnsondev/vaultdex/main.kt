package org.thejohnsondev.vaultdex

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "VaultDex Multiplatform",
    ) {
        App()
    }
}