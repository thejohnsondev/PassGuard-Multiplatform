package com.thejohnsondev.uimodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import vaultmultiplatform.core.uimodel.generated.resources.Res
import vaultmultiplatform.core.uimodel.generated.resources.pass

data class Filter(
    val id: String,
    val nameResId: StringResource,
    val filterIcon: FilterIcon?,
    val contentColorResName: String,
    val isSelected: Boolean = false
) {
    companion object {
        const val FILTER_ALL = "filter_all"

        val testFilter = Filter(
            id = "test_filter",
            nameResId = Res.string.pass,
            filterIcon = FilterIcon(
                imageVector = Icons.Default.FilterList
            ),
            contentColorResName = "onPrimaryContainer",
            isSelected = true
        )
    }
}

data class FilterIcon(
    val imageVector: ImageVector? = null,
    val imageVectorResId: DrawableResource? = null
)