package com.thejohnsondev.presentation.component

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import com.thejohnsondev.common.EXPAND_ANIM_DURATION
import com.thejohnsondev.presentation.additem.AddVaultItemViewModel
import com.thejohnsondev.ui.components.ExpandableContent
import com.thejohnsondev.ui.components.RoundedContainer
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.model.CategoryUIModel
import com.thejohnsondev.ui.model.FilterUIModel
import com.thejohnsondev.ui.model.FilterUIModel.Companion.mapToCategory
import com.thejohnsondev.ui.model.getImageVector
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.category

@Composable
fun CategorySelectorItem(
    modifier: Modifier = Modifier,
    state: AddVaultItemViewModel.State,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
) {
    val isExpanded = remember { mutableStateOf(false) }
    val category = state.selectedCategory
    RoundedContainer(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        isTopRounded = true,
        isBottomRounded = true
    ) {
        Column {
            SelectedCategoryContainer(
                isExpanded = isExpanded,
                category = category
            )
            AvailableCategoriesList(
                isExpanded = isExpanded,
                state = state,
                onAction = onAction
            )
        }
    }
}

@Composable
private fun SelectedCategoryContainer(
    isExpanded: MutableState<Boolean>,
    category: CategoryUIModel,
) {
    val transitionState = remember {
        MutableTransitionState(isExpanded.value).apply {
            targetState = !isExpanded.value
        }
    }
    val transition = rememberTransition(transitionState, label = "")
    val arrowRotation by transition.animateFloat({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isExpanded.value) 90f else 0f
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Size16))
            .clickable {
                isExpanded.value = !isExpanded.value
            },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(Size12),
            text = stringResource(ResString.category),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.weight(1f))
        SelectedCategoryChip(category)
        Icon(
            modifier = Modifier
                .rotate(arrowRotation)
                .padding(Size12),
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
        )
    }
}

@Composable
private fun SelectedCategoryChip(category: CategoryUIModel) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(vertical = Size12)
            .clip(RoundedCornerShape(Size8))
            .background(category.colors.getUnselectedContainerColor()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            modifier = Modifier.padding(Size8),
            text = stringResource(category.categoryNameResId),
            style = MaterialTheme.typography.titleMedium,
            color = category.colors.getUnselectedContentColor()
        )
        val icon = category.categoryIcon.getImageVector()
        icon?.let {
            Icon(
                modifier = Modifier
                    .padding(end = Size8)
                    .size(Size24),
                imageVector = icon,
                contentDescription = null,
                tint = category.colors.getUnselectedContentColor()
            )
        }
    }
}

@Composable
private fun AvailableCategoriesList(
    isExpanded: MutableState<Boolean>,
    state: AddVaultItemViewModel.State,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
) {
    ExpandableContent(isExpanded.value) {
        state.itemCategoryFilters.forEach { filter ->
            RoundedContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(Size4),
                isTopRounded = true,
                isBottomRounded = true,
                onClick = {
                    onAction(AddVaultItemViewModel.Action.SelectCategory(filter.mapToCategory()))
                    isExpanded.value = false
                },
                color = if (state.selectedCategory.id == filter.id) filter.colors.getUnselectedContainerColor() else MaterialTheme.colorScheme.surfaceContainerHigh
            ) {
                CategoryOption(filter)
            }
        }
    }
}

@Composable
private fun CategoryOption(filter: FilterUIModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Size12),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val icon = filter.filterIcon.getImageVector()
        icon?.let {
            Icon(
                modifier = Modifier
                    .padding(end = Size8)
                    .size(Size24),
                imageVector = icon,
                contentDescription = null,
                tint = filter.colors.getUnselectedContentColor()
            )
        }
        Text(
            text = stringResource(filter.nameResId),
            style = MaterialTheme.typography.titleMedium,
            color = filter.colors.getUnselectedContentColor()
        )
    }
}