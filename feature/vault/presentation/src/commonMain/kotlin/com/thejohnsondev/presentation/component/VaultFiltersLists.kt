package com.thejohnsondev.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.thejohnsondev.ui.model.Filter
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.feature.vault.presentation.generated.resources.Res
import vaultmultiplatform.feature.vault.presentation.generated.resources.all
import vaultmultiplatform.feature.vault.presentation.generated.resources.bank_accounts
import vaultmultiplatform.feature.vault.presentation.generated.resources.ic_password
import vaultmultiplatform.feature.vault.presentation.generated.resources.notes
import vaultmultiplatform.feature.vault.presentation.generated.resources.passwords

@Composable
fun getVaultItemTypeFilters() = listOf(
    Filter(
        id = "filter_all",
        name = stringResource(Res.string.all),
        imageVector = null,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer
    ),
    Filter(
        id = "filter_passwords",
        name = stringResource(Res.string.passwords),
        imageVector = vectorResource(Res.drawable.ic_password),
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer
    ),
    Filter(
        id = "filter_notes",
        name = stringResource(Res.string.notes),
        imageVector = Icons.AutoMirrored.Filled.StickyNote2,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer
    ),
    Filter(
        id = "filter_bank_accounts",
        name = stringResource(Res.string.bank_accounts),
        imageVector = Icons.Default.CreditCard,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer
    ),
)