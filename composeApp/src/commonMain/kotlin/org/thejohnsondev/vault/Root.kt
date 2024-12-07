package org.thejohnsondev.vault

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme
import org.koin.compose.KoinContext
import org.thejohnsondev.vault.navigation.AuthNavigation

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun Root(
    deviceThemeConfig: DeviceThemeConfig,
    firstScreenRoute: String
) {
    LaunchedEffect(true) {
        initializeLibs()
    }
    val windowSizeClass = calculateWindowSizeClass()
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = deviceThemeConfig
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