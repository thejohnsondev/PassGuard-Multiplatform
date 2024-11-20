package com.thejohnsondev.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.CreditCard
import com.thejohnsondev.uimodel.Filter
import com.thejohnsondev.uimodel.FilterIcon
import vaultmultiplatform.feature.vault.presentation.generated.resources.Res
import vaultmultiplatform.feature.vault.presentation.generated.resources.all
import vaultmultiplatform.feature.vault.presentation.generated.resources.bank_accounts
import vaultmultiplatform.feature.vault.presentation.generated.resources.ic_password
import vaultmultiplatform.feature.vault.presentation.generated.resources.notes
import vaultmultiplatform.feature.vault.presentation.generated.resources.passwords

fun getVaultItemTypeFilters() = listOf(
    Filter(
        id = Filter.FILTER_ALL,
        nameResId = Res.string.all,
        filterIcon = null,
        contentColorResName = "onPrimaryContainer",
        backgroundColorResName = "primaryContainer",
        isSelected = true
    ),
    Filter(
        id = "filter_passwords",
        nameResId = Res.string.passwords,
        filterIcon = FilterIcon(
            imageVectorResId = Res.drawable.ic_password
        ),
        contentColorResName = "onPrimaryContainer",
        backgroundColorResName = "primaryContainer",
        isSelected = true
    ),
    Filter(
        id = "filter_notes",
        nameResId = Res.string.notes,
        filterIcon = FilterIcon(
            imageVector = Icons.AutoMirrored.Filled.StickyNote2
        ),
        contentColorResName = "onPrimaryContainer",
        backgroundColorResName = "primaryContainer",
        isSelected = true
    ),
    Filter(
        id = "filter_bank_accounts",
        nameResId = Res.string.bank_accounts,
        filterIcon = FilterIcon(
            imageVector = Icons.Default.CreditCard
        ),
        contentColorResName = "onPrimaryContainer",
        backgroundColorResName = "primaryContainer",
        isSelected = true
    ),
)