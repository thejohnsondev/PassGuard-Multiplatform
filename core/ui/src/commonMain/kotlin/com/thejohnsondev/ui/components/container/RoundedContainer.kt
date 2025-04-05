package com.thejohnsondev.ui.components.container

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.utils.applyIf

@Composable
fun RoundedContainer(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    isTopRounded: Boolean = false,
    isBottomRounded: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = if (isTopRounded) Size16 else Size4,
                    topEnd = if (isTopRounded) Size16 else Size4,
                    bottomStart = if (isBottomRounded) Size16 else Size4,
                    bottomEnd = if (isBottomRounded) Size16 else Size4
                )
            )
            .applyIf(onClick != null) {
                clickable { onClick?.invoke() }
            },
        color = color
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            content(this)
        }
    }
}