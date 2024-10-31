package com.thejohnsondev.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.presentation.VaultScreen

fun NavGraphBuilder.vaultScreen(
    windowSize: WindowWidthSizeClass,
    onClick: () -> Unit
) {
    composable(
        route = Screens.VaultScreen.name
    ) {
        VaultScreen(onClick)
    }
}