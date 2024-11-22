package com.thejohnsondev.uimodel.filterlists

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.CreditCard
import com.thejohnsondev.common.COLOR_ID_onPrimaryContainer
import com.thejohnsondev.common.VAULT_ITEM_TYPE_BANK_ACCOUNTS
import com.thejohnsondev.common.VAULT_ITEM_TYPE_NOTES
import com.thejohnsondev.common.VAULT_ITEM_TYPE_PASSWORDS
import com.thejohnsondev.uimodel.FilterUIModel
import com.thejohnsondev.uimodel.FilterIcon
import vaultmultiplatform.core.uimodel.generated.resources.Res
import vaultmultiplatform.core.uimodel.generated.resources.bank_accounts
import vaultmultiplatform.core.uimodel.generated.resources.ic_password
import vaultmultiplatform.core.uimodel.generated.resources.notes
import vaultmultiplatform.core.uimodel.generated.resources.passwords

val passwordsFilterUIModel = FilterUIModel(
    id = VAULT_ITEM_TYPE_PASSWORDS,
    nameResId = Res.string.passwords,
    filterIcon = FilterIcon(
        imageVectorResId = Res.drawable.ic_password
    ),
    contentColorResName = COLOR_ID_onPrimaryContainer,
    isSelected = false
)

val notesFilterUIModel = FilterUIModel(
    id = VAULT_ITEM_TYPE_NOTES,
    nameResId = Res.string.notes,
    filterIcon = FilterIcon(
        imageVector = Icons.AutoMirrored.Filled.StickyNote2
    ),
    contentColorResName = COLOR_ID_onPrimaryContainer,
    isSelected = false
)

val bankAccountsFilterUIModel = FilterUIModel(
    id = VAULT_ITEM_TYPE_BANK_ACCOUNTS,
    nameResId = Res.string.bank_accounts,
    filterIcon = FilterIcon(
        imageVector = Icons.Default.CreditCard
    ),
    contentColorResName = "onPrimaryContainer",
    isSelected = false
)

fun getVaultItemTypeFilters() = listOf(
    passwordsFilterUIModel,
    notesFilterUIModel,
    bankAccountsFilterUIModel
)