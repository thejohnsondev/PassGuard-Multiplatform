package com.thejohnsondev.landing.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thejohnsondev.ui.components.VaultLogo
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size64
import com.thejohnsondev.ui.designsystem.Size80
import com.thejohnsondev.ui.utils.ResDrawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient

private const val DELAY_BEFORE_ANIM = 200L

private const val BLUR_SCALE_ANIM_START = 20f
private const val BLUR_SCALE_ANIM_END = 0f
private const val BLUR_SCALE_ANIM_DURATION = 600

private const val LOGO_Y_ANIM_START = -100f
private const val LOGO_Y_ANIM_END_PX = 0f
private const val LOGO_Y_ANIM_END_DURATION = 700

private const val LOGO_SCALE_ANIM_START = 0.9f
private const val LOGO_SCALE_ANIM_END = 1f
private const val LOGO_SCALE_ANIM_DURATION = 400

private const val LOGO_ALPHA_ANIM_START = 0f
private const val LOGO_ALPHA_ANIM_END = 1f
private const val LOGO_ALPHA_ANIM_DURATION = 300

@Composable
fun HomeScreen(
    paddingValues: PaddingValues = PaddingValues()
) {
    Surface(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .wrapContentHeight(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Background()
            TitleContent()
        }
    }
}

@Composable
private fun TitleContent() {
    val animatedLogoBlurScale = remember {
        Animatable(BLUR_SCALE_ANIM_START)
    }
    val animatedLogoScale = remember {
        Animatable(LOGO_SCALE_ANIM_START)
    }
    val animatedLogoAlpha = remember {
        Animatable(LOGO_ALPHA_ANIM_START)
    }
    val animatedLogoYPosition = remember {
        Animatable(LOGO_Y_ANIM_START)
    }
    val animatedLogoYPosEnd = 0 - with(LocalDensity.current) {
        LOGO_Y_ANIM_END_PX.dp.toPx()
    }

    LaunchedEffect(true) {
        startAnimations(
            this,
            animatedLogoBlurScale,
            animatedLogoScale,
            animatedLogoAlpha,
            animatedLogoYPosition,
            animatedLogoYPosEnd
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .blur(animatedLogoBlurScale.value.dp)
            .alpha(animatedLogoAlpha.value)
            .graphicsLayer {
                translationY = animatedLogoYPosition.value
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ContentLogo(
            modifier = Modifier

                .padding(top = Size64)
        )
        Text(
            modifier = Modifier
                .padding(top = Size64),
            text = "A simple, secure and easy to use \npassword manager.", // todo anotated string with accent on simple secure and easy
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 42.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            lineHeight = 46.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier
                .padding(top = Size12),
            text = "The only password manager you will ever need.",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1000.dp)
        )
    }
}

@Composable
private fun ContentLogo(
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(start = Size16).size(Size80),
            painter = painterResource(ResDrawable.ic_vault_108_gradient),
            contentDescription = "Logo"
        )
        VaultLogo(
            modifier = Modifier
                .padding(horizontal = Size16)
        )
    }
}

private suspend fun startAnimations(
    coroutineScope: CoroutineScope,
    animatedBackgroundBlurScale: Animatable<Float, AnimationVector1D>,
    animatedLogoScale: Animatable<Float, AnimationVector1D>,
    animatedLogoAlpha: Animatable<Float, AnimationVector1D>,
    animatedLogoYPosition: Animatable<Float, AnimationVector1D>,
    animatedLogoYPosEnd: Float,
) {
    delay(DELAY_BEFORE_ANIM)
    coroutineScope.launch {
        animatedBackgroundBlurScale.animateTo(
            targetValue = BLUR_SCALE_ANIM_END,
            animationSpec = tween(durationMillis = BLUR_SCALE_ANIM_DURATION)
        )
    }
    coroutineScope.launch {
        animatedLogoScale.animateTo(
            targetValue = LOGO_SCALE_ANIM_END,
            animationSpec = tween(durationMillis = LOGO_SCALE_ANIM_DURATION)
        )
    }
    coroutineScope.launch {
        animatedLogoAlpha.animateTo(
            targetValue = LOGO_ALPHA_ANIM_END,
            animationSpec = tween(durationMillis = LOGO_ALPHA_ANIM_DURATION)
        )
    }
    coroutineScope.launch {
        animatedLogoYPosition.animateTo(
            targetValue = animatedLogoYPosEnd,
            animationSpec = tween(durationMillis = LOGO_Y_ANIM_END_DURATION)
        )
    }
}

@Composable
private fun Background() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // TODO: add a blur background
    }
}