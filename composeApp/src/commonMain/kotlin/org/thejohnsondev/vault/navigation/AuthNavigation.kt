package org.thejohnsondev.vault.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.presentation.navigation.biometricLoginScreen
import com.thejohnsondev.presentation.navigation.loginScreen
import com.thejohnsondev.presentation.navigation.navigateToLogin
import com.thejohnsondev.presentation.navigation.navigateToSelectVaultTypeRoute
import com.thejohnsondev.presentation.navigation.navigateToSignUp
import com.thejohnsondev.presentation.navigation.selectVaultTypeScreen
import com.thejohnsondev.presentation.navigation.signUpScreen
import com.thejohnsondev.presentation.navigation.welcomeScreen

@Composable
fun AuthNavigation(
    windowSizeClass: WindowWidthSizeClass,
    firstScreenRoute: Routes,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = firstScreenRoute
    ) {
        welcomeScreen(windowSize = windowSizeClass, goToSelectVaultType = {
            navController.navigateToSelectVaultTypeRoute()
        })
        signUpScreen(windowSize = windowSizeClass, goToHome = {
            navController.navigate(Routes.HomeRoute()) {
                popUpTo(Routes.WelcomeRoute) {
                    inclusive = true
                }
            }
        }, goToLogin = {
            navController.navigateToLogin()
        }, goBack = {
            navController.popBackStack()
        })
        loginScreen(windowSize = windowSizeClass, goToHome = {
            navController.navigate(Routes.HomeRoute(isFromLogin = true)) {
                popUpTo(Routes.WelcomeRoute) {
                    inclusive = true
                }
            }
        }, goToSignUp = {
            navController.navigateToSignUp()
        }, goBack = {
            navController.popBackStack()
        })
        biometricLoginScreen(
            goToHome = {
                navController.navigate(Routes.HomeRoute()) {
                    popUpTo(Routes.BiometricRoute) {
                        inclusive = true
                    }
                }
            }
        )
        selectVaultTypeScreen(windowSize = windowSizeClass, goToHome = {
            navController.navigate(Routes.HomeRoute()) {
                popUpTo(Routes.WelcomeRoute) {
                    inclusive = true
                }
            }
        }, goToLogin = {
            navController.navigateToLogin()
        }, goToSignUp = {
            navController.navigateToSignUp()
        }, goBack = {
            navController.popBackStack()
        })
        composable<Routes.HomeRoute> {
            val route: Routes.HomeRoute = it.toRoute()
            HomeNavigation(
                windowSizeClass = windowSizeClass,
                rootNavController = navController,
                isFromLogin = route.isFromLogin
            )
        }
    }
}