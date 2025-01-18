package com.thejohnsondev.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.Size4

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    iconTintColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = iconTintColor,
        strokeWidth = Size4
    )
}