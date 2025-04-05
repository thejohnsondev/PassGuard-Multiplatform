package com.thejohnsondev.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.thejohnsondev.common.EXPAND_ANIM_DURATION
import com.thejohnsondev.ui.components.container.ExpandableContent
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size36
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String?,
    icon: ImageVector,
    isFirstItem: Boolean = false,
    isLastItem: Boolean = false,
    content: @Composable () -> Unit = {}
) {
    val haptic = LocalHapticFeedback.current
    var expanded by remember {
        mutableStateOf(false)
    }
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = rememberTransition(transitionState, label = "")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (expanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
    }
    val iconBgColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (expanded) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSecondaryContainer
    }
    val iconBgCornerSize by transition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (expanded) Size36 else Size8
    }
    val iconColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (expanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
    }
    val iconPadding by transition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (expanded) Size16 else Size8
    }
    val contentColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (expanded) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSecondaryContainer
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = Size16, end = Size16, bottom = Size4),
        shape = RoundedCornerShape(
            topStart = if (isFirstItem) Size16 else Size4,
            topEnd = if (isFirstItem) Size16 else Size4,
            bottomStart = if (isLastItem) Size16 else Size4,
            bottomEnd = if (isLastItem) Size16 else Size4
        ),
        colors = CardDefaults.cardColors(
            containerColor = cardBgColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = !expanded
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    },
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(Size16)
                        .clip(RoundedCornerShape(iconBgCornerSize)),
                    color = iconBgColor
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(iconPadding)
                            .size(Size24),
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconColor
                    )
                }
                Spacer(modifier = Modifier.width(Size8))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = contentColor
                    )

                    if (description != null) {
                        Spacer(modifier = Modifier.height(Size4))
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = contentColor
                        )
                    }

                }
            }
            ExpandableContent(
                visible = expanded,
                content = {
                    content()
                }
            )
        }

    }

}