package com.thejohnsondev.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import com.thejohnsondev.ui.components.loader.Loader
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size48
import com.thejohnsondev.ui.utils.applyIf
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoadedImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    errorDrawableResource: DrawableResource? = null,
    placeholderDrawableResource: DrawableResource? = null,
    placeholderDrawableTintColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = RectangleShape,
    showLoading: Boolean = false
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor
    ) {
        CoilImage(
            modifier = Modifier.applyIf(
                showLoading
            ) {
                blur(
                    radius = Size4,
                    edgeTreatment = BlurredEdgeTreatment.Rectangle
                )
            },
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = contentScale,
                alignment = Alignment.Center
            ),
            previewPlaceholder = placeholderDrawableResource?.let {
                painterResource(it)
            },
            loading = {
                if (showLoading) {
                    Loader(
                        modifier = Modifier
                            .size(Size48)
                            .padding(Size4)
                            .align(Alignment.Center)
                    )
                } else {
                    placeholderDrawableResource?.let {
                        Icon(
                            modifier = Modifier
                                .padding(Size4),
                            painter = painterResource(it),
                            contentDescription = null
                        )
                    }
                }
            },
            failure = {
                errorDrawableResource?.let {
                    Icon(
                        modifier = Modifier
                            .padding(Size4),
                        painter = painterResource(errorDrawableResource),
                        tint = placeholderDrawableTintColor,
                        contentDescription = null
                    )
                }
            }
        )
    }
}