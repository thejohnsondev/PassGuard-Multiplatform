package com.thejohnsondev.presentation.welcome

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.components.BlurContainer
import com.thejohnsondev.ui.components.RoundedButton
import com.thejohnsondev.ui.designsystem.Percent80
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size580
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.getGlobalFontFamily
import com.thejohnsondev.ui.designsystem.isLight
import com.thejohnsondev.ui.model.ButtonStyle
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.feature.auth.presentation.generated.resources.Res
import vaultmultiplatform.feature.auth.presentation.generated.resources.app_name
import vaultmultiplatform.feature.auth.presentation.generated.resources.ic_vault_108_gradient
import vaultmultiplatform.feature.auth.presentation.generated.resources.log_in
import vaultmultiplatform.feature.auth.presentation.generated.resources.sign_up
import vaultmultiplatform.feature.auth.presentation.generated.resources.your_fortress

private const val SCALE_ANIM_START = 0f
private const val SCALE_ANIM_END = 1f
private const val SCALE_ANIM_DURATION = 1200
private const val SCALE_ANIM_DELAY = 500L

@Composable
fun WelcomeScreen(
    windowSize: WindowWidthSizeClass,
    goToSignUp: () -> Unit,
    goToLogin: () -> Unit
) {

    WelcomeContent(
        windowSize = windowSize,
        goToSignUp = goToSignUp,
        goToLogin = goToLogin
    )
}

@Composable
fun WelcomeContent(
    windowSize: WindowWidthSizeClass,
    goToSignUp: () -> Unit,
    goToLogin: () -> Unit
) {
    val animatesScale = remember {
        Animatable(SCALE_ANIM_START)
    }

    LaunchedEffect(true) {
        delay(SCALE_ANIM_DELAY)
        animatesScale.animateTo(
            targetValue = SCALE_ANIM_END,
            animationSpec = tween(durationMillis = SCALE_ANIM_DURATION)
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = if (MaterialTheme.colorScheme.isLight()) {
            Color.White
        } else {
            Color.Black
        }
    ) {
        Box {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                BlurContainer(
                    modifier = Modifier
                        .fillMaxSize(),
                    component = {
                        Image(
                            modifier = Modifier.fillMaxSize()
                                .scale(animatesScale.value),
                            imageVector = vectorResource(Res.drawable.ic_vault_108_gradient),
                            contentDescription = null // TODO add content description
                        )
                    },
                    blur = 150f
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.wrapContentSize(),
                    imageVector = vectorResource(Res.drawable.ic_vault_108_gradient),
                    contentDescription = null // TODO add content description
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = Size8, horizontal = Size16),
                    text = stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.displayMedium,
                    fontFamily = getGlobalFontFamily()
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = Size8, horizontal = Size16),
                    text = stringResource(Res.string.your_fortress),
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = getGlobalFontFamily(),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = Percent80)
                )
            }
            Column(
                modifier = Modifier
                    .applyIf(windowSize.isCompact()) {
                        Modifier.fillMaxWidth()
                    }
                    .applyIf(!windowSize.isCompact()) {
                        Modifier.width(Size580)
                    }
                    .wrapContentHeight()
                    .padding(Size16)
                    .align(Alignment.BottomCenter),
            ) {
                RoundedButton(
                    text = stringResource(Res.string.log_in),
                    modifier = Modifier.padding(bottom = Size8, start = Size16, end = Size16),
                    onClick = {
                        goToLogin()
                    }
                )
                RoundedButton(
                    text = stringResource(Res.string.sign_up),
                    modifier = Modifier.padding(
                        top = Size8,
                        bottom = Size16,
                        start = Size16,
                        end = Size16
                    ),
                    onClick = {
                        goToSignUp()
                    },
                    buttonStyle = ButtonStyle.OUTLINE
                )
            }
        }
    }
}