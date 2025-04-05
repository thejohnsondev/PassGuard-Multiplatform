package com.thejohnsondev.ui.components

import androidx.compose.ui.graphics.vector.ImageVector

sealed class TextFieldIconBehavior {
    data object None : TextFieldIconBehavior()
    data object HideShow : TextFieldIconBehavior()
    data class Clear(val onClick: () -> Unit) : TextFieldIconBehavior()
    data class Icon(val icon: ImageVector, val onClick: (() -> Unit)? = null) : TextFieldIconBehavior()
}