package org.thejohnsondev.vault

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.common.utils.safeLet
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin
import org.thejohnsondev.vault.di.KoinInitializer
import org.thejohnsondev.vault.root.Root
import vaultmultiplatform.core.ui.generated.resources.app_name
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient
import java.awt.Dimension

fun main() = application {
    KoinInitializer().init()
    val getFirstScreenRouteUseCase: GetFirstScreenRouteUseCase = remember {
        getKoin().get()
    }
    val getSettingsUseCase: GetSettingsFlowUseCase = remember {
        getKoin().get()
    }
    val deviceThemeConfig: DeviceThemeConfig = remember {
        getKoin().get()
    }
    val coroutineScope = rememberCoroutineScope()
    val firstScreenRoute = remember { mutableStateOf<Routes?>(null) }
    val settingsConfig = remember { mutableStateOf<SettingsConfig?>(null) }

    LaunchedEffect(true) {
        coroutineScope.launch {
            firstScreenRoute.value = getFirstScreenRouteUseCase()
        }
        coroutineScope.launch {
            getSettingsUseCase.invoke().collect {
                settingsConfig.value = it
            }
        }
    }

    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        position = WindowPosition.Aligned(Alignment.Center),
        size = DpSize(DESKTOP_WINDOW_DEFAULT_WIDTH.dp, DESKTOP_WINDOW_DEFAULT_HEIGHT.dp),
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(ResString.app_name),
        icon = painterResource(ResDrawable.ic_vault_108_gradient),
        state = windowState
    ) {
        window.minimumSize = Dimension(DESKTOP_WINDOW_MIN_WIDTH, DESKTOP_WINDOW_MIN_HEIGHT)
        safeLet(firstScreenRoute.value, settingsConfig.value) { route, settings ->
            Root(deviceThemeConfig, route, settings)
        } ?: kotlin.run {
            DesktopSplash()
        }
    }
}