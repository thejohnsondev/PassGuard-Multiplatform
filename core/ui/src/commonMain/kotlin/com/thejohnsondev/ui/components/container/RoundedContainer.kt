package com.thejohnsondev.ui.components.container

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.thejohnsondev.ui.designsystem.EquallyRounded
import com.thejohnsondev.ui.utils.applyIf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoundedContainer(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    shape: Shape? = null,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(shape ?: EquallyRounded.medium)
            .applyIf(onClick != null) {
                combinedClickable(
                    onClick = {
                        onClick?.invoke()
                    },
                    onLongClick = onLongClick
                )
            },
        color = color
    ) {
        content()
    }
}