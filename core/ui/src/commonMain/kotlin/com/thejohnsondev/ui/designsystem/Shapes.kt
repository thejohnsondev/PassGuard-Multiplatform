package com.thejohnsondev.ui.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

val EquallyRounded = Shapes(
    extraSmall = RoundedCornerShape(Size4),
    small = RoundedCornerShape(Size8),
    medium = RoundedCornerShape(Size16),
    large = RoundedCornerShape(Size32)
)

val TopRounded = RoundedCornerShape(
    topStart = Size16,
    topEnd = Size16,
    bottomStart = Size4,
    bottomEnd = Size4
)

val BottomRounded = RoundedCornerShape(
    topStart = Size4,
    topEnd = Size4,
    bottomStart = Size16,
    bottomEnd = Size16
)

val StartRounded = RoundedCornerShape(
    topStart = Size16,
    topEnd = Size4,
    bottomStart = Size16,
    bottomEnd = Size4
)

val EndRounded = RoundedCornerShape(
    topStart = Size4,
    topEnd = Size16,
    bottomStart = Size4,
    bottomEnd = Size16
)

val NotRounded = RoundedCornerShape(
    topStart = Size4,
    topEnd = Size4,
    bottomStart = Size4,
    bottomEnd = Size4
)