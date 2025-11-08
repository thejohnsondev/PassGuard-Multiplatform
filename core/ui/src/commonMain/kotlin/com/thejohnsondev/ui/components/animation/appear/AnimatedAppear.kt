package com.thejohnsondev.ui.components.animation.appear

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import com.thejohnsondev.ui.utils.applyIf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimatedAppear(
    modifier: Modifier = Modifier,
    params: AnimatedAppearParams = AnimatedAppearParams.default(),
    content: @Composable (animatedModifier: Modifier) -> Unit
) {

    val blur = remember { Animatable(params.blurScaleAnimStart) }
    val alpha = remember { Animatable(params.alphaAnimStart) }
    val translationYValue = remember { Animatable(params.yTranslationAnimStart) }

    val yEndPx = with(LocalDensity.current) { params.yTranslationAnimEndPx.dp.toPx() }

    LaunchedEffect(Unit) {
        delay(params.delayBeforeAnim)

        launch {
            blur.animateTo(
                targetValue = params.blurScaleAnimEnd,
                animationSpec = tween(durationMillis = params.blurScaleAnimDuration)
            )
        }
        launch {
            alpha.animateTo(
                targetValue = params.alphaAnimEnd,
                animationSpec = tween(durationMillis = params.alphaAnimDuration)
            )
        }
        launch {
            translationYValue.animateTo(
                targetValue = -yEndPx,
                animationSpec = tween(durationMillis = params.yTranslationAnimEndDuration)
            )
        }
    }

    val isPreviewMode = LocalInspectionMode.current

    val animatedModifier = modifier
        .applyIf(params.doAnimateBlur) {
            blur(blur.value.dp)
        }
        .applyIf(params.doAnimateAlpha) {
            alpha(alpha.value)
        }
        .applyIf(params.doAnimateTranslationY) {
            graphicsLayer {
                translationY = translationYValue.value
            }
        }

    content(
        if (isPreviewMode) {
            Modifier
        } else {
            animatedModifier
        }
    )
}