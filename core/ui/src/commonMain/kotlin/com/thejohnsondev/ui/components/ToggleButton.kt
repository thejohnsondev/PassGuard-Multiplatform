package com.thejohnsondev.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.thejohnsondev.common.TOGGLE_ANIM_DURATION
import com.thejohnsondev.ui.designsystem.Size16

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp = Size16,
    isSelected: Boolean = false,
    onToggleClick: () -> Unit
) {
    val filterTransition = remember {
        MutableTransitionState(isSelected).apply {
            targetState = !isSelected
        }
    }
    val transition = rememberTransition(filterTransition, label = "")
    val filterContainerColor by transition.animateColor({
        tween(durationMillis = TOGGLE_ANIM_DURATION)
    }, label = "") {
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerLow
    }
    val filterContentColor by transition.animateColor({
        tween(durationMillis = TOGGLE_ANIM_DURATION)
    }, label = "") {
        if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = modifier
            .clickable {
                onToggleClick() },
        color = filterContainerColor,) {
        Icon(
            modifier = Modifier
                .size(iconSize)
                .padding(Size16),
            imageVector = icon,
            contentDescription = null,
            tint = filterContentColor
        )
    }

}