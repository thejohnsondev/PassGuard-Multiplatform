package com.thejohnsondev.ui.model.button

import androidx.compose.ui.unit.Dp
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size4

enum class ButtonShape(
    val topStart: Dp,
    val topEnd: Dp,
    val bottomStart: Dp,
    val bottomEnd: Dp,
) {
    ROUNDED(topStart = Size16, topEnd = Size16, bottomStart = Size16, bottomEnd = Size16),
    TopRounded(topStart = Size16, topEnd = Size16, bottomStart = Size4, bottomEnd = Size4),
}