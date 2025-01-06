package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerDark_monochrome
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerDark_violet
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerLight_monochrome
import com.thejohnsondev.ui.designsystem.colorscheme.onPrimaryContainerLight_violet
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerDark_monochrome
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerDark_violet
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerLight_monochrome
import com.thejohnsondev.ui.designsystem.colorscheme.onSecondaryContainerLight_violet
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerDark_monochrome
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerDark_violet
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerLight_monochrome
import com.thejohnsondev.ui.designsystem.colorscheme.primaryContainerLight_violet
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerDark_monochrome
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerDark_violet
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerLight_monochrome
import com.thejohnsondev.ui.designsystem.colorscheme.secondaryContainerLight_violet
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors

object MonochromeSelectableItemsColors : SelectableItemColors {

    @Composable
    override fun getSelectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            primaryContainerDark_monochrome
        } else {
            primaryContainerLight_monochrome
        }
    }

    @Composable
    override fun getUnselectedContainerColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            secondaryContainerDark_monochrome
        } else {
            secondaryContainerLight_monochrome
        }
    }

    @Composable
    override fun getSelectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onPrimaryContainerDark_monochrome
        } else {
            onPrimaryContainerLight_monochrome
        }
    }

    @Composable
    override fun getUnselectedContentColor(): Color {
        return if (!MaterialTheme.colorScheme.isLight()) {
            onSecondaryContainerDark_monochrome
        } else {
            onSecondaryContainerLight_monochrome
        }
    }

}