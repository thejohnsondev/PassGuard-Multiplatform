package com.thejohnsondev.ui.model.filterlists

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.CreditCard
import com.thejohnsondev.common.VAULT_ITEM_TYPE_BANK_ACCOUNTS
import com.thejohnsondev.common.VAULT_ITEM_TYPE_NOTES
import com.thejohnsondev.common.VAULT_ITEM_TYPE_PASSWORDS
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.DefaultSelectableItemColors
import com.thejohnsondev.ui.model.FilterIcon
import com.thejohnsondev.ui.model.FilterUIModel
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.bank_accounts
import vaultmultiplatform.core.ui.generated.resources.ic_password
import vaultmultiplatform.core.ui.generated.resources.notes
import vaultmultiplatform.core.ui.generated.resources.passwords

val passwordsFilterUIModel = FilterUIModel(
    id = VAULT_ITEM_TYPE_PASSWORDS,
    nameResId = Res.string.passwords,
    filterIcon = FilterIcon(
        imageVectorResId = Res.drawable.ic_password
    ),
    colors = DefaultSelectableItemColors,
    isSelected = false
)

val notesFilterUIModel = FilterUIModel(
    id = VAULT_ITEM_TYPE_NOTES,
    nameResId = Res.string.notes,
    filterIcon = FilterIcon(
        imageVector = Icons.AutoMirrored.Filled.StickyNote2
    ),
    colors = DefaultSelectableItemColors,
    isSelected = false
)

val bankAccountsFilterUIModel = FilterUIModel(
    id = VAULT_ITEM_TYPE_BANK_ACCOUNTS,
    nameResId = Res.string.bank_accounts,
    filterIcon = FilterIcon(
        imageVector = Icons.Default.CreditCard
    ),
    colors = DefaultSelectableItemColors,
    isSelected = false
)

fun getVaultItemTypeFilters() = listOf(
    passwordsFilterUIModel,
    notesFilterUIModel,
    bankAccountsFilterUIModel
)