package com.thejohnsondev.landing.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.landing.utils.windowHeight
import com.thejohnsondev.ui.components.VaultLogo
import com.thejohnsondev.ui.components.animation.appear.AnimatedAppear
import com.thejohnsondev.ui.components.animation.appear.AnimatedAppearParams
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size128
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size64
import com.thejohnsondev.ui.designsystem.Size80
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.padding
import org.jetbrains.compose.resources.painterResource
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient

@Composable
fun HomeScreen(
    paddingValues: PaddingValues = PaddingValues(), scrollProgress: Int = 0
) {

    LaunchedEffect(scrollProgress) {
        Logger.e("Home Screen Scroll Progress: $scrollProgress")
    }

    var phoneBoxCoordinates by remember {
        mutableStateOf<LayoutCoordinates?>(null)
    }

    val density = LocalDensity.current
    val windowHeightPx = with(density) { windowHeight.dp.toPx() }
    val middleOfWindow = windowHeightPx / 2f

    val centerY = phoneBoxCoordinates?.positionInWindow()?.y?.plus(
        phoneBoxCoordinates?.size?.height?.div(2f) ?: 0f
    ) ?: 0f

    val tolerancePx = 200

    val isPhoneBoxInCenter = centerY <= middleOfWindow && centerY >= middleOfWindow - tolerancePx

    Surface(
        modifier = Modifier.padding(paddingValues).fillMaxWidth().wrapContentHeight(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                modifier = Modifier.height(Size2).fillMaxWidth().graphicsLayer {
                    translationY = centerY
                }, color = Color.Red
            )
            HorizontalDivider(
                modifier = Modifier.height(Size2).fillMaxWidth().graphicsLayer {
                    translationY = middleOfWindow
                }, color = Color.Green
            )
            Background()
            Content()
            PhoneBoxContent(
                modifier = Modifier
                    .padding(top = Size64)

            )
        }
    }
}

@Composable
private fun Content() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AnimatedAppear { animatedModifier ->
            ContentLogo(
                modifier = animatedModifier.padding(top = Size64)
            )
        }
        AnimatedAppear(
            params = AnimatedAppearParams.default(
                delayBeforeAnim = 400L
            )
        ) { animatedModifier ->
            Column(
                modifier = animatedModifier.padding(top = Size64),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "A simple, secure and easy to use \npassword manager.", // todo anotated string with accent on simple secure and easy
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 42.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    lineHeight = 46.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier.padding(top = Size12),
                    text = "The only password manager you will ever need.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth().height(1000.dp)
        )
    }
}

@Composable
private fun PhoneBoxContent(
    modifier: Modifier = Modifier,
) {
    AnimatedAppear(
        modifier = Modifier
//            .applyIf(translationYValue != 0) {
//                graphicsLayer {
//                    translationY = translationYValue.toFloat()
//                }
//            }
        ,
        params = AnimatedAppearParams.default(
            delayBeforeAnim = 600L,
            doAnimateTranslationY = false,
        )
    ) { animatedModifier ->
        Box(
            modifier = animatedModifier.padding(top = Size128, horizontal = Size16)
                .size(200.dp, 400.dp).background(Color.Black, RoundedCornerShape(32.dp))
        )
    }
}

@Composable
private fun ContentLogo(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(start = Size16).size(Size80),
            painter = painterResource(ResDrawable.ic_vault_108_gradient),
            contentDescription = "Logo"
        )
        VaultLogo(
            modifier = Modifier.padding(horizontal = Size16)
        )
    }
}

@Composable
private fun Background() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        // TODO: add a blur background
    }
}