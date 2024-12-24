package com.thejohnsondev.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_FINANCE
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_PERSONAL
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_WORK
import com.thejohnsondev.ui.model.filterlists.financeFilterUIModel
import com.thejohnsondev.ui.model.filterlists.othersFilterUIModel
import com.thejohnsondev.ui.model.filterlists.personalFilterUIModel
import com.thejohnsondev.ui.model.filterlists.workFilterUIModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.pass

data class FilterUIModel(
    val id: String,
    val nameResId: StringResource,
    val filterIcon: FilterIcon,
    val contentColorResName: String,
    val isSelected: Boolean = false
) {
    companion object {

        val testFilterUIModel = FilterUIModel(
            id = "test_filter",
            nameResId = Res.string.pass,
            filterIcon = FilterIcon(
                imageVector = Icons.Default.FilterList
            ),
            contentColorResName = "onPrimaryContainer",
            isSelected = true
        )

        fun getFilterUiModelById(id: String): FilterUIModel {
            return when (id) {
                VAULT_ITEM_CATEGORY_PERSONAL -> personalFilterUIModel
                VAULT_ITEM_CATEGORY_WORK -> workFilterUIModel
                VAULT_ITEM_CATEGORY_FINANCE -> financeFilterUIModel
                else -> othersFilterUIModel
            }
        }
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