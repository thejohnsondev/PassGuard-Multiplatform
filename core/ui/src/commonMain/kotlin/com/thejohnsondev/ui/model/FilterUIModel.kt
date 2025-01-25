package com.thejohnsondev.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.DefaultSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.pass

data class FilterUIModel(
    val id: String,
    val nameResId: StringResource,
    val filterIcon: FilterIcon,
    val isSelected: Boolean = false,
    val colors: SelectableItemColors = DefaultSelectableItemColors
) {
    companion object {

        val testFilterUIModel = FilterUIModel(
            id = "test_filter",
            nameResId = Res.string.pass,
            filterIcon = FilterIcon(
                imageVector = Icons.Default.FilterList
            ),
            isSelected = true,
        )

        fun FilterUIModel.mapToCategory(): CategoryUIModel {
            return CategoryUIModel(
                id = this.id,
                categoryNameResId = this.nameResId,
                categoryIcon = this.filterIcon,
                colors = this.colors
            )
        }
    }

    override fun toString(): String {
        return "FilterUIModel(id=$id, isSelected=$isSelected)"
    }
}

data class FilterIcon(
    val imageVector: ImageVector? = null,
    val imageVectorResId: DrawableResource? = null
)

@Composable
fun FilterIcon.getImageVector(): ImageVector? {
    return imageVector ?: imageVectorResId?.let { vectorResource(it) }
}