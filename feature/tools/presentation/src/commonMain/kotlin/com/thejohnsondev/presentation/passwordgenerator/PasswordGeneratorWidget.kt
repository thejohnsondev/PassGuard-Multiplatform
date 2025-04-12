package com.thejohnsondev.presentation.passwordgenerator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.components.container.RoundedContainer
import com.thejohnsondev.ui.designsystem.EndRounded
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.StartRounded
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import vaultmultiplatform.core.ui.generated.resources.password_generator_copy
import vaultmultiplatform.core.ui.generated.resources.password_generator_generate

@OptIn(KoinExperimentalAPI::class)
@Composable
fun PasswordGeneratorWidget(
    modifier: Modifier = Modifier,
    viewModel: PasswordGeneratorViewModel = koinViewModel(),
    onCopyClick: (String) -> Unit = {},
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.perform(PasswordGeneratorViewModel.Action.FetchConfig)
    }

    PasswordGeneratorWidgetContent(
        modifier = modifier,
        state = state.value,
        onAction = {
            when (it) {
                is PasswordGeneratorViewModel.Action.Copy -> {
                    onCopyClick(it.password)
                }
                else -> {
                    // no-op
                }
            }
            viewModel.perform(it)
        },
    )
}

// this should be private, but the previews are in another module rn
@Composable
fun PasswordGeneratorWidgetContent(
    modifier: Modifier = Modifier,
    state: PasswordGeneratorViewModel.State,
    onAction: (PasswordGeneratorViewModel.Action) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedContainer(
            modifier = Modifier
                .padding(Size8)
                .fillMaxWidth(),
        ) {
            Column {
                MainPasswordView(state)
                GenerateButtonsRow(
                    hapticFeedback = hapticFeedback,
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun MainPasswordView(state: PasswordGeneratorViewModel.State) {
    Text(
        modifier = Modifier
            .padding(Size8)
            .fillMaxWidth(),
        text = buildAnnotatedString {
            append(state.passwordGeneratedResult?.password.orEmpty())
        },
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge,
    )
    HorizontalDivider(
        modifier = Modifier
            .padding(Size4)
            .fillMaxWidth()
    )
    Text(
        modifier = Modifier
            .padding(Size8)
            .fillMaxWidth(),
        text = buildAnnotatedString {
            append(state.passwordGeneratedResult?.suggestion.orEmpty())
        },
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
    )
}

@Composable
private fun GenerateButtonsRow(
    hapticFeedback: HapticFeedback,
    state: PasswordGeneratorViewModel.State,
    onAction: (PasswordGeneratorViewModel.Action) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(Size12),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RoundedButton(
            modifier = Modifier
                .padding(end = Size4)
                .weight(Percent100),
            text = stringResource(ResString.password_generator_generate),
            imageVector = Icons.Default.Casino,
            buttonShape = StartRounded,
            onClick = {
                hapticFeedback.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
                onAction(PasswordGeneratorViewModel.Action.GeneratePassword)
            }
        )
        RoundedButton(
            modifier = Modifier
                .padding(start = Size4)
                .weight(Percent100),
            text = stringResource(ResString.password_generator_copy),
            imageVector = Icons.Default.CopyAll,
            buttonShape = EndRounded,
            onClick = {
                hapticFeedback.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
                onAction(PasswordGeneratorViewModel.Action.Copy(
                    password = state.passwordGeneratedResult?.password.orEmpty()
                ))
            }
        )
    }
}