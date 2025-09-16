package com.thejohnsondev.ui.components.animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import com.thejohnsondev.ui.designsystem.Size19
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size20

@Composable
fun getDefaultAnimatedBorderColors() = listOf(
    MaterialTheme.colorScheme.onPrimaryContainer,
    MaterialTheme.colorScheme.primaryContainer,
    MaterialTheme.colorScheme.onPrimaryContainer,
    MaterialTheme.colorScheme.primaryContainer,
    MaterialTheme.colorScheme.onPrimaryContainer,
    MaterialTheme.colorScheme.primaryContainer,
    MaterialTheme.colorScheme.onPrimaryContainer,
)

@Composable
fun CardWithAnimatedBorder(
    modifier: Modifier = Modifier,
    borderColors: List<Color> = emptyList(),
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val borderAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.sweepGradient(borderColors.map { it.copy(alpha = borderAlpha) })

    Surface(modifier = modifier, shape = RoundedCornerShape(Size20), color = Color.Transparent) {
        Surface(
            modifier = Modifier
                .clipToBounds()
                .wrapContentSize()
                .padding(Size2)
                .drawWithContent {
                    rotate(angle) {
                        drawCircle(
                            brush = brush,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                }
                .background(Color.Transparent),
            shape = RoundedCornerShape(Size19)
        ) {
            Box { content() }
        }
    }
}