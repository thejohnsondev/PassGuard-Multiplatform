package com.thejohnsondev.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CountryFlagItem(
    modifier: Modifier = Modifier,
    flagDrawableResource: DrawableResource,
) {
    Surface(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(flagDrawableResource),
            contentDescription = "Language selection",
            contentScale = ContentScale.Crop
        )
    }
}