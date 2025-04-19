package com.thejohnsondev.ui.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thejohnsondev.common.empty
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Percent95
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
    this.border(SizeBorder, listOf(Color.Red, Color.Red, Color.Green, Color.Magenta).random())
}

fun Modifier.onEnterClick(onClick: () -> Unit) = composed {
    onKeyEvent {
        if (it.key == Key.Enter) {
            onClick()
            true
        } else {
            false
        }
    }
}

@Stable
fun Modifier.padding(
    allAround: Dp? = null,
    horizontal: Dp? = null,
    vertical: Dp? = null,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
) = allAround?.let {
    this.padding(all = allAround)
} ?: this.padding(
    start = horizontal ?: start,
    top = vertical ?: top,
    end = horizontal ?: end,
    bottom = vertical ?: bottom,
)