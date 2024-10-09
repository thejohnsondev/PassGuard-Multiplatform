package org.thejohnsondev.vault

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import org.koin.mp.KoinPlatform.getKoin
import org.thejohnsondev.vault.di.KoinInitializer

fun main() = application {
    KoinInitializer().init()
    val deviceThemeConfig: DeviceThemeConfig = remember {
        getKoin().get()
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Vault Multiplatform",
    ) {
        Root(deviceThemeConfig)
    }
}