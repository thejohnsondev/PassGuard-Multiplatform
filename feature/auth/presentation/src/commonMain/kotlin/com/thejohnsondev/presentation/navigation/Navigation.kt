package com.thejohnsondev.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.presentation.login.LoginScreen
import com.thejohnsondev.presentation.login.LoginViewModel
import com.thejohnsondev.presentation.signup.SignUpScreen
import com.thejohnsondev.presentation.signup.SignUpViewModel
import com.thejohnsondev.presentation.welcome.WelcomeScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

fun NavController.navigateToLogin() {
    navigate(
        Routes.LoginRoute
    ) {
        popUpTo(Routes.LoginRoute) {
            inclusive = true
        }
    }
}

fun NavController.navigateToSignUp() {
    navigate(
        Routes.SignUpRoute
    ) {
        popUpTo(Routes.SignUpRoute) {
            inclusive = true
        }
    }
}

fun NavController.navigateToWelcome() {
    navigate(
        Routes.WelcomeRoute
    ) {
        popUpTo(Routes.WelcomeRoute) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.welcomeScreen(
    windowSize: WindowWidthSizeClass,
    goToSignUp: () -> Unit,
    goToLogin: () -> Unit
) {
    composable<Routes.WelcomeRoute> {
        WelcomeScreen(
            windowSize = windowSize,
            goToSignUp = goToSignUp,
            goToLogin = goToLogin
        )
    }
}

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.loginScreen(
    windowSize: WindowWidthSizeClass,
    goToHome: () -> Unit,
    goToSignUp: () -> Unit,
    goBack: () -> Unit
) {
    composable<Routes.LoginRoute> {
        val viewModel = koinViewModel<LoginViewModel>()
        LoginScreen(
            windowSize = windowSize,
            viewModel = viewModel,
            goToHome = goToHome,
            goToSignUp = goToSignUp,
            goBack = goBack
        )
    }
}

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.signUpScreen(
    windowSize: WindowWidthSizeClass,
    goToHome: () -> Unit,
    goToLogin: () -> Unit,
    goBack: () -> Unit
) {
    composable<Routes.SignUpRoute> {
        val viewModel = koinViewModel<SignUpViewModel>()
        SignUpScreen(
            windowSize = windowSize,
            viewModel = viewModel,
            goToHome = goToHome,
            goToLogin = goToLogin,
            goBack = goBack
        )
    }
}