package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.filters

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryFinanceBackgroundDark
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryFinanceBackgroundLight
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryFinanceContentDark
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryFinanceContentLight

object FinanceSelectableItemColors: SelectableItemColors {
    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryFinanceContentDark
        } else {
            themeColorCategoryFinanceContentLight
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryFinanceBackgroundDark
        } else {
            themeColorCategoryFinanceBackgroundLight
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryFinanceBackgroundDark
        } else {
            themeColorCategoryFinanceBackgroundLight
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryFinanceContentDark
        } else {
            themeColorCategoryFinanceContentLight
        }
    }
}