package com.thejohnsondev.ui.components.loader

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.thejohnsondev.common.DEFAULT_ANIM_DURATION
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.utils.mapToStrengthLevelColor

@Composable
fun StrengthLevelIndicator(
    modifier: Modifier = Modifier,
    level: Float,
) {
    var oldLevelState by remember { mutableIntStateOf(level.normalizeToInt()) }
    val newLevel = level.normalizeToInt()
    SideEffect {
        oldLevelState = level.normalizeToInt()
    }

    val animatedLevelState by animateFloatAsState(
        targetValue = level,
        animationSpec = tween(durationMillis = DEFAULT_ANIM_DURATION),
        label = "Strength Level Animation"
    )

    val animatedColor by animateColorAsState(
        targetValue = level.mapToStrengthLevelColor(),
        animationSpec = tween(durationMillis = DEFAULT_ANIM_DURATION),
        label = "Strength Level Color Animation"
    )

    Box(
        modifier = modifier
            .size(Size32),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { animatedLevelState },
            color = animatedColor
        )
        AnimatedContent(
            targetState = level,
            transitionSpec = {
                if (oldLevelState > newLevel) {
                    slideInVertically { -it } togetherWith slideOutVertically { it }
                } else {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                }
            }
        ) {
            Text(
                text = newLevel.toString(),
                color = animatedColor,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

}

private fun Float.normalizeToInt(): Int {
    return (this * 10).toInt()
}