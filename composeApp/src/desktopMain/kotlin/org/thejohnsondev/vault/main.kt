package org.thejohnsondev.vault

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.thejohnsondev.common.DESKTOP_WINDOW_DEFAULT_HEIGHT
import com.thejohnsondev.common.DESKTOP_WINDOW_DEFAULT_WIDTH
import com.thejohnsondev.common.DESKTOP_WINDOW_MIN_HEIGHT
import com.thejohnsondev.common.DESKTOP_WINDOW_MIN_WIDTH
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin
import org.thejohnsondev.vault.di.KoinInitializer
import vaultmultiplatform.composeapp.generated.resources.Res
import vaultmultiplatform.composeapp.generated.resources.app_name
import vaultmultiplatform.composeapp.generated.resources.ic_vault_108_gradient
import java.awt.Dimension

fun main() = application {
    KoinInitializer().init()
    val deviceThemeConfig: DeviceThemeConfig = remember {
        getKoin().get()
    }
    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        position = WindowPosition.Aligned(Alignment.Center),
        size = DpSize(DESKTOP_WINDOW_DEFAULT_WIDTH.dp, DESKTOP_WINDOW_DEFAULT_HEIGHT.dp),
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
        icon = painterResource(Res.drawable.ic_vault_108_gradient),
        state = windowState
    ) {
        window.minimumSize = Dimension(DESKTOP_WINDOW_MIN_WIDTH, DESKTOP_WINDOW_MIN_HEIGHT)
        Root(deviceThemeConfig)
    }
}