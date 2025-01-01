package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerDark_redAlgae
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerLight_redAlgae
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerDark_redAlgae
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerLight_redAlgae
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerDark_redAlgae
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerLight_redAlgae
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerDark_redAlgae
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerLight_redAlgae
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors

object RedAlgaeSelectableItemColors : SelectableItemColors {
    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            primaryContainerDark_redAlgae
        } else {
            primaryContainerLight_redAlgae
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            secondaryContainerDark_redAlgae
        } else {
            secondaryContainerLight_redAlgae
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onPrimaryContainerDark_redAlgae
        } else {
            onPrimaryContainerLight_redAlgae
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onSecondaryContainerDark_redAlgae
        } else {
            onSecondaryContainerLight_redAlgae
        }
    }
}