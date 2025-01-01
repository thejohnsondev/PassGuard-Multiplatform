package com.thejohnsondev.ui.model.mappers

import com.thejohnsondev.ui.model.CategoryUIModel
import com.thejohnsondev.ui.model.FilterUIModel

fun FilterUIModel.mapToCategory(): CategoryUIModel {
    return CategoryUIModel(
        id = this.id,
        categoryNameResId = this.nameResId,
        categoryIcon = this.filterIcon,
        colors = this.colors
    )
}