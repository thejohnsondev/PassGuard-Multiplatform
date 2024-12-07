package com.thejohnsondev.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.presentation.SettingsScreen
import com.thejohnsondev.presentation.SettingsViewModel
import com.thejohnsondev.ui.model.ScaffoldConfig
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.settingsScreen(
    windowSize: WindowWidthSizeClass,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
    onLogoutClick: () -> Unit,
) {
    composable(
        route = Screens.Settings.name
    ) {
        val viewModel = koinViewModel<SettingsViewModel>()
        SettingsScreen(windowSize, viewModel, setScaffoldConfig, onLogoutClick)
    }
}