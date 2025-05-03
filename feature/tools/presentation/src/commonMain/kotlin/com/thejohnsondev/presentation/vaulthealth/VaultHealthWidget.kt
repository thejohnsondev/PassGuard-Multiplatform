package com.thejohnsondev.presentation.vaulthealth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GppMaybe
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SyncProblem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.common.PASSWORD_AGE_THRESHOLD_DAYS
import com.thejohnsondev.ui.components.ArcProgressbar
import com.thejohnsondev.ui.components.ExpandableSectionItem
import com.thejohnsondev.ui.components.vault.PasswordItem
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.DeepForestSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.MaterialSelectableItemColors
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.padding
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import vaultmultiplatform.core.ui.generated.resources.password_health_leaked_description
import vaultmultiplatform.core.ui.generated.resources.password_health_leaked_title
import vaultmultiplatform.core.ui.generated.resources.password_health_leaked_title_positive
import vaultmultiplatform.core.ui.generated.resources.password_health_old_description
import vaultmultiplatform.core.ui.generated.resources.password_health_old_title
import vaultmultiplatform.core.ui.generated.resources.password_health_old_title_positive
import vaultmultiplatform.core.ui.generated.resources.password_health_reused_description
import vaultmultiplatform.core.ui.generated.resources.password_health_reused_title
import vaultmultiplatform.core.ui.generated.resources.password_health_reused_title_positive
import vaultmultiplatform.core.ui.generated.resources.password_health_weak_description
import vaultmultiplatform.core.ui.generated.resources.password_health_weak_title
import vaultmultiplatform.core.ui.generated.resources.password_health_weak_title_positive

@OptIn(KoinExperimentalAPI::class)
@Composable
fun VaultHealthWidget(
    modifier: Modifier = Modifier,
    viewModel: VaultHealthViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.perform(VaultHealthViewModel.Action.GenerateReport)
    }

    VaultHealthWidgetContent(
        modifier = modifier,
        state = state.value,
        onAction = viewModel::perform
    )

}

@Composable
fun VaultHealthWidgetContent(
    modifier: Modifier,
    state: VaultHealthViewModel.State,
    onAction: (VaultHealthViewModel.Action) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArcProgressbar(
            modifier = Modifier
                .padding(horizontal = Size16),
            progress = state.report?.overallScore ?: 0f,
            text = "Score"
        )
        VaultHealthPasswordLists(
            modifier = Modifier
                .padding(vertical = Size16, horizontal = Size4)
                .fillMaxWidth(),
            state = state,
            onAction = onAction
        )
    }
}

