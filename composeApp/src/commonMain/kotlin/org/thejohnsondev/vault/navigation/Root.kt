package org.thejohnsondev.vault.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.presentation.navigation.loginScreen
import com.thejohnsondev.presentation.navigation.navigateToLogin
import com.thejohnsondev.presentation.navigation.navigateToSignUp
import com.thejohnsondev.presentation.navigation.signUpScreen
import com.thejohnsondev.presentation.navigation.welcomeScreen
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.designsystem.VaultTheme
import org.koin.compose.KoinContext

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun Root(
    deviceThemeConfig: DeviceThemeConfig
) {
    LaunchedEffect(true) {
        initializeLibs()
    }
    val windowSizeClass = calculateWindowSizeClass()
    VaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = deviceThemeConfig
    ) {
        KoinContext {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AuthNavigation(windowSizeClass)
            }
        }
    }
}

private fun initializeLibs() {
    Logger.initialize()
}