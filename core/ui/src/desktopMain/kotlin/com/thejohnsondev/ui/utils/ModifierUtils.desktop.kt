package com.thejohnsondev.ui.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import com.thejohnsondev.common.empty
import com.thejohnsondev.ui.designsystem.Percent100

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.cursorEnterAnimation(): Modifier = composed {
    var entered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        if (entered) 0.99f else Percent100, label = String.empty
    )
    this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }.onPointerEvent(PointerEventType.Enter) {
        entered = true
    }.onPointerEvent(PointerEventType.Exit) {
        entered = false
    }
}