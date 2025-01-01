package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.filters

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryPersonalBackgroundDark
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryPersonalBackgroundLight
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryPersonalContentDark
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryPersonalContentLight

object PersonalSelectableItemColors : SelectableItemColors {
    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryPersonalContentDark
        } else {
            themeColorCategoryPersonalContentLight
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryPersonalBackgroundDark
        } else {
            themeColorCategoryPersonalBackgroundLight
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryPersonalBackgroundDark
        } else {
            themeColorCategoryPersonalBackgroundLight
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryPersonalContentDark
        } else {
            themeColorCategoryPersonalContentLight
        }
    }
}