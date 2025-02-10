package com.thejohnsondev.ui.model

import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors
import org.jetbrains.compose.resources.StringResource

data class CategoryUIModel(
    val id: String,
    val categoryNameResId: StringResource,
    val categoryIcon: IconContainer,
    val colors: SelectableItemColors
)

