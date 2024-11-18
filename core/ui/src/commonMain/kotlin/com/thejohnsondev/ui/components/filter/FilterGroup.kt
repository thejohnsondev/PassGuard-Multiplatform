package com.thejohnsondev.ui.components.filter

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.thejohnsondev.common.TOGGLE_ANIM_DURATION
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size56
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.model.Filter
import com.thejohnsondev.ui.utils.mapToColor
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun FilterGroup(
    modifier: Modifier = Modifier,
    filters: List<Filter>,
    onFilterClick: (Filter, Boolean) -> Unit
) {
    LazyRow(
        modifier = modifier
            .height(Size56),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(filters) { filter ->
            Chip(
                modifier = Modifier
                    .padding(
                        start = if (filters.first() == filter) Size16 else Size8,
                        end = if (filters.last() == filter) Size16 else Size8,
                        bottom = Size8,
                        top = Size8
                    ),
                filter = filter
            ) { isSelected ->
                onFilterClick(filter, isSelected)
            }
        }
    }

}

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    filter: Filter,
    onClick: (Boolean) -> Unit
) {
    val filterTransition = remember {
        MutableTransitionState(filter.isSelected).apply {
            targetState = !filter.isSelected
        }
    }
    val transition = rememberTransition(filterTransition, label = "")
    val filterContainerColor by transition.animateColor({
        tween(durationMillis = TOGGLE_ANIM_DURATION)
    }, label = "") {
        if (filter.isSelected) filter.contentColorResName.mapToColor() else filter.backgroundColorResName.mapToColor()
    }
    val filterContentColor by transition.animateColor({
        tween(durationMillis = TOGGLE_ANIM_DURATION)
    }, label = "") {
        if (filter.isSelected) filter.backgroundColorResName.mapToColor() else filter.contentColorResName.mapToColor()
    }
    val filterChipHorizontalPadding by transition.animateDp({
        tween(durationMillis = TOGGLE_ANIM_DURATION)
    }, label = "") {
        if (filter.isSelected) Size24 else Size16
    }
    val filterChipVerticalPadding by transition.animateDp({
        tween(durationMillis = TOGGLE_ANIM_DURATION)
    }, label = "") {
        if (filter.isSelected) Size12 else Size8
    }

    Row(
        modifier = modifier
        .wrapContentHeight()
        .clip(CircleShape)
            .background(filterContainerColor)
        .clickable {
            onClick(!filter.isSelected)
        },
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val icon = filter.filterIcon?.imageVector
            ?: filter.filterIcon?.imageVectorResId?.let { vectorResource(it) }
        icon?.let {
            Icon(
                modifier = Modifier.padding(
                    start = filterChipHorizontalPadding,
                    end = Size4
                ).size(Size24),
                imageVector = it,
                contentDescription = null,
                tint = filterContentColor
            )
        }
        Text(
            modifier = Modifier
                .padding(
                    top = filterChipVerticalPadding, bottom = filterChipVerticalPadding,
                    start = if (icon != null) {
                        Size4
                    } else {
                        filterChipHorizontalPadding
                    },
                    end = filterChipHorizontalPadding
                ),
            text = stringResource(filter.nameResId),
            color = filterContentColor,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}