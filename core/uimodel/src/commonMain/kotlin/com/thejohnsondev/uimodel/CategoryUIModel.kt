package com.thejohnsondev.uimodel

import org.jetbrains.compose.resources.StringResource

data class CategoryUIModel(
    val id: String,
    val categoryNameResId: StringResource,
    val categoryIcon: FilterIcon,
    val contentColorResName: String
)

fun FilterUIModel.mapToCategory(): CategoryUIModel {
    return CategoryUIModel(
        id = this.id,
        categoryNameResId = this.nameResId,
        categoryIcon = this.filterIcon,
        contentColorResName = this.contentColorResName
    )
}
