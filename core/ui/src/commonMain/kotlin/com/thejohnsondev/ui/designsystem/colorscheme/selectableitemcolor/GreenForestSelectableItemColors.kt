package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerDark_greenForest
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerLight_greenForest
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerDark_greenForest
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerLight_greenForest
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerDark_greenForest
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerLight_greenForest
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerDark_greenForest
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerLight_greenForest

object GreenForestSelectableItemColors : SelectableItemColor {
    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            primaryContainerDark_greenForest
        } else {
            primaryContainerLight_greenForest
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            secondaryContainerDark_greenForest
        } else {
            secondaryContainerLight_greenForest
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onPrimaryContainerDark_greenForest
        } else {
            onPrimaryContainerLight_greenForest
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onSecondaryContainerDark_greenForest
        } else {
            onSecondaryContainerLight_greenForest
        }
    }
}