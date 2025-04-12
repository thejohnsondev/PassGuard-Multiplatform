package com.thejohnsondev.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.presentation.ToolsScreen
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.model.message.MessageContent

fun NavGraphBuilder.toolsScreen(
    windowSize: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
    showMessage: (MessageContent) -> Unit,
) {
    composable<Routes.ToolsRoute> {
        ToolsScreen(
            windowSizeClass = windowSize,
            paddingValues = paddingValues,
            setScaffoldConfig = setScaffoldConfig,
            showMessage = showMessage
        )
    }
}