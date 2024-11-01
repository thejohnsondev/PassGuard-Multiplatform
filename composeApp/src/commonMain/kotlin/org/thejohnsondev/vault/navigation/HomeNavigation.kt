package org.thejohnsondev.vault.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.presentation.navigation.navigateToWelcome
import com.thejohnsondev.presentation.navigation.vaultScreen

@Composable
fun HomeNavigation(
    windowSizeClass: WindowWidthSizeClass,
    rootNavController: NavHostController
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.VaultScreen.name
    ) {
        vaultScreen(
            windowSize = windowSizeClass,
                onClick = {
                    rootNavController.navigateToWelcome()
                }
        )
    }
}