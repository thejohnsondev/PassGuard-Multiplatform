package com.thejohnsondev.ui.components.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import com.thejohnsondev.ui.designsystem.isBlurSupported

@Composable
fun BlurContainer(
    modifier: Modifier,
    blur: Float,
    component: @Composable BoxScope.() -> Unit,
    content: (@Composable BoxScope.() -> Unit)? = null
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        if (isBlurSupported()) {
            Box(
                modifier = Modifier
                    .blur(
                        radius = blur.dp,
                        edgeTreatment = BlurredEdgeTreatment.Rectangle
                    ),
                content = component,
            )
        }
        Box(
            contentAlignment = Alignment.Center
        ) {
            content?.invoke(this)
        }
    }
}