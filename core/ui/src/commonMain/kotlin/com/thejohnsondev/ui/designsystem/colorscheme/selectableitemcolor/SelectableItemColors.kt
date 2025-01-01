package com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface SelectableItemColors {
    @Composable
    fun getSelectedContainerColor(): Color
    @Composable
    fun getUnselectedContainerColor(): Color
    @Composable
    fun getSelectedContentColor(): Color
    @Composable
    fun getUnselectedContentColor(): Color
}