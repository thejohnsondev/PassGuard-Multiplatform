package com.thejohnsondev.presentation.welcome

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.thejohnsondev.ui.components.BlurContainer
import com.thejohnsondev.ui.components.RoundedButton
import com.thejohnsondev.ui.designsystem.Percent80
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size580
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.designsystem.getGlobalFontFamily
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.core.ui.generated.resources.app_name
import vaultmultiplatform.core.ui.generated.resources.get_started
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient
import vaultmultiplatform.core.ui.generated.resources.your_fortress

private const val BLUR_SCALE_ANIM_START = 0f
private const val BLUR_SCALE_ANIM_END = 1f
private const val BLUR_SCALE_ANIM_DURATION = 1200
private const val BLUR_SCALE_ANIM_DELAY = 500L

private const val LOGO_Y_ANIM_START = 0f
private const val LOGO_Y_ANIM_END_PX = 100f
private const val LOGO_SCALE_ANIM_START = 1.4f
private const val LOGO_SCALE_ANIM_END = 1f

private const val CONTENT_ALPHA_ANIM_DELAY = 200L
private const val CONTENT_ALPHA_ANIM_START = 0f
private const val CONTENT_ALPHA_ANIM_END = 1f

@Composable
fun WelcomeScreen(
    windowSize: WindowWidthSizeClass,
    goToSelectVaultType: () -> Unit
) {
    WelcomeContent(
        windowSize = windowSize,
        goToSelectVaultType = goToSelectVaultType
    )
}

@Composable
fun WelcomeContent(
    windowSize: WindowWidthSizeClass,
    goToSelectVaultType: () -> Unit
) {
    val animatedBackgroundBlurScale = remember {
        Animatable(BLUR_SCALE_ANIM_START)
    }
    val animatedLogoYPosition = remember {
        Animatable(LOGO_Y_ANIM_START)
    }
    val animatedLogoScale = remember {
        Animatable(LOGO_SCALE_ANIM_START)
    }
    val animatedContentAlpha = remember {
        Animatable(CONTENT_ALPHA_ANIM_START)
    }
    val animatedLogoYPosEnd = 0 - with(LocalDensity.current) {
        LOGO_Y_ANIM_END_PX.dp.toPx()
    }

    LaunchedEffect(true) {
        startAnimations(
            this,
            animatedBackgroundBlurScale,
            animatedLogoYPosition,
            animatedLogoYPosEnd,
            animatedLogoScale,
            animatedContentAlpha
        )
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (MaterialTheme.colorScheme.isLight()) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )
        ) {
            LogoSection(
                modifier = Modifier
                    .align(Alignment.Center),
                animatedBackgroundBlurScale = animatedBackgroundBlurScale,
                animatedLogoScale = animatedLogoScale,
                animatedLogoYPosition = animatedLogoYPosition
            )
            Titles(animatedContentAlpha)
            ButtonsSection(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                paddingValues = paddingValues,
                windowSize = windowSize,
                animatedContentAlpha = animatedContentAlpha,
                goToSelectVaultType = goToSelectVaultType
            )
        }
    }
}

@Composable
private fun ButtonsSection(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    windowSize: WindowWidthSizeClass,
    animatedContentAlpha: Animatable<Float, AnimationVector1D>,
    goToSelectVaultType: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
            .applyIf(windowSize.isCompact()) {
                Modifier.fillMaxWidth()
            }
            .applyIf(!windowSize.isCompact()) {
                Modifier.width(Size580)
            }
            .wrapContentHeight()
            .padding(Size16),
    ) {
        RoundedButton(
            modifier = Modifier
                .padding(
                    top = Size8,
                    bottom = Size16,
                    start = Size16,
                    end = Size16
                ).alpha(animatedContentAlpha.value),
            text = stringResource(ResString.get_started),
            onClick = {
                goToSelectVaultType()
            }
        )
    }
}

@Composable
private fun Titles(
    animatedContentAlpha: Animatable<Float, AnimationVector1D>,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = Size8, horizontal = Size16)
                .alpha(animatedContentAlpha.value),
            text = stringResource(ResString.app_name),
            style = MaterialTheme.typography.displaySmall,
            fontFamily = getGlobalFontFamily()
        )
        Text(
            modifier = Modifier
                .padding(vertical = Size8, horizontal = Size16)
                .alpha(animatedContentAlpha.value),
            text = stringResource(ResString.your_fortress),
            style = MaterialTheme.typography.titleMedium,
            fontFamily = getGlobalFontFamily(),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = Percent80)
        )
    }
}

@Composable
private fun LogoSection(
    modifier: Modifier = Modifier,
    animatedBackgroundBlurScale: Animatable<Float, AnimationVector1D>,
    animatedLogoScale: Animatable<Float, AnimationVector1D>,
    animatedLogoYPosition: Animatable<Float, AnimationVector1D>,
) {
    Box(
        modifier = modifier
    ) {
        BlurContainer(
            modifier = Modifier
                .fillMaxSize(),
            component = {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(animatedBackgroundBlurScale.value),
                    imageVector = vectorResource(ResDrawable.ic_vault_108_gradient),
                    contentDescription = null // TODO add content description
                )
            },
            blur = 150f
        )
        Image(
            modifier = Modifier
                .wrapContentSize()
                .scale(animatedLogoScale.value)
                .graphicsLayer {
                    translationY = animatedLogoYPosition.value
                }
                .align(Alignment.Center),
            imageVector = vectorResource(ResDrawable.ic_vault_108_gradient),
            contentDescription = null // TODO add content description,
        )
    }
}

private suspend fun startAnimations(
    coroutineScope: CoroutineScope,
    animatedBackgroundBlurScale: Animatable<Float, AnimationVector1D>,
    animatedLogoYPosition: Animatable<Float, AnimationVector1D>,
    animatedLogoYPosEnd: Float,
    animatedLogoScale: Animatable<Float, AnimationVector1D>,
    animatedContentAlpha: Animatable<Float, AnimationVector1D>,
) {
    delay(BLUR_SCALE_ANIM_DELAY)
    coroutineScope.launch {
        animatedBackgroundBlurScale.animateTo(
            targetValue = BLUR_SCALE_ANIM_END,
            animationSpec = tween(durationMillis = BLUR_SCALE_ANIM_DURATION)
        )
    }
    coroutineScope.launch {
        animatedLogoYPosition.animateTo(
            targetValue = animatedLogoYPosEnd,
            animationSpec = tween(durationMillis = BLUR_SCALE_ANIM_DURATION)
        )
    }
    coroutineScope.launch {
        animatedLogoScale.animateTo(
            targetValue = LOGO_SCALE_ANIM_END,
            animationSpec = tween(durationMillis = BLUR_SCALE_ANIM_DURATION)
        )
    }
    delay(CONTENT_ALPHA_ANIM_DELAY)
    coroutineScope.launch {
        animatedContentAlpha.animateTo(
            targetValue = CONTENT_ALPHA_ANIM_END,
            animationSpec = tween(durationMillis = BLUR_SCALE_ANIM_DURATION)
        )
    }
}