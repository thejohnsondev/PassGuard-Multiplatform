package org.thejohnsondev.vault

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import org.koin.mp.KoinPlatform.getKoin
import org.thejohnsondev.vault.di.KoinInitializer
import org.thejohnsondev.vault.root.Root

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
    }
) {
    val deviceThemeConfig: DeviceThemeConfig = remember {
        getKoin().get()
    }
    Root(deviceThemeConfig, TODO() /* add firstScreenRoute retrieving */)
}