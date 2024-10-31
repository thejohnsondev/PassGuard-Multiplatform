package org.thejohnsondev.vault.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.presentation.navigation.loginScreen
import com.thejohnsondev.presentation.navigation.navigateToLogin
import com.thejohnsondev.presentation.navigation.navigateToSignUp
import com.thejohnsondev.presentation.navigation.signUpScreen
import com.thejohnsondev.presentation.navigation.welcomeScreen

@Composable
fun AuthNavigation(
    windowSizeClass: WindowSizeClass
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Welcome.name
    ) {
        welcomeScreen(
            windowSize = windowSizeClass.widthSizeClass,
            goToSignUp = {
                navController.navigateToSignUp()
            },
            goToLogin = {
                navController.navigateToLogin()
            }
        )
        signUpScreen(
            windowSize = windowSizeClass.widthSizeClass,
            goToHome = {
                navController.navigate(Screens.HomeScreen.name)
            },
            goToLogin = {
                navController.navigateToLogin()
            },
            goBack = {
                navController.popBackStack()
            }
        )
        loginScreen(
            windowSize = windowSizeClass.widthSizeClass,
            goToHome = {
                navController.navigate(Screens.HomeScreen.name)
            },
            goToSignUp = {
                navController.navigateToSignUp()
            },
            goBack = {
                navController.popBackStack()
            }
        )
        composable(
            route = Screens.HomeScreen.name
        ) {
            HomeNavigation(windowSizeClass, rootNavController = navController)
        }
    }
}