package com.thejohnsondev.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.thejohnsondev.common.TOGGLE_ANIM_DURATION
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.colorscheme.MaterialSelectableItemColors

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp = Size16,
    isSelected: Boolean = false,
    showAccentDot: Boolean = false,
    onToggleClick: () -> Unit
) {
    val toggleButtonTransition = remember {
        MutableTransitionState(isSelected).apply {
            targetState = !isSelected
        }
    }
    val transition = rememberTransition(toggleButtonTransition, label = "")
    val toggleButtonContainerColor by transition.animateColor({
        tween(durationMillis = TOGGLE_ANIM_DURATION)
    }, label = "") {
        if (isSelected) MaterialSelectableItemColors.getSelectedContainerColor() else MaterialSelectableItemColors.getUnselectedContainerColor()
    }
    val toggleButtonContentColor by transition.animateColor({
        tween(durationMillis = TOGGLE_ANIM_DURATION)
    }, label = "") {
        if (isSelected) MaterialSelectableItemColors.getSelectedContentColor() else MaterialSelectableItemColors.getUnselectedContentColor()
    }

    Box {
        Surface(
            modifier = modifier
                .clickable {
                    onToggleClick()
                },
            color = toggleButtonContainerColor,
        ) {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .padding(Size16),
                imageVector = icon,
                contentDescription = null,
                tint = toggleButtonContentColor
            )
        }

        if (showAccentDot) {
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = Size4, end = Size4),
                visible = !isSelected
            ) {
                Surface(
                    modifier = Modifier

                        .size(Size8),
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(Size8)
                ) { }
            }
        }
    }

}