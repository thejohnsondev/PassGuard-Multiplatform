package com.thejohnsondev.presentation.vaulthealth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GppMaybe
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SyncProblem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.common.PASSWORD_AGE_THRESHOLD_DAYS
import com.thejohnsondev.ui.components.ArcProgressbar
import com.thejohnsondev.ui.components.ExpandableSectionItem
import com.thejohnsondev.ui.components.vault.passworditem.PasswordItem
import com.thejohnsondev.ui.components.vault.passworditem.PasswordItemProperties
import com.thejohnsondev.ui.designsystem.EquallyRounded
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Text12
import com.thejohnsondev.ui.designsystem.Text18
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.DeepForestSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.RedAlgaeSelectableItemColors
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
import vaultmultiplatform.core.ui.generated.resources.password_health_stats_leaked
import vaultmultiplatform.core.ui.generated.resources.password_health_stats_reused
import vaultmultiplatform.core.ui.generated.resources.password_health_stats_total
import vaultmultiplatform.core.ui.generated.resources.password_health_stats_weak
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ArcProgressbar(
                modifier = Modifier
                    .padding(start = Size8),
                progress = state.report?.overallScore ?: 0f,
                text = "Score"
            )
            StatsWidget(
                modifier = Modifier
                    .padding(start = Size8)
                    .fillMaxWidth(),
                state = state,
            )
        }
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
private fun StatsWidget(
    modifier: Modifier = Modifier,
    state: VaultHealthViewModel.State,
) {
    Column {
        Row {
            StatBox(
                modifier = Modifier
                    .padding(Size4)
                    .wrapContentHeight()
                    .weight(Percent100),
                number = state.report?.totalPasswords,
                title = stringResource(ResString.password_health_stats_total),
                backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                textColor = MaterialTheme.colorScheme.onSurface,
            )
            StatBox(
                modifier = Modifier
                    .padding(Size4)
                    .wrapContentHeight()
                    .weight(Percent100),
                number = state.report?.weakPasswords?.size,
                title = stringResource(ResString.password_health_stats_weak),
                backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                textColor = MaterialTheme.colorScheme.onSurface,
            )
        }
        Row {
            StatBox(
                modifier = Modifier
                    .padding(Size4)
                    .wrapContentHeight()
                    .weight(Percent100),
                number = state.report?.leakedPasswords?.size,
                title = stringResource(ResString.password_health_stats_leaked),
                backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                textColor = MaterialTheme.colorScheme.onSurface,
            )
            StatBox(
                modifier = Modifier
                    .padding(Size4)
                    .wrapContentHeight()
                    .weight(Percent100),
                number = state.report?.reusedPasswords?.size,
                title = stringResource(ResString.password_health_stats_reused),
                backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                textColor = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun StatBox(
    modifier: Modifier = Modifier,
    number: Int?,
    title: String,
    backgroundColor: Color,
    textColor: Color,
) {
    Column(
        modifier = modifier
            .clip(EquallyRounded.medium)
            .background(backgroundColor)
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = Size8, horizontal = Size8),
            text = (number ?: 0).toString(),
            fontSize = Text18,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .padding(bottom = Size8, horizontal = Size8),
            text = title,
            style = TextStyle(
                fontSize = Text12,
                color = textColor
            )
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

    val warningColors = RedAlgaeSelectableItemColors(false)

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
            colors = if (areWeakPasswordsPresent) warningColors else DeepForestSelectableItemColors
        ) {
            state.weakPasswords?.forEach { password ->
                PasswordItem(
                    modifier = Modifier.padding(horizontal = Size4),
                    properties = PasswordItemProperties(
                        showFavoriteButton = false,
                        showCopyButton = false,
                        showEditButton = false,
                        showDeleteButton = false,
                        swapColorsWhenExpanding = false
                    ),
                    item = password,
                    onClick = {
                        onAction(
                            VaultHealthViewModel.Action.ToggleOpenItem(
                                itemId = password.id,
                                passwordListType = VaultHealthViewModel.PasswordListType.Weak
                            )
                        )
                    },
                    isExpanded = password.isExpanded,
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
            colors = if (areLeakedPasswordsPresent) warningColors else DeepForestSelectableItemColors
        ) {
            state.leakedPasswords?.forEach { password ->
                PasswordItem(
                    modifier = Modifier.padding(horizontal = Size4),
                    properties = PasswordItemProperties(
                        showFavoriteButton = false,
                        showCopyButton = false,
                        showEditButton = false,
                        showDeleteButton = false,
                        swapColorsWhenExpanding = false
                    ),
                    item = password,
                    onClick = {
                        onAction(
                            VaultHealthViewModel.Action.ToggleOpenItem(
                                itemId = password.id,
                                passwordListType = VaultHealthViewModel.PasswordListType.Leaked
                            )
                        )
                    },
                    isExpanded = password.isExpanded,
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
            colors = if (areReusedPasswordsPresent) warningColors else DeepForestSelectableItemColors
        ) {
            state.reusedPasswords?.forEach { password ->
                PasswordItem(
                    modifier = Modifier.padding(horizontal = Size4),
                    properties = PasswordItemProperties(
                        showFavoriteButton = false,
                        showCopyButton = false,
                        showEditButton = false,
                        showDeleteButton = false,
                        swapColorsWhenExpanding = false
                    ),
                    item = password,
                    onClick = {
                        onAction(
                            VaultHealthViewModel.Action.ToggleOpenItem(
                                itemId = password.id,
                                passwordListType = VaultHealthViewModel.PasswordListType.Reused
                            )
                        )
                    },
                    isExpanded = password.isExpanded,
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
                stringResource(
                    ResString.password_health_old_description,
                    PASSWORD_AGE_THRESHOLD_DAYS.toString()
                )
            } else null,
            isLastItem = true,
            icon = if (areOldPasswordsPresent) Icons.Default.History else Icons.Default.CheckCircle, // TODO change depending on areOldPasswordsPresent
            colors = if (areOldPasswordsPresent) warningColors else DeepForestSelectableItemColors
        ) {
            state.oldPasswords?.forEach { password ->
                PasswordItem(
                    modifier = Modifier.padding(horizontal = Size4),
                    properties = PasswordItemProperties(
                        showFavoriteButton = false,
                        showCopyButton = false,
                        showEditButton = false,
                        showDeleteButton = false,
                        swapColorsWhenExpanding = false
                    ),
                    item = password,
                    onClick = {
                        onAction(
                            VaultHealthViewModel.Action.ToggleOpenItem(
                                itemId = password.id,
                                passwordListType = VaultHealthViewModel.PasswordListType.Old
                            )
                        )
                    },
                    isExpanded = password.isExpanded,
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