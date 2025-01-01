package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerDark_deepForest
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerLight_deepForest
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerDark_deepForest
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerLight_deepForest
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerDark_deepForest
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerLight_deepForest
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerDark_deepForest
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerLight_deepForest
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors

object DeepForestSelectableItemColors : SelectableItemColors {
    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            primaryContainerDark_deepForest
        } else {
            primaryContainerLight_deepForest
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            secondaryContainerDark_deepForest
        } else {
            secondaryContainerLight_deepForest
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onPrimaryContainerDark_deepForest
        } else {
            onPrimaryContainerLight_deepForest
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onSecondaryContainerDark_deepForest
        } else {
            onSecondaryContainerLight_deepForest
        }
    }
}