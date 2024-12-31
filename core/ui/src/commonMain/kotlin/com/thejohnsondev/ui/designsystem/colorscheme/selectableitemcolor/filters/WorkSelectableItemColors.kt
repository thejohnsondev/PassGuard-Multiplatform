package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.filters

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryWorkBackgroundDark
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryWorkBackgroundLight
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryWorkContentDark
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryWorkContentLight

object WorkSelectableItemColors : SelectableItemColors {
    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryWorkContentDark
        } else {
            themeColorCategoryWorkContentLight
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryWorkBackgroundDark
        } else {
            themeColorCategoryWorkBackgroundLight
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryWorkBackgroundDark
        } else {
            themeColorCategoryWorkBackgroundLight
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryWorkContentDark
        } else {
            themeColorCategoryWorkContentLight
        }
    }
}