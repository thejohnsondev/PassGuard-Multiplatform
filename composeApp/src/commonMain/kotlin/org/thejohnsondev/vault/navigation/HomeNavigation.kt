package org.thejohnsondev.vault.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.presentation.navigation.navigateToWelcome
import com.thejohnsondev.presentation.navigation.vaultScreen

@Composable
fun HomeNavigation(
    windowSizeClass: WindowSizeClass,
    rootNavController: NavHostController
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.VaultScreen.name
    ) {
        composable(
            route = Screens.VaultScreen.name
        ) {
            vaultScreen(
                windowSize = windowSizeClass.widthSizeClass,
                onClick = {
                    rootNavController.navigateToWelcome()
                }
            )
        }
    }
}