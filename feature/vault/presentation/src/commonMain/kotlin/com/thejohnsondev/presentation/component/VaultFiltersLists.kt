package com.thejohnsondev.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import com.thejohnsondev.ui.utils.COLOR_ID_onPrimaryContainer
import com.thejohnsondev.ui.utils.COLOR_ID_themeColorCategoryFinanceContent
import com.thejohnsondev.ui.utils.COLOR_ID_themeColorCategoryOtherContent
import com.thejohnsondev.ui.utils.COLOR_ID_themeColorCategoryPersonalContent
import com.thejohnsondev.uimodel.Filter
import com.thejohnsondev.uimodel.FilterIcon
import vaultmultiplatform.feature.vault.presentation.generated.resources.Res
import vaultmultiplatform.feature.vault.presentation.generated.resources.bank_accounts
import vaultmultiplatform.feature.vault.presentation.generated.resources.finances
import vaultmultiplatform.feature.vault.presentation.generated.resources.ic_password
import vaultmultiplatform.feature.vault.presentation.generated.resources.notes
import vaultmultiplatform.feature.vault.presentation.generated.resources.other
import vaultmultiplatform.feature.vault.presentation.generated.resources.passwords
import vaultmultiplatform.feature.vault.presentation.generated.resources.personal

fun getVaultItemTypeFilters() = listOf(
    Filter(
        id = "filter_passwords",
        nameResId = Res.string.passwords,
        filterIcon = FilterIcon(
            imageVectorResId = Res.drawable.ic_password
        ),
        contentColorResName = COLOR_ID_onPrimaryContainer,
        isSelected = false
    ),
    Filter(
        id = "filter_notes",
        nameResId = Res.string.notes,
        filterIcon = FilterIcon(
            imageVector = Icons.AutoMirrored.Filled.StickyNote2
        ),
        contentColorResName = COLOR_ID_onPrimaryContainer,
        isSelected = false
    ),
    Filter(
        id = "filter_bank_accounts",
        nameResId = Res.string.bank_accounts,
        filterIcon = FilterIcon(
            imageVector = Icons.Default.CreditCard
        ),
        contentColorResName = "onPrimaryContainer",
        isSelected = false
    ),
)


fun getVaultCategoryFilters() = listOf(
    Filter(
        id = "filter_category_personal",
        nameResId = Res.string.personal,
        filterIcon = FilterIcon(
            imageVector = Icons.Filled.Person
        ),
        contentColorResName = COLOR_ID_themeColorCategoryPersonalContent,
        isSelected = false
    ),
    Filter(
        id = "filter_category_finance",
        nameResId = Res.string.finances,
        filterIcon = FilterIcon(
            imageVector = Icons.Default.MonetizationOn
        ),
        contentColorResName = COLOR_ID_themeColorCategoryFinanceContent,
        isSelected = false
    ),
    Filter(
        id = "filter_bank_other",
        nameResId = Res.string.other,
        filterIcon = FilterIcon(
            imageVector = Icons.Default.MoreHoriz
        ),
        contentColorResName = COLOR_ID_themeColorCategoryOtherContent,
        isSelected = false
    ),
)