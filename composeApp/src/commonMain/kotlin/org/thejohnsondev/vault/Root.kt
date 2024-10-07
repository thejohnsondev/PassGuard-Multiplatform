package org.thejohnsondev.vault

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thejohnsondev.presentation.SignUpScreen
import com.thejohnsondev.presentation.SignUpViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun Root() {
    MaterialTheme {
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