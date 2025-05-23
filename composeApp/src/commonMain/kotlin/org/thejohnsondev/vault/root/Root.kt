package org.thejohnsondev.vault.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme
import org.koin.compose.KoinContext
import org.thejohnsondev.vault.navigation.AuthNavigation
import androidx.compose.foundation.isSystemInDarkTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun Root(
    deviceThemeConfig: DeviceThemeConfig,
    firstScreenRoute: Routes,
    settingsConfig: SettingsConfig,
) {
    LaunchedEffect(true) {
        initializeLibs()
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

private fun initializeLibs() {
    Logger.initialize()
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