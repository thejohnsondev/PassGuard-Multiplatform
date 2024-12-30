package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerDark_blueSky
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerLight_blueSky
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerDark_blueSky
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerLight_blueSky
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerDark_blueSky
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerLight_blueSky
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerDark_blueSky
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerLight_blueSky

object BlueSkySelectableItemColors : SelectableItemColor {
    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            primaryContainerDark_blueSky
        } else {
            primaryContainerLight_blueSky
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            secondaryContainerDark_blueSky
        } else {
            secondaryContainerLight_blueSky
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onPrimaryContainerDark_blueSky
        } else {
            onPrimaryContainerLight_blueSky
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onSecondaryContainerDark_blueSky
        } else {
            onSecondaryContainerLight_blueSky
        }
    }
}