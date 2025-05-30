package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors

object MaterialSelectableItemColors: SelectableItemColors {

    @Composable
    override fun getSelectedContainerColor(): Color {
        return MaterialTheme.colorScheme.surfaceContainer
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return MaterialTheme.colorScheme.secondaryContainer
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return MaterialTheme.colorScheme.onSurface
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return MaterialTheme.colorScheme.onSecondaryContainer
    }

}