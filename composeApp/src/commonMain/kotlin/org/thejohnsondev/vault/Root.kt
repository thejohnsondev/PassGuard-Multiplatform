package org.thejohnsondev.vault

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thejohnsondev.presentation.SignUpScreen
import com.thejohnsondev.presentation.SignUpViewModel
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.designsystem.VaultTheme
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun Root(
    deviceThemeConfig: DeviceThemeConfig
) {
    VaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = deviceThemeConfig
    ) {
        KoinContext {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "signup"
            ) {
                composable("signup") {
                    val viewModel = koinViewModel<SignUpViewModel>()
                    SignUpScreen(viewModel)
                }
            }
        }
    }
}