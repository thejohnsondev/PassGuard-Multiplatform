package org.thejohnsondev.vault.root

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.thejohnsondev.analytics.Analytics
import com.thejohnsondev.analytics.AnalyticsPlatform
import com.thejohnsondev.analytics.posthog.PosthogAnalyticsConfig
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.domain.model.AnalyticsProps
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme
import org.koin.compose.KoinContext
import org.koin.mp.KoinPlatform
import org.thejohnsondev.vault.navigation.AuthNavigation

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun Root(
    deviceThemeConfig: DeviceThemeConfig,
    firstScreenRoute: Routes,
    settingsConfig: SettingsConfig,
    analyticsProps: AnalyticsProps
) {
    LaunchedEffect(true) {
        initializeLogger()
        initAnalytics(analyticsProps)
    }
    val windowSizeClass = calculateWindowSizeClass()
    VaultDefaultTheme(
        dynamicColor = shouldUseDynamicColor(settingsConfig),
        darkTheme = shouldUseDarkTheme(settingsConfig),
        deviceThemeConfig = deviceThemeConfig,
        customTheme = settingsConfig.customTheme
    ) {
        KoinContext {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AuthNavigation(windowSizeClass.widthSizeClass, firstScreenRoute)
            }
        }
    }
}

private fun initializeLogger() {
    Logger.initialize()
}


private fun initAnalytics(analyticsProps: AnalyticsProps) {
    val platform: AnalyticsPlatform = KoinPlatform.getKoin().get()
    val config = PosthogAnalyticsConfig(
        apiKey = BuildKonfigProvider.getPosthogApiKey(),
        host = BuildKonfigProvider.getPosthogHost()
    )
    Logger.d("Initializing analytics with props: $analyticsProps")
    Analytics.apply {
        // TODO update analytics props when: user logs in/out, changes theme, changes vault type
        setInstallId(analyticsProps.installID)
        setAppTheme(analyticsProps.darkThemeConfig.name)
        setVaultType(analyticsProps.vaultType?.name)
        setVaultInitialized(analyticsProps.isVaultInitialized)
    }
    Analytics.init(config, platform)
}

@Composable
fun shouldUseDarkTheme(
    settingsConfig: SettingsConfig
): Boolean = when (settingsConfig.darkThemeConfig) {
    DarkThemeConfig.LIGHT -> false
    DarkThemeConfig.DARK -> true
    DarkThemeConfig.SYSTEM -> isSystemInDarkTheme()
    else -> true
}

@Composable
private fun shouldUseDynamicColor(
    settingsConfig: SettingsConfig,
): Boolean = settingsConfig.useDynamicColor