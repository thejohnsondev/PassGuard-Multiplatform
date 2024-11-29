package com.thejohnsondev.ui.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.thejohnsondev.common.empty
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Percent95
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.SizeBorder

expect fun Modifier.cursorEnterAnimation(): Modifier

enum class ButtonState { Pressed, Idle }

fun Modifier.applyIf(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) this.then(
        modifier(Modifier)
    ) else this
}

fun Modifier.bounceClick(
    minScale: Float = Percent95,
    disableCursorEnterAnimation: Boolean = false
) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        if (buttonState == ButtonState.Pressed) minScale else Percent100,
        label = String.empty
    )

    this.graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
        .applyIf(!disableCursorEnterAnimation) {
            cursorEnterAnimation()
        }

}

fun Modifier.testBorder() = composed {
    this.border(SizeBorder, Color.Red)
}


@Composable
fun CardWithAnimatedBorder(
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
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.sweepGradient(borderColors.map { it.copy(alpha = borderAlpha) })

    Surface(modifier = Modifier, shape = RoundedCornerShape(20.dp)) {
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
                },
            shape = RoundedCornerShape(19.dp)
        ) {
            Box { content() }
        }
    }
}