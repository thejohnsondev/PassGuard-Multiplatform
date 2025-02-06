package com.thejohnsondev.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

data class IconContainer(
    val imageVector: ImageVector? = null,
    val imageVectorResId: DrawableResource? = null,
)

@Composable
fun IconContainer.getImageVector(): ImageVector {
    return imageVector ?: imageVectorResId?.let { vectorResource(it) } ?: throw IllegalStateException("No icon provided")
}