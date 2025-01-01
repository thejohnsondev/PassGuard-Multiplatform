package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.filters

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryOtherBackgroundDark
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryOtherBackgroundLight
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryOtherContentDark
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryOtherContentLight

object OtherSelectableItemColors : SelectableItemColors {
    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryOtherContentDark
        } else {
            themeColorCategoryOtherContentLight
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryOtherBackgroundDark
        } else {
            themeColorCategoryOtherBackgroundLight
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryOtherBackgroundDark
        } else {
            themeColorCategoryOtherBackgroundLight
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            themeColorCategoryOtherContentDark
        } else {
            themeColorCategoryOtherContentLight
        }
    }
}