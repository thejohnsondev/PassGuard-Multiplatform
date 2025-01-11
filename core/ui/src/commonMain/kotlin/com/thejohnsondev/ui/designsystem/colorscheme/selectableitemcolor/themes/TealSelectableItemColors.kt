package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerDark_teal
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerLight_teal
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerDark_teal
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerLight_teal
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerDark_teal
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerLight_teal
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerDark_teal
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerLight_teal
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors

object TealSelectableItemColors : SelectableItemColors {
    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            primaryContainerDark_teal
        } else {
            primaryContainerLight_teal
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            secondaryContainerDark_teal
        } else {
            secondaryContainerLight_teal
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onPrimaryContainerDark_teal
        } else {
            onPrimaryContainerLight_teal
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onSecondaryContainerDark_teal
        } else {
            onSecondaryContainerLight_teal
        }
    }
}