package com.thejohnsondev.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.presentation.SettingsScreen
import com.thejohnsondev.presentation.SettingsViewModel
import com.thejohnsondev.ui.model.ScaffoldConfig
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.settingsScreen(
    windowSize: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
    onLogoutClick: () -> Unit,
) {
    composable<Routes.SettingsRoute> {
        val viewModel = koinViewModel<SettingsViewModel>()
        SettingsScreen(
            windowSizeClass = windowSize,
            paddingValues = paddingValues,
            viewModel = viewModel,
            setScaffoldConfig = setScaffoldConfig,
            onLogoutClick = onLogoutClick
        )
    }
}