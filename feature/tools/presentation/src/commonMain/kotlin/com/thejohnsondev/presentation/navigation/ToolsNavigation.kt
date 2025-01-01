package com.thejohnsondev.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.presentation.ToolsScreen
import com.thejohnsondev.ui.model.ScaffoldConfig

fun NavGraphBuilder.toolsScreen(
    windowSize: WindowWidthSizeClass,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
) {
    composable<Routes.ToolsRoute> {
        ToolsScreen(windowSize, setScaffoldConfig)
    }
}