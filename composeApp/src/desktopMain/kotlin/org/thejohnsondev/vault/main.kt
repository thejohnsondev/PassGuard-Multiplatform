package org.thejohnsondev.vault

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.thejohnsondev.analytics.di.AnalyticsDependency
import com.thejohnsondev.analytics.di.DesktopAnalyticsDependency
import com.thejohnsondev.analytics.test.DemoAnalyticsDependency
import com.thejohnsondev.common.AppType
import com.thejohnsondev.common.DESKTOP_WINDOW_DEFAULT_HEIGHT
import com.thejohnsondev.common.DESKTOP_WINDOW_DEFAULT_WIDTH
import com.thejohnsondev.common.DESKTOP_WINDOW_MIN_HEIGHT
import com.thejohnsondev.common.DESKTOP_WINDOW_MIN_WIDTH
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.common.utils.safeLet
import com.thejohnsondev.domain.CheckInstallIDUseCase
import com.thejohnsondev.domain.GetAnalyticsPropsUseCase
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.domain.model.AnalyticsProps
import com.thejohnsondev.localization.LocalizationUtils
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.platform.di.DesktopPlatformDependency
import com.thejohnsondev.platform.di.PlatformDependency
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import io.kanro.compose.jetbrains.expui.theme.DarkTheme
import io.kanro.compose.jetbrains.expui.window.JBWindow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin
import org.thejohnsondev.vault.di.KoinInitializer
import org.thejohnsondev.vault.root.Root
import vaultmultiplatform.composeapp.generated.resources.Res
import vaultmultiplatform.composeapp.generated.resources.ic_vault_1024_dark_mac
import vaultmultiplatform.core.ui.generated.resources.app_name
import vaultmultiplatform.core.ui.generated.resources.ic_vault_24_gradient
import java.awt.Dimension

fun main() = application {
    initKoin()

    val getFirstScreenRouteUseCase: GetFirstScreenRouteUseCase = remember {
        getKoin().get()
    }
    val getSettingsUseCase: GetSettingsFlowUseCase = remember {
        getKoin().get()
    }
    val deviceThemeConfig: DeviceThemeConfig = remember {
        getKoin().get()
    }
    val localizationUtils: LocalizationUtils = remember {
        getKoin().get()
    }
    val checkInstallIDUseCase = remember {
        getKoin().get<CheckInstallIDUseCase>()
    }
    val getAnalyticsPropsUseCase = remember {
        getKoin().get<GetAnalyticsPropsUseCase>()
    }
    val coroutineScope = rememberCoroutineScope()
    val firstScreenRoute = remember { mutableStateOf<Routes?>(null) }
    val settingsConfig = remember { mutableStateOf<SettingsConfig?>(null) }
    val checkInstallIDResult = remember { mutableStateOf<Boolean?>(null) }
    val analyticsProps = remember { mutableStateOf<AnalyticsProps?>(null) }

    LaunchedEffect(true) {
        coroutineScope.launch {
            firstScreenRoute.value = getFirstScreenRouteUseCase()
        }
        coroutineScope.launch {
            getSettingsUseCase.invoke().collect {
                settingsConfig.value = it
            }
        }
        coroutineScope.launch {
            checkInstallIDResult.value = checkInstallIDUseCase()
        }
        coroutineScope.launch {
            analyticsProps.value = getAnalyticsPropsUseCase()
        }
        coroutineScope.launch {
            applySelectedLanguage(localizationUtils)
        }
    }

    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        position = WindowPosition.Aligned(Alignment.Center),
        size = DpSize(DESKTOP_WINDOW_DEFAULT_WIDTH.dp, DESKTOP_WINDOW_DEFAULT_HEIGHT.dp),
    )

    AdaptiveWindow(
        exitApplication = ::exitApplication,
        windowState = windowState
    ) {
        safeLet(
            firstScreenRoute.value,
            settingsConfig.value,
            analyticsProps.value,
            checkInstallIDResult.value
        ) { route, settings, analyticsProps, _ ->
            Root(deviceThemeConfig, route, settings, analyticsProps)
        } ?: kotlin.run {
            DesktopSplash()
        }
    }
}

@Composable
fun AdaptiveWindow(
    exitApplication: () -> Unit,
    windowState: WindowState,
    content: @Composable () -> Unit
) {
    if (System.getProperty("os.name").contains("Mac")) {
        androidx.compose.ui.window.Window(
            onCloseRequest = exitApplication,
            title = stringResource(ResString.app_name),
            icon = painterResource(Res.drawable.ic_vault_1024_dark_mac),
            state = windowState
        ) {
            window.minimumSize = Dimension(DESKTOP_WINDOW_MIN_WIDTH, DESKTOP_WINDOW_MIN_HEIGHT)
            content()
        }
    } else {
        JBWindow(
            onCloseRequest = exitApplication,
            title = stringResource(ResString.app_name),
            icon = painterResource(ResDrawable.ic_vault_24_gradient),
            theme = DarkTheme,
            state = windowState
        ) {
            window.minimumSize = Dimension(DESKTOP_WINDOW_MIN_WIDTH, DESKTOP_WINDOW_MIN_HEIGHT)
            content()
        }
    }

}

private suspend fun applySelectedLanguage(localizationUtils: LocalizationUtils) {
    val selectedLanguage = localizationUtils.getSelectedLanguage()
    localizationUtils.setSelectedLanguage(selectedLanguage)
}

@Composable
private fun initKoin() {
    val platformDependency: PlatformDependency = getPlatformDependency()
    val analyticsDependency: AnalyticsDependency = getAnalyticsDependency()
    KoinInitializer(
        platformDependency = platformDependency,
        analyticsDependency = analyticsDependency
    ).init()
}

private fun getPlatformDependency(): PlatformDependency {
    return DesktopPlatformDependency()
}

private fun getAnalyticsDependency(): AnalyticsDependency {
    return when (AppType.from(BuildKonfigProvider.getAppType())) {
        AppType.DEMO -> {
            DemoAnalyticsDependency()
        }
        AppType.DEV,
        AppType.PROD -> {
            DesktopAnalyticsDependency()
        }
    }
}