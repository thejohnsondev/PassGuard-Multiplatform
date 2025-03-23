package com.thejohnsondev.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.DefaultSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.StringResource
import vaultmultiplatform.core.ui.generated.resources.pass

data class FilterUIModel(
    val id: String,
    val nameResId: StringResource,
    val iconContainer: IconContainer,
    val isSelected: Boolean = false,
    val colors: SelectableItemColors = DefaultSelectableItemColors,
) {
    companion object {

        val testFilterUIModel = FilterUIModel(
            id = "test_filter",
            nameResId = ResString.pass,
            iconContainer = IconContainer(
                imageVector = Icons.Default.FilterList
            ),
            isSelected = true,
        )

        fun FilterUIModel.mapToCategory(): CategoryUIModel {
            return CategoryUIModel(
                id = this.id,
                categoryNameResId = this.nameResId,
                categoryIcon = this.iconContainer,
                colors = this.colors
            )
        }
    }

    override fun toString(): String {
        return "FilterUIModel(id=$id, isSelected=$isSelected)"
    }
}