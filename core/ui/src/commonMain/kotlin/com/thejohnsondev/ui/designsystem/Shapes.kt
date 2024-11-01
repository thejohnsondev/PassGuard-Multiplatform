package com.thejohnsondev.ui.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

val TopRounded = Shapes(
    medium = RoundedCornerShape(topStart = Size16, topEnd = Size16),
)

val EqualRounded = Shapes(
    small = RoundedCornerShape(Size8),
    medium = RoundedCornerShape(Size16),
    large = RoundedCornerShape(Size32)
)