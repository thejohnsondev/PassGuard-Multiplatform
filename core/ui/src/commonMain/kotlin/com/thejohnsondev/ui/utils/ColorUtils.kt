package com.thejohnsondev.ui.utils

import androidx.compose.ui.graphics.Color

inline fun Color.darken(darkenBy: Float = 0.3f): Color {
    return copy(
        red = red * darkenBy,
        green = green * darkenBy,
        blue = blue * darkenBy,
        alpha = alpha
    )
}