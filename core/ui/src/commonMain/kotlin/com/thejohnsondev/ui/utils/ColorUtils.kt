package com.thejohnsondev.ui.utils

import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorStrengthHigh
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorStrengthLow
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorStrengthMedium
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorStrengthMediumHigh
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorStrengthMediumLow

inline fun Color.darken(darkenBy: Float = 0.3f): Color {
    return copy(
        red = red * darkenBy,
        green = green * darkenBy,
        blue = blue * darkenBy,
        alpha = alpha
    )
}

inline fun Float.mapToStrengthLevelColor(): Color {
    return when (this) {
        in 0.0..0.2 -> themeColorStrengthLow
        in 0.3..0.4 -> themeColorStrengthMediumLow
        0.5f -> themeColorStrengthMedium
        in 0.6..0.7 -> themeColorStrengthMediumHigh
        in 0.8..1.0 -> themeColorStrengthHigh
        else -> {
            themeColorStrengthMedium
        }
    }
}