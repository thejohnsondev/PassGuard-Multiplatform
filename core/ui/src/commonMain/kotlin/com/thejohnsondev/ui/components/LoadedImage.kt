package com.thejohnsondev.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.ic_password

@Composable
fun LoadedImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    errorDrawableResource: DrawableResource? = null,
    placeholderDrawableResource: DrawableResource? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = RectangleShape
) {
    Surface(modifier = modifier, shape = shape, color = backgroundColor) {
        CoilImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = contentScale,
                alignment = Alignment.Center
            ),
            previewPlaceholder = placeholderDrawableResource?.let {
                painterResource(Res.drawable.ic_password)
            },
            loading = {
                Loader()
            },
            failure = {
                errorDrawableResource?.let {
                    Icon(painter = painterResource(errorDrawableResource), contentDescription = null)
                }
            }
        )
    }
}