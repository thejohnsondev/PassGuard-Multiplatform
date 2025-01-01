package com.thejohnsondev.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.utils.bounceClick

@Composable
fun RoundedIconButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Size12),
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    iconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    onClick: () -> Unit,
    imageVector: ImageVector
) {
    Surface(
        modifier = modifier
            .bounceClick()
            .clip(shape)
            .clickable {
                onClick()
            },
        color = containerColor,
    ) {
        Icon(
            modifier = Modifier
                .padding(Size8),
            imageVector = imageVector,
            contentDescription = null, // TODO add content description
            tint = iconColor
        )
    }
}