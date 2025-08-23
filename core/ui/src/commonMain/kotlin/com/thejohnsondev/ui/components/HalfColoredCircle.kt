package com.thejohnsondev.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun HalfColoredCircle(
    modifier: Modifier = Modifier,
    color1: Color,
    color2: Color,
    angle: Float = 60f,
) {
    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)

        drawArc(
            brush = Brush.sweepGradient(listOf(color1, color1)),
            startAngle = angle,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )

        drawArc(
            brush = Brush.sweepGradient(listOf(color2, color2)),
            startAngle = angle + 180f,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )
    }
}