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
    RECTANGLE(Size4, Size4, Size4, Size4),
    ROUNDED(Size16, Size16, Size16, Size16),
    TOP_ROUNDED(Size16, Size16, Size4, Size4),
    BOTTOM_ROUNDED(Size4, Size4, Size16, Size16),
    START_ROUNDED(Size16, Size4, Size16, Size4),
    END_ROUNDED(Size4, Size16, Size4, Size16)
}