@Composable
fun VaultHealthPasswordLists(
    modifier: Modifier = Modifier,
    state: VaultHealthViewModel.State,
    onAction: (VaultHealthViewModel.Action) -> Unit,
) {
    val areOldPasswordsPresent = state.report?.oldPasswords?.isNotEmpty() ?: false
    val areWeakPasswordsPresent = state.report?.weakPasswords?.isNotEmpty() ?: false
    val areReusedPasswordsPresent = state.report?.reusedPasswords?.isNotEmpty() ?: false
    val areLeakedPasswordsPresent = state.report?.leakedPasswords?.isNotEmpty() ?: false

    val defaultColors = MaterialSelectableItemColors

    Column(modifier = modifier) {
        ExpandableSectionItem(
            modifier = Modifier
                .padding(horizontal = Size4)
                .fillMaxWidth(),
            title = stringResource(if (areWeakPasswordsPresent) ResString.password_health_weak_title else ResString.password_health_weak_title_positive),
            description = if (areWeakPasswordsPresent) {
                stringResource(ResString.password_health_weak_description)
            } else null,
            isFirstItem = true,
            icon = if (areWeakPasswordsPresent) Icons.Default.SentimentDissatisfied else Icons.Default.CheckCircle, // TODO change depending on areOldPasswordsPresent
            colors = if (areWeakPasswordsPresent) defaultColors else DeepForestSelectableItemColors
        ) {
            state.weakPasswords?.forEach { password ->
                PasswordItem(
                    modifier = Modifier.padding(horizontal = Size4),
                    item = password,
                    onClick = { /* Handle click */ },
                    onDeleteClick = { /* Handle delete click */ },
                    onEditClick = { /* Handle edit click */ },
                    onCopySensitive = { /* Handle copy sensitive */ },
                    onCopy = { /* Handle copy */ },
                    onFavoriteClick = { /* Handle favorite click */ },
                )
            }
        }

        ExpandableSectionItem(
            modifier = Modifier
                .padding(top = Size4, horizontal = Size4)
                .fillMaxWidth(),
            title = stringResource(if (areLeakedPasswordsPresent) ResString.password_health_leaked_title else ResString.password_health_leaked_title_positive),
            description = if (areLeakedPasswordsPresent) {
                stringResource(ResString.password_health_leaked_description)
            } else null,
            icon = if (areLeakedPasswordsPresent) Icons.Default.GppMaybe else Icons.Default.CheckCircle, // TODO change depending on areOldPasswordsPresent
            colors = if (areLeakedPasswordsPresent) defaultColors else DeepForestSelectableItemColors
        ) {
            state.leakedPasswords?.forEach { password ->
                PasswordItem(
                    modifier = Modifier.padding(horizontal = Size4),
                    item = password,
                    onClick = { /* Handle click */ },
                    onDeleteClick = { /* Handle delete click */ },
                    onEditClick = { /* Handle edit click */ },
                    onCopySensitive = { /* Handle copy sensitive */ },
                    onCopy = { /* Handle copy */ },
                    onFavoriteClick = { /* Handle favorite click */ },
                )
            }
        }

        ExpandableSectionItem(
            modifier = Modifier
                .padding(top = Size4, horizontal = Size4)
                .fillMaxWidth(),
            title = stringResource(if (areReusedPasswordsPresent) ResString.password_health_reused_title else ResString.password_health_reused_title_positive),
            description = if (areReusedPasswordsPresent) {
                stringResource(ResString.password_health_reused_description)
            } else null,
            icon = if (areReusedPasswordsPresent) Icons.Default.SyncProblem else Icons.Default.CheckCircle, // TODO change depending on areOldPasswordsPresent
            colors = if (areReusedPasswordsPresent) defaultColors else DeepForestSelectableItemColors
        ) {
            state.reusedPasswords?.forEach { password ->
                PasswordItem(
                    modifier = Modifier.padding(horizontal = Size4),
                    item = password,
                    onClick = { /* Handle click */ },
                    onDeleteClick = { /* Handle delete click */ },
                    onEditClick = { /* Handle edit click */ },
                    onCopySensitive = { /* Handle copy sensitive */ },
                    onCopy = { /* Handle copy */ },
                    onFavoriteClick = { /* Handle favorite click */ },
                )
            }
        }

        ExpandableSectionItem(
            modifier = Modifier
                .padding(top = Size4, horizontal = Size4)
                .fillMaxWidth(),
            title = stringResource(if (areOldPasswordsPresent) ResString.password_health_old_title else ResString.password_health_old_title_positive),
            description = if (areOldPasswordsPresent) {
                stringResource(ResString.password_health_old_description, PASSWORD_AGE_THRESHOLD_DAYS.toString())
            } else null,
            isLastItem = true,
            icon = if (areOldPasswordsPresent) Icons.Default.History else Icons.Default.CheckCircle, // TODO change depending on areOldPasswordsPresent
            colors = if (areOldPasswordsPresent) defaultColors else DeepForestSelectableItemColors
        ) {
            state.oldPasswords?.forEach { password ->
                PasswordItem(
                    modifier = Modifier.padding(horizontal = Size4),
                    item = password,
                    onClick = { /* Handle click */ },
                    onDeleteClick = { /* Handle delete click */ },
                    onEditClick = { /* Handle edit click */ },
                    onCopySensitive = { /* Handle copy sensitive */ },
                    onCopy = { /* Handle copy */ },
                    onFavoriteClick = { /* Handle favorite click */ },
                )
            }
        }
    }
}