package com.thejohnsondev.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

const val COLOR_ID_onPrimaryContainer = "onPrimaryContainer"
const val COLOR_ID_primaryContainer = "primaryContainer"

@Composable
inline fun String.mapToColor(): Color {
    return when (this) {
        COLOR_ID_onPrimaryContainer -> MaterialTheme.colorScheme.onPrimaryContainer
        COLOR_ID_primaryContainer -> MaterialTheme.colorScheme.primaryContainer
        else -> Color.Black
    }
}