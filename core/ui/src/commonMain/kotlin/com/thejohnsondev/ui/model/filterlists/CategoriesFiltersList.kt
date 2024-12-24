package com.thejohnsondev.ui.model.filterlists

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import com.thejohnsondev.common.COLOR_ID_themeColorCategoryFinanceContent
import com.thejohnsondev.common.COLOR_ID_themeColorCategoryOtherContent
import com.thejohnsondev.common.COLOR_ID_themeColorCategoryPersonalContent
import com.thejohnsondev.common.COLOR_ID_themeColorCategoryWorkContent
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_FINANCE
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_OTHERS
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_PERSONAL
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_WORK
import com.thejohnsondev.ui.model.FilterIcon
import com.thejohnsondev.ui.model.FilterUIModel
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.finances
import vaultmultiplatform.core.ui.generated.resources.other
import vaultmultiplatform.core.ui.generated.resources.personal
import vaultmultiplatform.core.ui.generated.resources.work

val personalFilterUIModel = FilterUIModel(
    id = VAULT_ITEM_CATEGORY_PERSONAL,
    nameResId = Res.string.personal,
    filterIcon = FilterIcon(
        imageVector = Icons.Filled.Person
    ),
    contentColorResName = COLOR_ID_themeColorCategoryPersonalContent,
    isSelected = false
)

val workFilterUIModel = FilterUIModel(
    id = VAULT_ITEM_CATEGORY_WORK,
    nameResId = Res.string.work,
    filterIcon = FilterIcon(
        imageVector = Icons.Default.Work
    ),
    contentColorResName = COLOR_ID_themeColorCategoryWorkContent,
    isSelected = false
)

val financeFilterUIModel = FilterUIModel(
    id = VAULT_ITEM_CATEGORY_FINANCE,
    nameResId = Res.string.finances,
    filterIcon = FilterIcon(
        imageVector = Icons.Default.MonetizationOn
    ),
    contentColorResName = COLOR_ID_themeColorCategoryFinanceContent,
    isSelected = false
)

val othersFilterUIModel = FilterUIModel(
    id = VAULT_ITEM_CATEGORY_OTHERS,
    nameResId = Res.string.other,
    filterIcon = FilterIcon(
        imageVector = Icons.Default.MoreHoriz
    ),
    contentColorResName = COLOR_ID_themeColorCategoryOtherContent,
    isSelected = false
)

fun getVaultCategoryFilters() = listOf(
    personalFilterUIModel,
    workFilterUIModel,
    financeFilterUIModel,
    othersFilterUIModel
)