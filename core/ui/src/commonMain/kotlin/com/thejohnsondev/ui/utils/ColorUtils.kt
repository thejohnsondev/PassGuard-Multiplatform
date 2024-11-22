package com.thejohnsondev.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.common.COLOR_ID_onPrimaryContainer
import com.thejohnsondev.common.COLOR_ID_primaryContainer
import com.thejohnsondev.common.COLOR_ID_themeColorCategoryFinanceContent
import com.thejohnsondev.common.COLOR_ID_themeColorCategoryOtherContent
import com.thejohnsondev.common.COLOR_ID_themeColorCategoryPersonalContent
import com.thejohnsondev.common.COLOR_ID_themeColorCategoryWorkContent
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryFinanceContent
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryOtherContent
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryPersonalContent
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorCategoryWorkContent


@Composable
inline fun String.mapToColor(): Color {
    return when (this) {
        COLOR_ID_onPrimaryContainer -> MaterialTheme.colorScheme.onPrimaryContainer
        COLOR_ID_primaryContainer -> MaterialTheme.colorScheme.primaryContainer
        COLOR_ID_themeColorCategoryPersonalContent -> themeColorCategoryPersonalContent
        COLOR_ID_themeColorCategoryWorkContent -> themeColorCategoryWorkContent
        COLOR_ID_themeColorCategoryFinanceContent -> themeColorCategoryFinanceContent
        COLOR_ID_themeColorCategoryOtherContent -> themeColorCategoryOtherContent
        else -> Color.Black
    }
}

inline fun Color.darken(darkenBy: Float = 0.3f): Color {
    return copy(
        red = red * darkenBy,
        green = green * darkenBy,
        blue = blue * darkenBy,
        alpha = alpha
    )
}