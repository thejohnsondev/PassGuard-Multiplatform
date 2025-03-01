package com.thejohnsondev.presentation.component

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.thejohnsondev.common.EXPAND_ANIM_DURATION
import com.thejohnsondev.ui.designsystem.Percent30
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.SizeDefault
import com.thejohnsondev.ui.designsystem.SizeMinus
import kotlinx.coroutines.delay

@Composable
fun HighlightOnLongPressText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: () -> Unit,
    onLongClick: (String) -> Unit,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    var showBorder by remember { mutableStateOf(false) }

    LaunchedEffect(showBorder) {
        if (showBorder) {
            delay(EXPAND_ANIM_DURATION.toLong())
            showBorder = false
        }
    }

    val borderTransitionState = remember {
        MutableTransitionState(!showBorder).apply {
            targetState = showBorder
        }
    }
    val borderTransition = rememberTransition(borderTransitionState, label = "")
    val borderWidth by borderTransition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (showBorder) Size2 else SizeMinus
    }
    val borderPadding by borderTransition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (showBorder) Size2 else SizeDefault
    }
    val overlayAlpha by borderTransition.animateFloat({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (showBorder) Percent30 else 0f
    }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showBorder = true
                        onLongClick(text)
                    },
                    onTap = {
                        onClick()
                    }
                )
            }
            .border(
                width = borderWidth,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(Size8)
            )
            .padding(borderPadding)
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = style,
            fontWeight = fontWeight,
            color = color,
            overflow = overflow,
            maxLines = maxLines
        )

        if (showBorder) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(Size8))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = overlayAlpha))
            )
        }
    }
}