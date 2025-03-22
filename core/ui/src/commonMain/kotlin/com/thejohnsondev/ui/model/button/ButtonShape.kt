package com.thejohnsondev.ui.model.button

import androidx.compose.ui.unit.Dp
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size4

enum class ButtonShape(
    val topStart: Dp,
    val topEnd: Dp,
    val bottomStart: Dp,
    val bottomEnd: Dp
) {
    ROUNDED(Size16, Size16, Size16, Size16),
}