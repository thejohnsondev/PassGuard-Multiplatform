package org.thejohnsondev.vault.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.presentation.navigation.loginScreen
import com.thejohnsondev.presentation.navigation.navigateToLogin
import com.thejohnsondev.presentation.navigation.navigateToSignUp
import com.thejohnsondev.presentation.navigation.signUpScreen
import com.thejohnsondev.presentation.navigation.welcomeScreen

@Composable
fun AuthNavigation(
    windowSizeClass: WindowWidthSizeClass,
    firstScreenRoute: Routes
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = firstScreenRoute
    ) {
        welcomeScreen(
            windowSize = windowSizeClass,
            goToSignUp = {
                navController.navigateToSignUp()
            },
            goToLogin = {
                navController.navigateToLogin()
            }
        )
        signUpScreen(
            windowSize = windowSizeClass,
            goToHome = {
                navController.navigate(Routes.HomeRoute)
            },
            goToLogin = {
                navController.navigateToLogin()
            },
            goBack = {
                navController.popBackStack()
            }
        )
        loginScreen(
            windowSize = windowSizeClass,
            goToHome = {
                navController.navigate(Routes.HomeRoute)
            },
            goToSignUp = {
                navController.navigateToSignUp()
            },
            goBack = {
                navController.popBackStack()
            }
        )
        composable<Routes.HomeRoute> {
            HomeNavigation(windowSizeClass, rootNavController = navController)
        }
    }
}