package com.thejohnsondev.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size36
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size56
import com.thejohnsondev.ui.designsystem.Size64
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors

@Composable
fun SelectableThemeOptionItem(
    modifier: Modifier = Modifier,
    optionTitle: String,
    isSelected: Boolean,
    colors: SelectableItemColors? = null,
    onOptionSelect: () -> Unit = {},
) {
    val selectedContainerColor =
        colors?.getSelectedContainerColor() ?: MaterialTheme.colorScheme.primaryContainer
    val selectedContentColor =
        colors?.getSelectedContentColor() ?: MaterialTheme.colorScheme.onPrimaryContainer
    val surfaceColor = MaterialTheme.colorScheme.surfaceContainer

    Surface(
        modifier = modifier
            .wrapContentWidth()
            .defaultMinSize(minWidth = Size56)
            .height(if (isSelected) Size64 else Size56)
            .wrapContentHeight()
            .clip(RoundedCornerShape(Size16))
            .clickable {
                onOptionSelect()
            },
        color = surfaceColor,
    ) {
        Row(
            modifier = Modifier
                .padding(Size12),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            HalfColoredCircle(
                modifier = Modifier.size(Size36),
                color1 = selectedContainerColor,
                color2 = selectedContentColor
            )
            AnimatedVisibility(isSelected) {
                Text(
                    modifier = Modifier.padding(start = Size8, end = Size4),
                    text = optionTitle,
                    color = selectedContentColor
                )
            }
        }
    }
}

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