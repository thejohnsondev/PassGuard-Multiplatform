package org.thejohnsondev.vault

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun Root(
    deviceThemeConfig: DeviceThemeConfig
) {
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
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "signup"
                ) {
                    composable("signup") {
                        val viewModel = koinViewModel<SignUpViewModel>()
                        SignUpScreen(
                            windowSizeClass.widthSizeClass,
                            viewModel, {
                                // TODO implement navigation later
                            }, {
                                // TODO implement navigation later
                            })
                    }
                }
            }
        }
    }
}