package com.thejohnsondev.ui.model

import org.jetbrains.compose.resources.StringResource

data class CategoryUIModel(
    val id: String,
    val categoryNameResId: StringResource,
    val categoryIcon: FilterIcon,
    val contentColorResName: String
)

