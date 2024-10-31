package org.thejohnsondev.vault.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.presentation.navigation.navigateToWelcome

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
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(text = "Vault Screen")
                Button(
                    onClick = {
                        rootNavController.navigateToWelcome()
                    },
                ) {
                    Text("Logout")
                }

            }
        }
    }
}