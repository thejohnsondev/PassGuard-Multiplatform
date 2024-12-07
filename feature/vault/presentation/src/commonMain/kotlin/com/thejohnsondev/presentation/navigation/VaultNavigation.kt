package com.thejohnsondev.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.presentation.vault.VaultScreen
import com.thejohnsondev.presentation.vault.VaultViewModel
import com.thejohnsondev.ui.model.ScaffoldConfig
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.vaultScreen(
    windowSize: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
    updateIsEmptyVault: (Boolean) -> Unit
) {
    composable(
        route = Screens.VaultScreen.name
    ) {
        val viewModel = koinViewModel<VaultViewModel>()
        VaultScreen(windowSize, viewModel, paddingValues, setScaffoldConfig, updateIsEmptyVault)
    }
}