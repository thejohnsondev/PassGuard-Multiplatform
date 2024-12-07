package com.thejohnsondev.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size4

@Composable
fun RoundedContainer(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    isFirstItem: Boolean = false,
    isLastItem: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(
            topStart = if (isFirstItem) Size16 else Size4,
            topEnd = if (isFirstItem) Size16 else Size4,
            bottomStart = if (isLastItem) Size16 else Size4,
            bottomEnd = if (isLastItem) Size16 else Size4
        ),
        color = color
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            content(this)
        }
    }
}