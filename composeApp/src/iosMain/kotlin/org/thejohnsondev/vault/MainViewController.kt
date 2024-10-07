package org.thejohnsondev.vault

import androidx.compose.ui.window.ComposeUIViewController
import org.thejohnsondev.vault.di.KoinInitializer

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
    }
) { Root() }