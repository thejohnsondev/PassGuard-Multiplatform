package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerDark
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerLight
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerDark
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerLight
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerDark
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerLight
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerDark
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerLight

object DefaultSelectableItemColors : SelectableItemColors {
    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            primaryContainerDark
        } else {
            primaryContainerLight
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            secondaryContainerDark
        } else {
            secondaryContainerLight
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onPrimaryContainerDark
        } else {
            onPrimaryContainerLight
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onSecondaryContainerDark
        } else {
            onSecondaryContainerLight
        }
    }
}