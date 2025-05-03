package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.tools

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors

class ToolSelectableItemColors(
    private val swapColors: Boolean = true,
) : SelectableItemColors {

    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (swapColors) MaterialTheme.colorScheme.surfaceContainerHigh else MaterialTheme.colorScheme.surfaceContainer
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return MaterialTheme.colorScheme.surfaceContainer
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (swapColors) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return MaterialTheme.colorScheme.onSurface
    }

}