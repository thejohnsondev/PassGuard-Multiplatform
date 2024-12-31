package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerDark_sunny
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerLight_sunny
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerDark_sunny
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerLight_sunny
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerDark_sunny
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerLight_sunny
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerDark_sunny
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerLight_sunny
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors

object SunnySelectableItemColors : SelectableItemColors {
    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            primaryContainerDark_sunny
        } else {
            primaryContainerLight_sunny
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            secondaryContainerDark_sunny
        } else {
            secondaryContainerLight_sunny
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onPrimaryContainerDark_sunny
        } else {
            onPrimaryContainerLight_sunny
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onSecondaryContainerDark_sunny
        } else {
            onSecondaryContainerLight_sunny
        }
    }
}